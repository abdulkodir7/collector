package com.itransition.coursework.collection;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.itransition.coursework.custom_field.CustomField;
import com.itransition.coursework.custom_field.CustomFieldRepository;
import com.itransition.coursework.custom_field.CustomFieldType;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private final Cloudinary cloudinary;
    private final CustomFieldRepository customFieldRepository;

    public List<CollectionView> getAllCollectionsForAdmin() {
        return collectionRepository.getAllCollectionsForAdmin();
    }

    public ThymeleafResponse saveCollection(MultipartFile file, HttpServletRequest request, User currentUser) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            String name = request.getParameter("name");
            String topicId = request.getParameter("topicId");
            String description = request.getParameter("description");

            if (topicId == null || name == null || description == null)
                return new ThymeleafResponse(false, REQUIRED_FIELDS_NULL);

            Topic topic = topicRepository.findById(Long.parseLong(topicId))
                    .orElseThrow(() -> new ResourceAccessException(TOPIC_NOT_FOUND));

            String imgUrl = "https://efectocolibri.com/wp-content/uploads/2021/01/placeholder.png";
            imgUrl = uploadImage(file, imgUrl);

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
            Collection savedCollection = collectionRepository.save(newCollection);

            createCustomField(request, parameterMap, savedCollection);
            return new ThymeleafResponse(true, SUCCESS_MESSAGE);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getMessage());
        }
    }

    private String uploadImage(MultipartFile file, String imgUrl) throws IOException {
        if (file != null) {
            File image = convert(file);
            Map uploadResult = cloudinary.uploader().upload(image, ObjectUtils.emptyMap());
            imgUrl = (String) uploadResult.get("url");
            System.out.println(imgUrl);
        }
        return imgUrl;
    }

    private void createCustomField(HttpServletRequest request, Map<String, String[]> parameterMap, Collection savedCollection) {
        for (int i = 0; i < parameterMap.size(); i++) {
            String customFieldName = request.getParameter("customFields[" + i + "][name]");
            String customFieldType = request.getParameter("customFields[" + i + "][type]");

            if (!customFieldName.isEmpty() && !customFieldType.isEmpty()) {
                CustomField newCustomField = CustomField
                        .builder()
                        .name(customFieldName)
                        .type(CustomFieldType.valueOf(customFieldType))
                        .collection(savedCollection)
                        .build();
                customFieldRepository.save(newCustomField);
            }
        }
    }

    private File convert(MultipartFile image) {
        try {
            File file = new File(Objects.requireNonNull(
                    image.getOriginalFilename()
            ));
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(image.getBytes());
            fileOutputStream.close();
            return file;
        } catch (Exception e) {
            log.info("cannot convert MultiPartFile to File {}", e.getMessage());
            return null;
        }
    }


    public Collection getSingleCollection(Long id) {
        try {
            return collectionRepository.findById(id)
                    .orElseThrow(() -> new ResourceAccessException(COLLECTION_NOT_FOUND));
        } catch (Exception e) {
            return null;
        }
    }
}
