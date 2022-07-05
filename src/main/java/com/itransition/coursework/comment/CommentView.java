package com.itransition.coursework.comment;

import java.time.LocalDateTime;

public interface CommentView {
    Long getId();

    String getBody();

    String getCommentedBy();

    String getCommentedByImgUrl();

    Long getCommentedById();

    LocalDateTime getCommentedAt();

}
