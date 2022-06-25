package com.itransition.coursework.custom_field;

import com.itransition.coursework.collection.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Abdulqodir Ganiev 6/13/2022 4:04 PM
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class CustomField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Collection collection;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomFieldType type;

    public CustomField(String name, Collection collection, CustomFieldType type) {
        this.name = name;
        this.collection = collection;
        this.type = type;
    }
}
