package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.core.io.ByteArrayResource;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String to, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Welcome to IT-INFO Academy");
        message.setText("Dear " + name + ",\n\nThank you for registering with us!\n\nBest Regards,\nIT-INFO Academy");
        mailSender.send(message);
    }
    
    public void sendFeeReceipt(String to, String name, byte[] pdfBytes) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("IT-INFO Academy: Fee Receipt");
        helper.setText("Dear " + name + ",\n\nPlease find attached your fee receipt.\n\nRegards,\nIT-INFO Academy");

        helper.addAttachment("FeeReceipt.pdf", new ByteArrayResource(pdfBytes));

        mailSender.send(message);
    }
    
   
}
