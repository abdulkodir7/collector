package com.itransition.coursework.category;

import com.itransition.coursework.user.User;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @NonNull
    Page<Category> findAll(@NonNull Pageable pageable);

    boolean existsByName(String name);
}
