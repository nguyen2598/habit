package com.habittracke.service.impl;


import com.habittracke.service.SendMailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendMailServiceImpl implements SendMailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendTextMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your_email@gmail.com"); // bắt buộc giống với username ở trên
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }

    @Override
    public void sendHtmlMail(String to, String subject, String htmlContent)  {

        try {
            MimeMessage message = mailSender.createMimeMessage();

            // true: multipart (nếu cần đính kèm file)
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("your_email@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true để cho phép HTML

            mailSender.send(message);
        }catch (MessagingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
