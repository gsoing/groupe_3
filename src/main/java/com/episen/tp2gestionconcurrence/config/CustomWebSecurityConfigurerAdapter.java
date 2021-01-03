package com.episen.tp2gestionconcurrence.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@RequiredArgsConstructor
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("redacteur").password(passwordEncoder().encode("redacteur"))
                .roles("EDITOR");
        auth.inMemoryAuthentication()
                .withUser("relecteur").password(passwordEncoder().encode("relecteur"))
                .roles("REVIEWER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // allow documentation to be embedded in an iframe
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/documents/**").hasAnyRole("EDITOR","REVIEWER")
                // C'est un peu violent comme sécurité car personne ne peut faire de GET sur la ressource mais ok pourquoi pas
                .antMatchers("/documents/{documentId}/status").hasRole("REVIEWER")
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
