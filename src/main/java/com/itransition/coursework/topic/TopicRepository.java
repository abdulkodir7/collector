package com.itransition.coursework.topic;

import com.itransition.coursework.search.SearchRepository;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends SearchRepository<Topic, Long> {

    @NonNull
    Page<com.itransition.coursework.topic.Topic> findAll(@NonNull Pageable pageable);

    boolean existsByName(String name);
    boolean existsById(Long id);

    List<Topic> findByIsEnabled(Boolean isEnabled);

}
