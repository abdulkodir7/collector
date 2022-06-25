package com.itransition.coursework.custom_field;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomFieldRepository extends JpaRepository<CustomField, Long> {

    @Query(nativeQuery = true,
            value = "select cf.id,\n" +
                    "       cf.name,\n" +
                    "       cf.type\n" +
                    "from custom_field cf\n" +
                    "         join collection c on c.id = cf.collection_id\n" +
                    "where c.id = :collectionId")
    List<CustomFieldValue> getItemCustomFields(Long collectionId);
}
