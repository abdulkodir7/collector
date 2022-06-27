package com.itransition.coursework.comment;

import java.time.LocalDateTime;

public interface CommentView {
    Long getId();

    String getBody();

    String getCommentedBy();

    Long getCommentedById();

    LocalDateTime getCommentedAt();

}
