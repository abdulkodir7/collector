package com.itransition.coursework.collection;

import com.itransition.coursework.custom_field.CustomFieldView;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.List;

public interface SingleCollectionView {
    Long getId();

    String getName();

    String getDescription();

    String getImgUrl();

    // TODO: 6/25/2022 Collectionlar ni kurganda topic, avtor utiga bosganda single page utsin
    Long getTopicId();

    String getTopicName();

    Long getAuthorId();

    String getAuthorName();

    Long getItemsCount();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

    @Value("#{@customFieldRepository.getItemCustomFields({target.id})}")
    List<CustomFieldView> getCustomFields();
}
