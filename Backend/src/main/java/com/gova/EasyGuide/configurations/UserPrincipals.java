package com.gova.EasyGuide.configurations;

import com.gova.EasyGuide.Enums.Roles;
import com.gova.EasyGuide.entities.db1.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipals implements UserDetails {

    private final User user;

    public UserPrincipals(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Prefix with "ROLE_" as Spring Security expects this format
        Roles role = user.getRoles();  // assuming getRole() returns enum Roles
        String roleName = "ROLE_" + role.name(); // e.g., ROLE_ADMIN
        return Collections.singleton(new SimpleGrantedAuthority(roleName));
    }

    @Override
    public String getPassword() {
        return user.getUserPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
