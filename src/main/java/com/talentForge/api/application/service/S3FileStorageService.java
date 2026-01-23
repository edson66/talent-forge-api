package com.talentForge.api.infrastructure.service;

import com.talentForge.api.domain.service.FileStorageService;
import io.awspring.cloud.s3.S3Template; // Biblioteca facilitadora do Spring Cloud AWS
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3FileStorageService implements FileStorageService {

    @Autowired
    private S3Template s3Template;

    @Value("${app.s3.bucket}")
    private String bucketName;

    @Override
    public String uploadFile(MultipartFile file) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        try {
            s3Template.upload(bucketName, fileName, file.getInputStream());

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer upload do arquivo", e);
        }
    }

    @Override
    public String getFileUrl(String fileName) {
        return s3Template.createSignedGetURL(bucketName,fileName,java.time.Duration.ofHours(1)).toString();
    }
}