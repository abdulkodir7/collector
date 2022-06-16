package com.itransition.coursework.collection;

import com.itransition.coursework.category.Category;
import com.itransition.coursework.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Abdulqodir Ganiev 6/13/2022 3:51 PM
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @ManyToOne(optional = false)
    private Category category;

    @ManyToOne(optional = false)
    private User owner;

    @Column(columnDefinition = "text")
    private String imgUrl;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();
}
