package com.itransition.coursework.item;

import com.itransition.coursework.tag.TagView;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemView {
    Long getId();

    String getName();

    String getCollectionName();

    @Value("#{@tagRepository.getItemTags({target.id})}")
    List<TagView> getTags();

    Long getLikeCount();

    Long getCommentCount();

    LocalDateTime getCreatedAt();
}
