package com.gova.EasyGuide.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringConfiguration {


    @Autowired
    private UserDetailsService userDetailsService;

    //own we are creating our own filter
    @Autowired
    private JwtFilter jwtFilter;

    //using this it will remove default
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {


        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // ✅ CORS enabled using lambda
                .csrf(customizer->customizer.disable())
                .authorizeHttpRequests(request->request
                        //the bottom  line says the url two no need authentication and the thire dline says every thing should authenticate
                        .requestMatchers("/user/user-signup","/user/login","/user/forgotPassword/**","/user/verify-forgot-password").permitAll()
                        // Role-based endpoints
                .requestMatchers("/user/**").hasRole("USER")
                .requestMatchers("/mentor/**").hasRole("MENTOR")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())  //fore default form login
                .httpBasic(Customizer.withDefaults())//for to acess that api end points from postman
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//to make the applciation stateless
                .authenticationProvider(authenticationProvider())
                //usernamepasswordauth.... filter do jwtfilter(this is the validation of the jwt)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        System.out.println("VErification completed");
        return  httpSecurity.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }



    //to authenticate the user using db data
    @Bean
    public AuthenticationProvider authenticationProvider()
    {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //byycrptpassworndeconeoder strength
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        //myUSerDetailsServcei
        provider.setUserDetailsService(userDetailsService);
        return  provider;
    }



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }




}
