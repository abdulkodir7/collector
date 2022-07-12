package com.itransition.coursework.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itransition.coursework.item.Item;
import com.itransition.coursework.user.User;
import lombok.*;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Abdulqodir Ganiev 6/13/2022 4:12 PM
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @FullTextField
    @Column(nullable = false, columnDefinition = "text")
    private String body;

    @ManyToOne(optional = false)
    @JsonIgnore
    private Item item;

    @IndexedEmbedded
    @ManyToOne(optional = false)
    private User commentedBy;

    private LocalDateTime commentedAt = LocalDateTime.now();
}
