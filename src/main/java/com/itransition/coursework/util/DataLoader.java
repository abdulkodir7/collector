package com.itransition.coursework.util;

import com.itransition.coursework.user.Role;
import com.itransition.coursework.user.User;
import com.itransition.coursework.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Abdulqodir Ganiev 6/16/2022 7:05 PM
 */

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    String initMode;


    @Override
    public void run(String... args) {
        if (initMode.equals("always")) {

            User super_admin = new User(
                    "Super Admin",
                    "sa@gmail.com",
                    passwordEncoder.encode("1234"),
                    Role.SUPER_ADMIN
            );
            userRepository.save(super_admin);

            User abdulqodir = new User(
                    "Abdulkodir",
                    "a@gmail.com",
                    passwordEncoder.encode("1234"),
                    Role.ADMIN
            );
            userRepository.save(abdulqodir);

            User john = new User(
                    "John",
                    "j@gmail.com",
                    passwordEncoder.encode("1234"),
                    Role.CREATOR
            );
            userRepository.save(john);
        }
    }
}
