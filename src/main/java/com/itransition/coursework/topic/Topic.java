package com.itransition.coursework.topic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Abdulqodir Ganiev 6/13/2022 3:52 PM
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Boolean isEnabled;

    public Topic(String name, Boolean isEnabled) {
        this.name = name;
        this.isEnabled = isEnabled;
    }

}
