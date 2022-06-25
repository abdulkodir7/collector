package com.itransition.coursework.util;

import com.itransition.coursework.custom_field.*;
import com.itransition.coursework.item.ItemRepository;
import com.itransition.coursework.tag.Tag;
import com.itransition.coursework.tag.TagRepository;
import com.itransition.coursework.topic.Topic;
import com.itransition.coursework.topic.TopicRepository;
import com.itransition.coursework.collection.Collection;
import com.itransition.coursework.collection.CollectionRepository;
import com.itransition.coursework.item.Item;
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
                            new Topic("Art", true),
                            new Topic("Games", true),
                            new Topic("Celebrities", true),
                            new Topic("Music", true),
                            new Topic("Sport", true)
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
                            new Tag("#beauty"),
                            new Tag("#car"),
                            new Tag("#technology")
                    )
            );
            tagRepository.saveAll(tags);

            /* COLLECTIONS */
            Collection soccerJersey = Collection.builder()
                    .name("Soccer jersey")
                    .description("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
                    .topic(categories.get(4))
                    .author(john)
                    .imgUrl("https://www.dhresource.com/f2/albu/g20/M00/2B/59/rBVaqWIXSCKABZnlAAIK5V4_oCo327.jpg")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            Collection barcelonaPlayers = Collection.builder()
                    .name("Barcelona players")
                    .description("The list includes notable footballers who have played for Barcelona. Generally, this means players that have played at least 100 league matches for the club ...")
                    .topic(categories.get(4))
                    .author(abdulqodir)
                    .imgUrl("https://www.fcbarcelonanoticias.com/uploads/s1/11/95/80/6/foto-de-equipo-del-fc-barcelona-slavia.jpeg")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            collectionRepository.save(barcelonaPlayers);
            collectionRepository.save(soccerJersey);

            /* CUSTOM FIELD*/
            CustomField age = new CustomField(
                    "Age",
                    barcelonaPlayers,
                    CustomFieldType.number
            );

            CustomField image = new CustomField(
                    "Image",
                    barcelonaPlayers,
                    CustomFieldType.file
            );
            customFieldRepository.save(age);
            customFieldRepository.save(image);

            /* ITEMS */
            Item leonelMessi = Item.builder()
                    .name("Leonel Messi")
                    .collection(barcelonaPlayers)
                    .likedBy(Collections.singletonList(abdulqodir))
                    .tags(tags)
                    .build();
            itemRepository.save(leonelMessi);

            /* CUSTOM FIELD VALUE */
            CustomFieldValue customFieldValue = new CustomFieldValue(
                    age,
                    leonelMessi,
                    "36"
            );

            CustomFieldValue customFieldValue1 = new CustomFieldValue(
                    image,
                    leonelMessi,
                    "https://www.ligue1.com/-/media/Project/LFP/shared/Images/Players/2021-2022/00/94700.jpg"
            );

            customFieldValueRepository.save(customFieldValue);
            customFieldValueRepository.save(customFieldValue1);


        }
    }
}
