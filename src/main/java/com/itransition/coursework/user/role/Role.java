package com.itransition.coursework.user.role;

import com.itransition.coursework.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Abdulqodir Ganiev 6/20/2022 8:34 PM
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleEnum name;

    @OneToMany(mappedBy = "role")
    private List<User> users;

    public Role(RoleEnum name) {
        this.name = name;
    }
}
