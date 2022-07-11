package com.itransition.coursework.collection.projection;

import com.itransition.coursework.custom_field.CustomFieldView;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface CollectionView {
    Long getId();

    String getName();

    String getImgUrl();

    Long getTopicId();

    String getTopicName();

    Long getAuthorId();

    String getAuthorImgUrl();

    String getAuthorName();

    Long getItemsCount();
}

