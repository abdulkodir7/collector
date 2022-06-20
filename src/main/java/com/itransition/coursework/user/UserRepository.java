package com.itransition.coursework.user;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Abdulqodir Ganiev 6/16/2022 7:07 PM
 */

public interface UserRepository extends JpaRepository<User, Long> {

    @NonNull
    Page<User> findAll(@NonNull Pageable pageable);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
