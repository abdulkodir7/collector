package com.itransition.coursework.custom_field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itransition.coursework.collection.Collection;
import com.itransition.coursework.custom_field.custom_field_value.CustomFieldValue;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

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

    @FullTextField
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomFieldType type;

    @OneToMany(mappedBy = "customField", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CustomFieldValue> customFieldValues;

    @ManyToMany(mappedBy = "customFields")
    @JsonIgnore
    private List<Collection> collections;

}
