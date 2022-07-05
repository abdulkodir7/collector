package com.itransition.coursework.collection;

import com.itransition.coursework.attachment.AttachmentService;
import com.itransition.coursework.collection.dto.EditCollectionDto;
import com.itransition.coursework.collection.projection.CollectionView;
import com.itransition.coursework.collection.projection.SingleCollectionView;
import com.itransition.coursework.collection.projection.TopCollectionView;
import com.itransition.coursework.comment.CommentRepository;
import com.itransition.coursework.custom_field.CustomField;
import com.itransition.coursework.custom_field.CustomFieldRepository;
import com.itransition.coursework.custom_field.CustomFieldType;
import com.itransition.coursework.item.ItemRepository;
import com.itransition.coursework.topic.Topic;
import com.itransition.coursework.topic.TopicRepository;
import com.itransition.coursework.user.User;
import com.itransition.coursework.util.ThymeleafResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.itransition.coursework.util.Constants.*;

/**
 * Abdulqodir Ganiev 6/21/2022 7:05 PM
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private final TopicRepository topicRepository;
    private final CustomFieldRepository customFieldRepository;
    private final AttachmentService attachmentService;
    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;

    public List<CollectionView> getAllCollectionsForAdmin() {
        return collectionRepository.getAllCollectionsForAdmin();
    }

    public ThymeleafResponse saveCollection(MultipartFile file, HttpServletRequest request, User currentUser) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            String name = request.getParameter("name");
            parameterMap.remove("name");
            String topicId = request.getParameter("topicId");
            parameterMap.remove("topicId");
            String description = request.getParameter("description");
            parameterMap.remove("description");

            if (topicId.isEmpty() || name.isEmpty() || description.isEmpty())
                return new ThymeleafResponse(false, REQUIRED_FIELDS_NULL);

            Topic topic = topicRepository.findById(Long.parseLong(topicId))
                    .orElseThrow(() -> new ResourceAccessException(TOPIC_NOT_FOUND));

            String imgUrl = attachmentService.uploadImage(file);
            Collection savedCollection = createNewCollection(currentUser, name, description, topic, imgUrl);
            createCustomField(request, parameterMap, savedCollection);
            return new ThymeleafResponse(true, SUCCESS_MESSAGE);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getCause().toString());
        }
    }

    private Collection createNewCollection(User currentUser, String name,
                                           String description, Topic topic,
                                           String imgUrl) {
        Collection newCollection = Collection
                .builder()
                .name(name)
                .description(description)
                .topic(topic)
                .author(currentUser)
                .imgUrl(imgUrl)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return collectionRepository.save(newCollection);
    }

    private void createCustomField(HttpServletRequest request,
                                   Map<String, String[]> parameterMap,
                                   Collection savedCollection) {
        ArrayList<CustomField> customFields = new ArrayList<>();
        for (int i = 0; i < parameterMap.size() / 2; i++) {
            String customFieldName = request.getParameter("customFields[" + i + "][name]");
            String customFieldType = request.getParameter("customFields[" + i + "][type]");

            if (!customFieldName.isEmpty() && !customFieldType.isEmpty()) {
                Optional<CustomField> optionalCustomField = customFieldRepository
                        .getByNameAndType(customFieldName, CustomFieldType.valueOf(customFieldType));
                if (optionalCustomField.isPresent()) {
                    customFields.add(optionalCustomField.get());
                } else {
                    CustomField newCustomField = CustomField
                            .builder()
                            .type(CustomFieldType.valueOf(customFieldType))
                            .name(customFieldName)
                            .build();
                    customFields.add(customFieldRepository.save(newCustomField));
                }
            }
        }
        savedCollection.setCustomFields(customFields);
        collectionRepository.save(savedCollection);
    }


    public SingleCollectionView getSingleCollection(Long id) {
        try {
            return collectionRepository.getCollectionWithCustomFields(id)
                    .orElseThrow(() -> new ResourceAccessException(COLLECTION_NOT_FOUND));
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public ThymeleafResponse deleteCollection(Long id) {
        try {
            Collection collection = collectionRepository.findById(id)
                    .orElseThrow(() -> new ResourceAccessException(COLLECTION_NOT_FOUND));
            itemRepository.deleteAllByCollectionId(id);
            collectionRepository.delete(collection);
            return new ThymeleafResponse(true, SUCCESS_DELETE);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getMessage());
        }
    }

    public List<TopCollectionView> getTop5BiggestCollections() {
        return collectionRepository.getTop5();
    }


    public ThymeleafResponse editCollection(Long id, EditCollectionDto dto) {
        try {
            Collection collection = collectionRepository.findById(id)
                    .orElseThrow(() -> new ResourceAccessException(COLLECTION_NOT_FOUND));
            if (dto.getTopicId() == null)
                return new ThymeleafResponse(false, TOPIC_NOT_FOUND);
            Topic topic = topicRepository.findById(dto.getTopicId())
                    .orElseThrow(() -> new ResourceAccessException(TOPIC_NOT_FOUND));
            collection.setName(dto.getName());
            collection.setDescription(dto.getDescription());
            collection.setTopic(topic);
            if (!dto.getImage().isEmpty()) {
                String imageUrl = attachmentService.uploadImage(dto.getImage());
                collection.setImgUrl(imageUrl);
            }
            collection.setUpdatedAt(LocalDateTime.now());
            collectionRepository.save(collection);
            return new ThymeleafResponse(true, SUCCESS_MESSAGE);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getMessage());
        }
    }

    public List<CollectionView> getAuthorCollections(Long id) {
        return collectionRepository.getAuthorCollections(id);
    }
}
