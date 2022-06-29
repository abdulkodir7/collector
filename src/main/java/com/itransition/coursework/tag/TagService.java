package com.itransition.coursework.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Abdulqodir Ganiev 6/25/2022 7:00 PM
 */

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public List<Tag> getTagNonSelectedTags(List<TagView> tags) {
        List<Tag> tagList = new ArrayList<>();
        for (Tag allTag : getAllTags())
            for (TagView tag : tags)
                if (!Objects.equals(allTag.getId(), tag.getId()))
                    tagList.add(allTag);
        System.out.println(tagList);
        return tagList;

    }
}