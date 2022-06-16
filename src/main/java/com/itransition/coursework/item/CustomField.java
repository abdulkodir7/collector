package com.itransition.coursework.item;

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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomFieldType type;

    @Column(nullable = false)
    private String value;
}
