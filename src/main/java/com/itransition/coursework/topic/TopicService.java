package com.itransition.coursework.topic;

import com.itransition.coursework.util.ThymeleafResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

import static com.itransition.coursework.util.Constants.*;

/**
 * Abdulqodir Ganiev 6/16/2022 10:12 PM
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;

    public Page<Topic> getAllTopics(int size, int page) {
        Pageable pageable = PageRequest.of(
                page - 1,
                size
        );
        return topicRepository.findAll(pageable);
    }

    public Integer getTopicsSize(){
        return topicRepository.findAll().size();
    }

    public ThymeleafResponse saveTopic(Long id, String name) {
        if (id != null)
            return editTopic(id, name);
        else
            return addNewTopic(name);
    }

    private ThymeleafResponse addNewTopic(String name) {
        if (topicRepository.existsByName(name))
            return new ThymeleafResponse(false, TOPIC_EXISTS);
        Topic topic = topicRepository.save(new Topic(name, true));
        log.info("created new topic {}", topic);
        return new ThymeleafResponse(true, SUCCESS_MESSAGE);
    }

    private ThymeleafResponse editTopic(Long id, String name) {
        try {
            Topic topic = topicRepository.findById(id)
                    .orElseThrow(() -> new ResourceAccessException(TOPIC_NOT_FOUND));
            topic.setName(name);
            Topic savedTopic = topicRepository.save(topic);
            log.info("topic edited {}", savedTopic);
            return new ThymeleafResponse(true, SUCCESS_CHANGE);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getMessage());
        }
    }

    public ThymeleafResponse deleteTopic(Long id) {
        try {
            Topic topic = topicRepository.findById(id)
                    .orElseThrow(() -> new ResourceAccessException(TOPIC_NOT_FOUND));
            topic.getCollections().clear();
            topicRepository.save(topic);
            topicRepository.delete(topic);
            log.info("topic {} deleted", topic);
            return new ThymeleafResponse(true, SUCCESS_DELETE);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getMessage());
        }
    }

    public ThymeleafResponse disableTopic(Long id) {
        try {
            Topic topic = topicRepository.findById(id)
                    .orElseThrow(() -> new ResourceAccessException(TOPIC_NOT_FOUND));
            topic.setIsEnabled(false);
            topicRepository.save(topic);
            log.info("topic {} disabled", topic);
            return new ThymeleafResponse(true, SUCCESS_CHANGE);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getMessage());
        }
    }

    public ThymeleafResponse enableTopic(Long id) {
        try {
            Topic topic = topicRepository.findById(id)
                    .orElseThrow(() -> new ResourceAccessException(TOPIC_NOT_FOUND));
            topic.setIsEnabled(true);
            topicRepository.save(topic);
            log.info("topic {} enabled", topic);
            return new ThymeleafResponse(true, SUCCESS_CHANGE);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getMessage());
        }
    }

    public List<Topic> getAllEnabledTopics() {
        return topicRepository.findByIsEnabled(true);
    }
}
