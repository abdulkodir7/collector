package com.itransition.coursework.collection.projection;

import com.itransition.coursework.custom_field.CustomFieldView;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.List;

public interface SingleCollectionView {
    Long getId();

    String getName();

    String getDescription();

    String getImgUrl();

    Long getTopicId();

    String getTopicName();

    Long getAuthorId();

    String getAuthorImgUrl();

    String getAuthorName();

    Long getItemsCount();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

    @Value("#{@customFieldRepository.getItemCustomFields({target.id})}")
    List<CustomFieldView> getCustomFields();
}
