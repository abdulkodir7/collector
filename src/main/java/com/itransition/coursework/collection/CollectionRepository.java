package com.itransition.coursework.collection;

import com.itransition.coursework.collection.projection.CollectionView;
import com.itransition.coursework.collection.projection.SingleCollectionView;
import com.itransition.coursework.collection.projection.TopCollectionView;
import com.itransition.coursework.search.SearchRepository;
import com.itransition.coursework.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CollectionRepository extends SearchRepository<Collection, Long> {

    @Query(nativeQuery = true,
            value = "select c.id, " +
                    "       c.name, " +
                    "       c.img_url imgUrl, " +
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
    List<CollectionView> getAllCollections();

    @Query(nativeQuery = true,
            value = "select c.id, " +
                    "       c.name, " +
                    "       c.description, " +
                    "       c.img_url imgUrl," +
                    "       t.id         topicId, " +
                    "       t.name       topicName, " +
                    "       u.id         authorId, " +
                    "       u.img_url    authorImgUrl, " +
                    "       u.name       authorName, " +
                    "       count(i)     itemsCount, " +
                    "       c.created_at createdAt, " +
                    "       c.updated_at updatedAt " +
                    "from collection c " +
                    "         left join users u on u.id = c.author_id " +
                    "         left join topic t on t.id = c.topic_id " +
                    "         left join item i on c.id = i.collection_id " +
                    "where c.id = :id " +
                    "group by c.id, t.id, u.id;")
    Optional<SingleCollectionView> getCollectionWithCustomFields(Long id);

    @Query(nativeQuery = true,
            value = "select c.id, " +
                    "       c.name, " +
                    "       c.img_url imgUrl, " +
                    "       t.name topicName, " +
                    "       u.name author, " +
                    "       count(i) itemsCount " +
                    "from collection c " +
                    "         left join topic t on t.id = c.topic_id " +
                    "         left join users u on u.id = c.author_id " +
                    "         left join item i on c.id = i.collection_id " +
                    "group by c.id, t.name, u.name " +
                    "order by itemsCount desc " +
                    "limit 5")
    List<TopCollectionView> getTop5();

    @Query(nativeQuery = true,
            value = "select c.id, " +
                    "       c.name, " +
                    "       c.img_url imgUrl, " +
                    "       t.id topicId, " +
                    "       t.name topicName, " +
                    "       u.id authorId, " +
                    "       u.img_url authorImgUrl, " +
                    "       u.name authorName, " +
                    "       count(i) itemsCount " +
                    "from collection c " +
                    "         left join item i on c.id = i.collection_id " +
                    "         left join topic t on t.id = c.topic_id " +
                    "         left join users u on u.id = c.author_id " +
                    "where u.id = :id " +
                    "group by c.id, t.id, u.id")
    List<CollectionView> getAuthorCollections(Long id);

    void deleteCollectionByTopicId(Long topicId);
}
