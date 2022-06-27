package com.itransition.coursework.comment;

import com.itransition.coursework.item.Item;
import com.itransition.coursework.user.User;
import lombok.*;

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

    @Column(nullable = false, columnDefinition = "text")
    private String body;

    @ManyToOne(optional = false)
    private Item item;

    @ManyToOne(optional = false)
    private User commentedBy;

    private LocalDateTime commentedAt = LocalDateTime.now();
}
