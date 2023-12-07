package com.example.taskmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    @Value("${mail.sender}")
    private static String sender;
    private final JavaMailSender javaMailSender;

    public void sendVerificationCode(String receiver, int code){
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(receiver);
            simpleMailMessage.setSubject("Verification Code");
            simpleMailMessage.setText(String.valueOf(code));
            javaMailSender.send(simpleMailMessage);

        }catch (Exception e){
            throw new RuntimeException("Exception with sending email");
        }
    }
}
