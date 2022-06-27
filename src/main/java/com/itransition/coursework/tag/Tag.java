package com.itransition.coursework.tag;

import lombok.*;

import javax.persistence.*;

/**
 * Abdulqodir Ganiev 6/13/2022 4:02 PM
 */


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public Tag(String name) {
        this.name = name;
    }
}
