package com.itransition.coursework.custom_field;

import com.itransition.coursework.item.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Abdulqodir Ganiev 6/23/2022 8:43 PM
 */

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

    public CustomFieldValue(CustomField customField, Item item, String value) {
        this.customField = customField;
        this.item = item;
        this.value = value;
    }
}
