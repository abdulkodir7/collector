package com.itransition.coursework.util;

import com.itransition.coursework.collection.Collection;
import com.itransition.coursework.collection.CollectionRepository;
import com.itransition.coursework.custom_field.CustomFieldRepository;
import com.itransition.coursework.custom_field.CustomFieldValueRepository;
import com.itransition.coursework.item.Item;
import com.itransition.coursework.item.ItemRepository;
import com.itransition.coursework.tag.Tag;
import com.itransition.coursework.tag.TagRepository;
import com.itransition.coursework.topic.Topic;
import com.itransition.coursework.topic.TopicRepository;
import com.itransition.coursework.user.User;
import com.itransition.coursework.user.UserRepository;
import com.itransition.coursework.user.role.Role;
import com.itransition.coursework.user.role.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
    private final CollectionRepository collectionRepository;
    private final CustomFieldRepository customFieldRepository;
    private final CustomFieldValueRepository customFieldValueRepository;
    private final ItemRepository itemRepository;

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

            /* CATEGORIES */
            ArrayList<Topic> categories = new ArrayList<>(
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
            topicRepository.saveAll(categories);

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
