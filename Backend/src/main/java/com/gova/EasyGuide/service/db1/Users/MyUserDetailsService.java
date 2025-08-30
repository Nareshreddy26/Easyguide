package com.gova.EasyGuide.service.db1.Users;

import com.gova.EasyGuide.entities.db1.User;
import com.gova.EasyGuide.configurations.UserPrincipals;
import com.gova.EasyGuide.repositeries.db1repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class MyUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Looking up user: " + username);
        Optional<User> optionalUser = userRepo.findByUserName(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User Not found with username: " + username);
        }

        return new UserPrincipals(optionalUser.get());
    }

}
