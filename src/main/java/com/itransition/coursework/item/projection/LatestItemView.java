package com.itransition.coursework.item.projection;

import java.time.LocalDateTime;

public interface LatestItemView {
    Long getId();

    String getName();

    LocalDateTime getCreatedAt();
}
