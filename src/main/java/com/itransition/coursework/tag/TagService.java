package com.itransition.coursework.tag;

import com.itransition.coursework.util.ThymeleafResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.itransition.coursework.util.Constants.*;

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


    public ThymeleafResponse delete(Long id) {
        try {
            Tag tag = tagRepository.findById(id)
                    .orElseThrow(() -> new ResourceAccessException(TAG_NOT_FOUND));
            tag.getItems().clear();
            tagRepository.save(tag);
            tagRepository.delete(tag);
            return new ThymeleafResponse(true, SUCCESS_DELETE);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getMessage());
        }
    }

    public ThymeleafResponse edit(Long id, String name) {
        try {
            Tag tag = tagRepository.findById(id)
                    .orElseThrow(() -> new ResourceAccessException(TAG_NOT_FOUND));
            if (tagRepository.existsByName(name))
                return new ThymeleafResponse(false, TAG_EXISTS);
            tag.setName(name);
            tagRepository.save(tag);
            return new ThymeleafResponse(true, SUCCESS_DELETE);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getMessage());
        }
    }

    public ThymeleafResponse create(String name) {
        if (tagRepository.existsByName(name))
            return new ThymeleafResponse(false, TAG_EXISTS);
        tagRepository.save(new Tag(name));
        return new ThymeleafResponse(true, SUCCESS_MESSAGE);
    }
}