package com.itransition.coursework.collection;

import com.itransition.coursework.custom_field.CustomField;
import com.itransition.coursework.topic.Topic;
import com.itransition.coursework.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    private Topic topic;

    @ManyToOne(optional = false)
    private User author;

    @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL)
    private List<CustomField> customFields;

    @Column(columnDefinition = "text")
    private String imgUrl;

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
