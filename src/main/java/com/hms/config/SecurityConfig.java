package com.hms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private JWTFilter jwtFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception{
        //h(cd)2
        http.csrf().disable().cors().disable();

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


        //haap
//        http.authorizeHttpRequests().anyRequest().permitAll();
        http.authorizeHttpRequests().
                requestMatchers("/api/v1/users/login", "/api/v1/users/signUp","/api/v1/users/signup-propertyowner").
                permitAll().
                requestMatchers("/api/v1/users/get").hasRole("OWNER").
                anyRequest().
                authenticated();
         return http.build();
    }

}
