package com.itransition.coursework.util;

import com.itransition.coursework.user.Role;
import com.itransition.coursework.user.RoleEnum;
import com.itransition.coursework.user.User;
import com.itransition.coursework.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
                    new Role(RoleEnum.SUPER_ADMIN),
                    true,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
            userRepository.save(super_admin);

            User abdulqodir = new User(
                    "Abdulkodir",
                    "a@gmail.com",
                    passwordEncoder.encode("1234"),
                    new Role(RoleEnum.ADMIN),
                    true,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
            userRepository.save(abdulqodir);

            User john = new User(
                    "John",
                    "j@gmail.com",
                    passwordEncoder.encode("1234"),
                    new Role(RoleEnum.CREATOR),
                    false,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
            userRepository.save(john);
        }
    }
}
