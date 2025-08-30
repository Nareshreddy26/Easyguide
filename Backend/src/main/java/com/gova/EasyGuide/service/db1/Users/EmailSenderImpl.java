package com.gova.EasyGuide.service.db1.Users;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderImpl implements  EmailSender{

    @Autowired
    private JavaMailSender mailsender;

    @Override
    public void sendEmail(String to, String message, String body) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(message);
        simpleMailMessage.setText(body);
        simpleMailMessage.setFrom("saigovardhan52@gmail.com");
        mailsender.send(simpleMailMessage);

    }
}
