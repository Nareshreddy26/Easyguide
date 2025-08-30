package com.gova.EasyGuide.entities.db1;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ForgotPassword {

    public String userEmail;

    public String userPassword;

    public  String token;
}
