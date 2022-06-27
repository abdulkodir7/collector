package com.itransition.coursework.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Abdulqodir Ganiev 6/27/2022 2:07 PM
 */

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(nativeQuery = true,
            value = "select c.id, " +
                    "       c.body, " +
                    "       c.commented_at commentedAt, " +
                    "       u.name commentedBy, " +
                    "       u.id commentedById " +
                    "from comment c " +
                    "         join item i on i.id = c.item_id " +
                    "         join users u on u.id = c.commented_by_id " +
                    "where i.id = :id " +
                    "order by c.commented_at desc ")
    List<CommentView> getItemComments(Long id);
}
