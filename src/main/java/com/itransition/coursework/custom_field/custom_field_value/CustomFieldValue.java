package com.itransition.coursework.custom_field.custom_field_value;

import com.itransition.coursework.custom_field.CustomField;
import com.itransition.coursework.item.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Abdulqodir Ganiev 6/23/2022 8:43 PM
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class CustomFieldValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CustomField customField;

    @ManyToOne
    private Item item;

    @Column(nullable = false)
    private String value;

}
