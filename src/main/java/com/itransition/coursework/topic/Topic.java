package com.itransition.coursework.topic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itransition.coursework.collection.Collection;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

import javax.persistence.*;
import java.util.List;

/**
 * Abdulqodir Ganiev 6/13/2022 3:52 PM
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @FullTextField
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Boolean isEnabled;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Collection> collections;

    public Topic(String name, Boolean isEnabled) {
        this.name = name;
        this.isEnabled = isEnabled;
    }

}
