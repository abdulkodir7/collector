package com.itransition.coursework.custom_field;

import com.itransition.coursework.collection.Collection;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Abdulqodir Ganiev 6/13/2022 4:04 PM
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class CustomField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomFieldType type;

    @OneToMany(mappedBy = "customField", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomFieldValue> customFieldValues;

}
