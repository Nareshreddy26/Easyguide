package com.gova.EasyGuide.service.db1.Users;

public interface EmailSender {

    void sendEmail(String to,String message,String body);

}
