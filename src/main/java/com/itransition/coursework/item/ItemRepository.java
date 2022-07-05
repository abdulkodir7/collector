package com.itransition.coursework.item;

import com.itransition.coursework.item.projection.ItemView;
import com.itransition.coursework.item.projection.LatestItemView;
import com.itransition.coursework.item.projection.LikeView;
import com.itransition.coursework.item.projection.SingleItemView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(nativeQuery = true,
            value = "select i.id, " +
                    "       i.name, " +
                    "       c.author_id authorId, " +
                    "       count(distinct il) likeCount, " +
                    "       count(distinct com) commentCount, " +
                    "       i.created_at createdAt " +
                    "from item i " +
                    "         left join item_like il on i.id = il.item_id " +
                    "         left join comment com on i.id = com.item_id " +
                    "         join collection c on c.id = i.collection_id " +
                    "where c.id = :id " +
                    "group by i.id, c.author_id")
    List<ItemView> getItemsOfSingleCollection(Long id);

    void deleteAllByCollectionId(Long collection_id);

    @Query(nativeQuery = true,
            value = "select u.id, " +
                    "u.name, " +
                    "u.img_url imgUrl " +
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
                    "       u.name              authorName, " +
                    "       u.img_url           authorImgUrl, " +
                    "       count(distinct il)  likeCount, " +
                    "       count(distinct com) commentCount, " +
                    "       i.created_at        createdAt " +
                    "from item i " +
                    "         left join item_like il on i.id = il.item_id " +
                    "         left join comment com on i.id = com.item_id " +
                    "         join collection c on c.id = i.collection_id " +
                    "         join users u on c.author_id = u.id " +
                    "group by i.id, c.name, u.name, u.img_url ")
    List<ItemView> getAllItems();

    @Query(nativeQuery = true,
            value = "select id, " +
                    "       name, " +
                    "       cast(extract(epoch from (item.created_at - now() at time zone 'utc' at time zone 'asia/tashkent') / 60) as varchar) minutes " +
                    "from item " +
                    "limit 6")
    List<LatestItemView> getLatestItems();
}
