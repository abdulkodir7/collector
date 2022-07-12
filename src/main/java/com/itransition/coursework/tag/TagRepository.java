package com.itransition.coursework.tag;

import com.itransition.coursework.search.SearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends SearchRepository<Tag, Long> {

    @Query(nativeQuery = true,
            value = "select t.id, t.name " +
                    "from tag t " +
                    "         join item_tags it on t.id = it.tags_id " +
                    "         join item i on i.id = it.item_id " +
                    "where i.id = :id")
    List<TagView> getItemTags(Long id);

    boolean existsByName(String name);
}
