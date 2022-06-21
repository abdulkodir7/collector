package com.itransition.coursework.util;

import com.itransition.coursework.category.Category;
import com.itransition.coursework.category.CategoryRepository;
import com.itransition.coursework.user.role.Role;
import com.itransition.coursework.user.role.RoleEnum;
import com.itransition.coursework.user.User;
import com.itransition.coursework.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Abdulqodir Ganiev 6/16/2022 7:05 PM
 */

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;

    @Value("${spring.sql.init.mode}")
    String initMode;

    @Override
    public void run(String... args) {
        if (initMode.equals("always")) {

            /* SAVING USER */
            User super_admin = new User(
                    "Super Admin",
                    "sa@gmail.com",
                    passwordEncoder.encode("1234"),
                    new Role(RoleEnum.ROLE_SUPER_ADMIN),
                    true,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
            userRepository.save(super_admin);

            User abdulqodir = new User(
                    "Abdulkodir",
                    "a@gmail.com",
                    passwordEncoder.encode("1234"),
                    new Role(RoleEnum.ROLE_ADMIN),
                    true,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
            userRepository.save(abdulqodir);

            User john = new User(
                    "John",
                    "j@gmail.com",
                    passwordEncoder.encode("1234"),
                    new Role(RoleEnum.ROLE_CREATOR),
                    false,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
            userRepository.save(john);
            /* SAVING USER END */

            /* SAVING CATEGORIES */
            ArrayList<Category> categories = new ArrayList<>(
                    Arrays.asList(
                            new Category("Art", true),
                            new Category("Games", true),
                            new Category("Celebrities", true),
                            new Category("Music", false),
                            new Category("Sport", false)
                    )
            );
            categoryRepository.saveAll(categories);
            /* SAVING CATEGORIES END */

        }
    }
}
