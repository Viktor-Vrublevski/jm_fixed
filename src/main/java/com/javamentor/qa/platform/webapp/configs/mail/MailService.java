package com.javamentor.qa.platform.webapp.configs.mail;

public interface MailService {
    void sendSimpleMessage(String to, String subject, String text);
}
