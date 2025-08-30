package com.gova.EasyGuide.service.db1.Users;


import com.gova.EasyGuide.Enums.Roles;
import com.gova.EasyGuide.configurations.DetailsPatcher;
import com.gova.EasyGuide.entities.db1.ForgotPassword;
import com.gova.EasyGuide.entities.db1.User;
import com.gova.EasyGuide.entities.db1.UserRegistartionDto;
import com.gova.EasyGuide.entities.db1.UserLogin;
import com.gova.EasyGuide.exceptions.AllExceptions;
import com.gova.EasyGuide.repositeries.db1repo.MentorRepo;
import com.gova.EasyGuide.repositeries.db1repo.UserRepo;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DetailsPatcher patcher;

    @Autowired
    private MentorRepo mentorRepo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private MyUserDetailsService myUserDetailsService;


    @Override
    public User regiseterUser(UserRegistartionDto dto) throws AllExceptions.userAllReadyExist {

        Optional<User> userfind = userRepo.findByUserEmail(dto.getDtoUseremail());
        Optional<User> userfindByName= userRepo.findByUserName(dto.getDtoUsername());

        if(userfind.isPresent())
        {
            throw  new AllExceptions.userAllReadyExist("User exist with user mail");

        }
        if(userfindByName.isPresent())
        {
            throw new AllExceptions.userAllReadyExist("User exist with user name");
        }

        //to encrypt the password
        dto.setDtoUserPassword(encoder.encode(dto.getDtoUserPassword()));
        User user = new User(dto.getDtoUsername(), dto.getDtoUseremail(), dto.getDtoUserPassword());
        user.setRoles(Roles.USER);
            return userRepo.save(user);
    }

    @Override
    public void updatingUsersDeatails(User user) {
        Optional<User> currentUser = userRepo.findByUserId(user.getUserId());

        if(currentUser.isPresent())
        {
            User existingUser= currentUser.get();
            try {

                if(user.getUserName() !=null)
                {
                    existingUser.setUserName(user.getUserName());
                }
                if(user.getUserContactNumber() !=null)
                {
                    existingUser.setUserContactNumber(user.getUserContactNumber());
                }
                if(user.getAbout() !=null)
                {
                    existingUser.setAbout(user.getAbout());
                }
                if(user.getProfession() !=null)
                {
                    existingUser.setProfession(user.getProfession());
                }
                if(user.getUserSocailMedia() !=null)
                {
                    existingUser.setUserSocailMedia(user.getUserSocailMedia());
                }
            userRepo.save(existingUser);

            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }else {
            throw new AllExceptions.userNotFound("user not found with");
        }

    }



    //verifyting the username and the password at the time of thelogin
    @Override
    public HashMap<String, String> validateUser(UserLogin userLogin) {
        Optional<User> user = userRepo.findByUserName(userLogin.getUserName());
        if (user.isPresent()) {
            try {
                // Authenticate credentials
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                userLogin.getUserName(),
                                userLogin.getUserPassword()
                        )
                );

                // Load UserDetails with proper authorities
                UserDetails userDetails = myUserDetailsService.loadUserByUsername(userLogin.getUserName());

                // Generate tokens
                HashMap<String, String> keys = new HashMap<>();
                keys.put("JWT_TOKEN", jwtService.generateToken(userDetails));
                keys.put("REFRESH_TOKEN", jwtService.generateRefreshToken(userDetails));

                return keys;

            } catch (AuthenticationException e) {
                throw new AllExceptions.invalidCredentails("Credentials are not matched");
            }
        }
        throw new AllExceptions.userNotFound("User with this username is not registered, please register");
    }

    public User getUserDetails(String userName)
    {
        Optional<User> user = userRepo.findByUserName(userName);
        if(user.isPresent())
        {
            return user.get();
        }
        else{
            throw new RuntimeException("User wiht this name is not found");
        }
    }

    @Override
    public String forgotPasswordLink(String email) {
        String token = RandomStringUtils.randomAlphanumeric(10,15);
        Optional<User> optionalEmail = userRepo.findByUserEmail(email);
        if(optionalEmail.isPresent())
        {
                String url="http://localhost:5173/forgotPassword/token="+token;
                emailSender.sendEmail(email,
                        "One time password generation link this will expires in 5 minutes",url
                );
                redisTemplate.opsForValue().set(email,token, Duration.ofMinutes(5));
                return "Link has been sent to user Registered Email";
        }
        else {
            throw new AllExceptions.userNotFound("User with this email is not found");
        }


    }

    @Override
    public String verifyForgotPasswordLink(ForgotPassword forgotPassword,String token) {

        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Token must not be null or empty");
        }
        String tokenValue = redisTemplate.opsForValue().get(forgotPassword.getUserEmail());
        if(tokenValue==null)
        {
            return "expired";
        }
        Optional<User> optionalUser = userRepo.findByUserEmail(forgotPassword.getUserEmail());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Set new password
            user.setUserPassword(encoder.encode(forgotPassword.getUserPassword()));

            // Save updated user
            userRepo.save(user);

            // Optionally: delete the token from Redis
            redisTemplate.delete(forgotPassword.getUserEmail());

            return "Password has been reset successfully";
        }
        else {
            throw new AllExceptions.userNotFound("User with this email is not found");
        }
    }


}


