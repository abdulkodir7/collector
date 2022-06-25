package com.itransition.coursework.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(nativeQuery = true,
            value = "select i.id, " +
                    "       i.name, " +
                    "       count(l)                                   as likeCount, " +
                    "       count(c2)                                  as commentCount, " +
                    "       i.created_at                               as createdAt " +
                    "from item i " +
                    "         left join collection c on c.id = i.collection_id " +
                    "         left join users u on u.id = c.author_id " +
                    "         left join item_like il on i.id = il.item_id " +
                    "         left join item_like l on i.id = l.item_id " +
                    "         left join comment c2 on i.id = c2.item_id " +
                    "where c.id = :id " +
                    "group by i.id, c.id,u.name")
    List<ItemView> getItemsOfSingleCollection(Long id);
}
