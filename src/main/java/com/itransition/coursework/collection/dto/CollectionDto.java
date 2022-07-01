package com.itransition.coursework.collection.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Abdulqodir Ganiev 6/24/2022 2:12 AM
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CollectionDto {
    private String name;
    private Integer topic;
    private String description;
    private MultipartFile image;
    private List<CustomFieldDto> customFields;
}
