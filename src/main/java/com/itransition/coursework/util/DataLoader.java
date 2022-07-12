package com.itransition.coursework.util;

import com.itransition.coursework.collection.CollectionRepository;
import com.itransition.coursework.custom_field.CustomFieldRepository;
import com.itransition.coursework.custom_field.custom_field_value.CustomFieldValueRepository;
import com.itransition.coursework.item.ItemRepository;
import com.itransition.coursework.tag.Tag;
import com.itransition.coursework.tag.TagRepository;
import com.itransition.coursework.topic.Topic;
import com.itransition.coursework.topic.TopicRepository;
import com.itransition.coursework.user.User;
import com.itransition.coursework.user.UserRepository;
import com.itransition.coursework.user.role.Role;
import com.itransition.coursework.user.role.RoleEnum;
import com.itransition.coursework.user.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static com.itransition.coursework.util.Constants.DEFAULT_PROFILE_IMAGE;

/**
 * Abdulqodir Ganiev 6/16/2022 7:05 PM
 */

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TopicRepository topicRepository;
    private final TagRepository tagRepository;
    private final RoleRepository roleRepository;

    @Value("${spring.sql.init.mode}")
    String initMode;

    @Override
    public void run(String... args) {
        if (initMode.equals("always")) {

            Role superAdmin = roleRepository.save(new Role(RoleEnum.ROLE_SUPER_ADMIN));
            Role admin = roleRepository.save(new Role(RoleEnum.ROLE_ADMIN));
            Role creator = roleRepository.save(new Role(RoleEnum.ROLE_CREATOR));

            /* SAVING USER */
            User super_admin = new User(
                    "Super Admin",
                    "sa@gmail.com",
                    DEFAULT_PROFILE_IMAGE,
                    passwordEncoder.encode("1234"),
                    superAdmin,
                    true,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
            userRepository.save(super_admin);

            User abdulqodir = new User(
                    "Abdulkodir",
                    "a@gmail.com",
                    DEFAULT_PROFILE_IMAGE,
                    passwordEncoder.encode("1234"),
                    admin,
                    true,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
            userRepository.save(abdulqodir);

            User john = new User(
                    "John",
                    "j@gmail.com",
                    DEFAULT_PROFILE_IMAGE,
                    passwordEncoder.encode("1234"),
                    creator,
                    false,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
            userRepository.save(john);

            /* CATEGORIES */
            ArrayList<Topic> topics = new ArrayList<>(
                    Arrays.asList(
                            new Topic("Sport", true),
                            new Topic("Art", true),
                            new Topic("Celebrities", true),
                            new Topic("Music", true),
                            new Topic("Animals", true),
                            new Topic("Cars", true),
                            new Topic("Clothes", true),
                            new Topic("Films", true),
                            new Topic("Meal", true),
                            new Topic("Technology", true)
                    )
            );
            topicRepository.saveAll(topics);

            /* TAGS */
            ArrayList<Tag> tags = new ArrayList<>(
                    Arrays.asList(
                            new Tag("#art"),
                            new Tag("#sport"),
                            new Tag("#book"),
                            new Tag("#animal"),
                            new Tag("#celebrities"),
                            new Tag("#car"),
                            new Tag("#new"),
                            new Tag("#top"),
                            new Tag("#old"),
                            new Tag("#movie"),
                            new Tag("#clothing"),
                            new Tag("#technology")
                    )
            );
            tagRepository.saveAll(tags);

        }
    }
}
