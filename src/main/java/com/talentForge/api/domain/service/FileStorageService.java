package com.talentForge.api.domain.service;

import org.springframework.web.multipart.MultipartFile;


public interface FileStorageService {

    String uploadFile(MultipartFile file);

    String getFileUrl(String fileName);
}
