package com.habittracke.service;

public interface SendMailService {
    void sendTextMail(String to, String subject, String content);
    void sendHtmlMail(String to, String subject, String htmlContent);
}
