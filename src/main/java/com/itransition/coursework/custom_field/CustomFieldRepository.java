package com.itransition.coursework.custom_field;

import com.itransition.coursework.search.SearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomFieldRepository extends SearchRepository<CustomField, Long> {

    @Query(nativeQuery = true,
            value = "select cf.id, " +
                    "       cf.name, " +
                    "       cf.type " +
                    "from custom_field cf " +
                    "         join collection_custom_fields ccf on cf.id = ccf.custom_fields_id " +
                    "         join collection c on c.id = ccf.collection_id " +
                    "where c.id = :collectionId")
    List<CustomFieldView> getItemCustomFields(Long collectionId);

    Optional<CustomField> getByNameAndType(String name, CustomFieldType type);
}
