package com.itransition.coursework.user;

import com.itransition.coursework.comment.Comment;
import com.itransition.coursework.user.role.Role;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Abdulqodir Ganiev 6/13/2022 3:37 PM
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private Role role;

    private Boolean isActive;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<com.itransition.coursework.collection.Collection> collections;

    @OneToMany(mappedBy = "commentedBy", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @Column(updatable = false, nullable = false)
    private LocalDateTime joinedAt;

    @Column(nullable = false)
    private LocalDateTime editedAt;

    public User(String name, String email,
                String password, Role role, Boolean isActive,
                LocalDateTime joinedAt, LocalDateTime editedAt) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
        this.joinedAt = joinedAt;
        this.editedAt = editedAt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.getName().name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
