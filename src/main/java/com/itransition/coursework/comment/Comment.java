package com.itransition.coursework.comment;

import com.itransition.coursework.item.Item;
import com.itransition.coursework.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Abdulqodir Ganiev 6/13/2022 4:12 PM
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "text")
    private String body;

    @ManyToOne(optional = false)
    private Item item;

    @ManyToOne(optional = false)
    private User commentedBy;

    private LocalDateTime commentedAt = LocalDateTime.now();
}
