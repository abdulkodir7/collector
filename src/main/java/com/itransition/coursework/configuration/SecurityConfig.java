package com.itransition.coursework.configuration;

import com.itransition.coursework.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Abdulqodir Ganiev 6/16/2022 5:52 PM
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Bean
    public PasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(encodePassword());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable();

        http
                .authorizeRequests()
                .antMatchers(
                        "/",
                        "/login",
                        "/register",
                        "/topics/**",
                        "/tags/**",
                        "/collections/**",
                        "/items/**",
                        "/client-search/**",
                        "/admin-login",
                        "/admin-assets/**",
                        "/css/**",
                        "/fonts/**",
                        "/images/**",
                        "/js/**"
                )
                .permitAll()
                .antMatchers("/admin/**")
                .hasAnyRole("SUPER_ADMIN", "ADMIN")
                .antMatchers("/**")
                .authenticated()
                .and()
                .formLogin().loginPage("/login")
                .permitAll()
                .and().logout()
                .permitAll();
    }
}

