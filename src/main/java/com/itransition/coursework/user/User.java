package com.itransition.coursework.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

/**
 * Abdulqodir Ganiev 6/13/2022 3:37 PM
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
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
}
