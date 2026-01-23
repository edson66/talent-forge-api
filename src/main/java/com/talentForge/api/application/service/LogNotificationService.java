package com.talentForge.api.application.service;

import com.talentForge.api.domain.service.NotificationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LogNotificationService implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(LogNotificationService.class);

    @Override
    public void notify(String to, String subject, String body) {
        System.out.println("==================================================");
        logger.info("📧 SIMULAÇÃO DE ENVIO DE E-MAIL");
        logger.info("PARA: {}", to);
        logger.info("ASSUNTO: {}", subject);
        logger.info("MENSAGEM: {}", body);
        System.out.println("==================================================");
    }
}
