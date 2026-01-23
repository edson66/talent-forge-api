package com.talentForge.api.domain.service;

public interface NotificationService {
    void notify(String to, String subject, String body);
}
