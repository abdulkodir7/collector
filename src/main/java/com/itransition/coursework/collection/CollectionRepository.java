package com.itransition.coursework.collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection, Long> {

    @Query(nativeQuery = true,
            value = "select c.id id, " +
                    "       c.name name, " +
                    "       t.id topicId, " +
                    "       t.name topicName, " +
                    "       u.id authorId, " +
                    "       u.name authorName, " +
                    "       count(i) itemsCount " +
                    "from collection c " +
                    "         left join item i on c.id = i.collection_id " +
                    "         left join topic t on t.id = c.topic_id " +
                    "         left join users u on u.id = c.author_id " +
                    "group by c.id, t.id, u.id")
    List<CollectionView> getAllCollectionsForAdmin();
}
