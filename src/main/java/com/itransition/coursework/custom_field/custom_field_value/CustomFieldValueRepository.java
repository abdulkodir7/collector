package com.itransition.coursework.custom_field.custom_field_value;

import com.itransition.coursework.search.SearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomFieldValueRepository extends SearchRepository<CustomFieldValue, Long> {
    @Query(nativeQuery = true,
            value = "select cf.name, cf.type, cfv.value " +
                    "from custom_field_value cfv " +
                    "         join custom_field cf on cf.id = cfv.custom_field_id " +
                    "         join item i on i.id = cfv.item_id " +
                    "where i.id = :itemId")
    List<CustomFieldValueView> getItemCustomFiledValue(Long itemId);
}
