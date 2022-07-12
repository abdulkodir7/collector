package com.itransition.coursework.custom_field.custom_field_value;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itransition.coursework.custom_field.CustomField;
import com.itransition.coursework.item.Item;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

import javax.persistence.*;
import java.util.Objects;

/**
 * Abdulqodir Ganiev 6/23/2022 8:43 PM
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class CustomFieldValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private CustomField customField;

    @ManyToOne
    @JsonIgnore
    private Item item;

    @FullTextField
    @Column(nullable = false)
    private String value;

}
