package com.itransition.coursework.item;

import com.itransition.coursework.collection.Collection;
import com.itransition.coursework.tag.Tag;
import com.itransition.coursework.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Abdulqodir Ganiev 6/13/2022 4:00 PM
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    private Collection collection;

    @ManyToMany
    private List<Tag> tags;

    @ManyToMany
    @JoinTable(
            name = "item_like",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> likedBy;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

}
