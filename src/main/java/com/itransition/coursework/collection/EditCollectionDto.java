package com.itransition.coursework.collection;

import com.itransition.coursework.topic.Topic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * Abdulqodir Ganiev 6/28/2022 5:03 PM
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EditCollectionDto {
    private String name;
    private String description;
    private Long topicId;
    private MultipartFile image;
}
