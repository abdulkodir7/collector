package com.itransition.coursework.item;

import com.itransition.coursework.item.projection.ItemView;
import com.itransition.coursework.item.projection.LikeView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(nativeQuery = true,
            value = "select i.id, " +
                    "       i.name, " +
                    "       count(distinct il) likeCount, " +
                    "       count(distinct com) commentCount, " +
                    "       i.created_at createdAt " +
                    "from item i " +
                    "         left join item_like il on i.id = il.item_id " +
                    "         left join comment com on i.id = com.item_id " +
                    "         join collection c on c.id = i.collection_id " +
                    "where c.id = :id " +
                    "group by i.id")
    List<ItemView> getItemsOfSingleCollection(Long id);

    void deleteAllByCollectionId(Long collection_id);

    @Query(nativeQuery = true,
            value = "select u.id, u.name " +
                    "from item i " +
                    "         join item_like il on i.id = il.item_id " +
                    "         join users u on u.id = il.user_id " +
                    "where i.id = :id")
    List<LikeView> getLikedUsers(Long id);

    @Query(nativeQuery = true,
            value = "select i.id, " +
                    "       i.name, " +
                    "       c.id       collectionId, " +
                    "       c.name       collectionName, " +
                    "       t.name       topicName, " +
                    "       u.name       author, " +
                    "       i.updated_at updatedAt, " +
                    "       i.created_at createdAt " +
                    "from item i " +
                    "         join collection c on c.id = i.collection_id " +
                    "         join users u on u.id = c.author_id " +
                    "         join topic t on t.id = c.topic_id " +
                    "where i.id = :id")
    Optional<SingleItemView> getSingleItem(Long id);

    @Query(nativeQuery = true,
            value = "select i.id, " +
                    "       i.name, " +
                    "       c.name              collectionName, " +
                    "       count(distinct il)  likeCount, " +
                    "       count(distinct com) commentCount, " +
                    "       i.created_at        createdAt " +
                    "from item i " +
                    "         left join item_like il on i.id = il.item_id " +
                    "         left join comment com on i.id = com.item_id " +
                    "         join collection c on c.id = i.collection_id " +
                    "group by i.id, c.name")
    List<ItemView> getAllItems();
}
