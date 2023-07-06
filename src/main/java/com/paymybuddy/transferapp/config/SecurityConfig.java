package com.paymybuddy.transferapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailService customUserDetailService;

    public SecurityConfig(CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/").permitAll()
                /*.requestMatchers("/user/list").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/user/**").hasAuthority("ADMIN")
                .requestMatchers("/user/add/**").hasAuthority("ADMIN")
                .requestMatchers("/user/update/**").hasAuthority("ADMIN")
                .requestMatchers("/user/validate").hasAuthority("ADMIN")
                .requestMatchers("/user/delete/**").hasAuthority("ADMIN")*/
                /*.requestMatchers("/user/update/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/user/**").hasRole("ADMIN")
                .requestMatchers("/user/validate").hasRole("ADMIN")
                .requestMatchers("/user/delete/**").hasRole("ADMIN")*/
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .permitAll()
                .defaultSuccessUrl("/transactions", true)
                .and()
                .logout()
                .permitAll()
                .deleteCookies("JSESSIONID")
                .and()
                .rememberMe()
                .key("uniqueAndSecret");
        return httpSecurity.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*@Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(customUserDetailService);
        return provider;
    }*/
}

