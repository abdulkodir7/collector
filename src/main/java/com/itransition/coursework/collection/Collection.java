package com.itransition.coursework.collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itransition.coursework.custom_field.CustomField;
import com.itransition.coursework.item.Item;
import com.itransition.coursework.topic.Topic;
import com.itransition.coursework.user.User;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Abdulqodir Ganiev 6/13/2022 3:51 PM
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @FullTextField
    @Column(nullable = false)
    private String name;

    @FullTextField
    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @IndexedEmbedded
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    private Topic topic;

    @IndexedEmbedded
    @ManyToOne(optional = false)
    @JsonIgnore
    private User author;

    @IndexedEmbedded
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "collection_custom_fields",
            joinColumns = @JoinColumn(name = "collection_id"),
            inverseJoinColumns = @JoinColumn(name = "custom_fields_id"))
    @JsonIgnore
    private List<CustomField> customFields;

    @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<Item> items;

    @Column(columnDefinition = "text")
    private String imgUrl;

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Collection that = (Collection) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
