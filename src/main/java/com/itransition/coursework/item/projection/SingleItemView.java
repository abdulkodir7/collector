package com.itransition.coursework.item.projection;

import com.itransition.coursework.comment.CommentView;
import com.itransition.coursework.custom_field.custom_field_value.CustomFieldValueView;
import com.itransition.coursework.item.projection.LikeView;
import com.itransition.coursework.tag.TagView;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.List;

public interface SingleItemView {
    Long getId();

    String getName();

    Long getCollectionId();

    String getCollectionName();

    String getTopicName();

    String getAuthor();

    Long getAuthorId();

    @Value("#{@tagRepository.getItemTags({target.id})}")
    List<TagView> getTags();

    @Value("#{@itemRepository.getLikedUsers({target.id})}")
    List<LikeView> getLikedUsers();

    @Value("#{@commentRepository.getItemComments({target.id})}")
    List<CommentView> getComments();

    @Value("#{@customFieldValueRepository.getItemCustomFiledValue({target.id})}")
    List<CustomFieldValueView> getCustomFields();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();
}
