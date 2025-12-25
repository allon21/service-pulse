package com.servicepulse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/error", "/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon.ico").permitAll()
                        .anyRequest().authenticated()  // Остальное требует логина
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()             // Разрешает доступ к странице логина и обработке
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")    // После логаута на главную
                        .permitAll()
                );

        return http.build();
    }
}