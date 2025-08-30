package com.gova.EasyGuide.service.db1.Users;

import com.gova.EasyGuide.entities.db1.ForgotPassword;
import com.gova.EasyGuide.entities.db1.User;
import com.gova.EasyGuide.entities.db1.UserRegistartionDto;
import com.gova.EasyGuide.entities.db1.UserLogin;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public interface UserService {

    public User regiseterUser(UserRegistartionDto dto);

    public void  updatingUsersDeatails(User user);


    public HashMap<String,String> validateUser(UserLogin userLogin);

    public User getUserDetails(String userName);

    public String forgotPasswordLink(String email);

    public String verifyForgotPasswordLink(ForgotPassword forgotPassword,String token);



}
