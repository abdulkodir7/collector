package com.itransition.coursework.item;

import com.itransition.coursework.attachment.AttachmentService;
import com.itransition.coursework.collection.Collection;
import com.itransition.coursework.collection.CollectionRepository;
import com.itransition.coursework.custom_field.*;
import com.itransition.coursework.tag.Tag;
import com.itransition.coursework.tag.TagRepository;
import com.itransition.coursework.util.ThymeleafResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.itransition.coursework.util.Constants.*;

/**
 * Abdulqodir Ganiev 6/24/2022 12:12 AM
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final CollectionRepository collectionRepository;
    private final TagRepository tagRepository;
    private final CustomFieldValueRepository customFieldValueRepository;
    private final AttachmentService attachmentService;
    private final CustomFieldRepository customFieldRepository;

    public List<ItemView> getItemsOfSingleCollection(Long id) {
        return itemRepository.getItemsOfSingleCollection(id);
    }

    public ThymeleafResponse saveItem(MultipartFile file, HttpServletRequest request) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            String collectionId = request.getParameter("collectionId");
            Collection collection = collectionRepository.findById(Long.parseLong(collectionId))
                    .orElseThrow(() -> new ResourceAccessException(COLLECTION_NOT_FOUND));
            String[] strTagIds = parameterMap.get("tagId");
            String itemName = request.getParameter("itemName");
            if (itemName.isEmpty() || strTagIds.length == 0)
                return new ThymeleafResponse(false, REQUIRED_FIELDS_NULL);

            List<Long> tagIds = Arrays.stream(strTagIds)
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            List<Tag> tags = tagRepository.findAllById(tagIds);
            Item savedItem = saveNewItem(collection, itemName, tags);
            saveCustomFieldValues(request, collection, savedItem, file);
            return new ThymeleafResponse(true, SUCCESS_MESSAGE);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getMessage());
        }
    }

    private void saveCustomFieldValues(HttpServletRequest request, Collection collection, Item savedItem, MultipartFile file) {
        try {
            for (CustomField customField : collection.getCustomFields()) {
                CustomFieldValue customFieldValue = new CustomFieldValue();
                customFieldValue.setItem(savedItem);
                customFieldValue.setCustomField(customField);

                String parameter = request.getParameter(customField.getName());
                switch (customField.getType()) {
                    case checkbox:
                        if (parameter != null)
                            customFieldValue.setValue("Yes");
                        else
                            customFieldValue.setValue("No");
                        break;
                    case file:
                        String imgUrl = attachmentService.uploadImage(file);
                        customFieldValue.setValue(imgUrl);
                        break;
                    case date:
                    case number:
                    case text:
                    case textarea:
                        customFieldValue.setValue(parameter);
                        break;
                }
                customFieldValueRepository.save(customFieldValue);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Item saveNewItem(Collection collection, String itemName, List<Tag> tags) {
        Item item = Item
                .builder()
                .name(itemName)
                .collection(collection)
                .tags(tags)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        return itemRepository.save(item);
    }

    public SingleItemView getSingleItem(Long id) {
        try {
            return itemRepository.getSingleItem(id)
                    .orElseThrow(() -> new ResourceAccessException(ITEM_NOT_FOUND));
        } catch (Exception e) {
            log.info("exception {}", e.getMessage());
            return null;
        }
    }

    public ThymeleafResponse deleteItem(Long id) {
        try {
            Item item = itemRepository.findById(id)
                    .orElseThrow(() -> new ResourceAccessException(ITEM_NOT_FOUND));
            itemRepository.delete(item);
            return new ThymeleafResponse(true, SUCCESS_DELETE);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getMessage());
        }
    }


    public ThymeleafResponse editItem(Long id, HttpServletRequest request, MultipartFile file) {
        try {
            Item item = itemRepository.findById(id)
                    .orElseThrow(() -> new ResourceAccessException(ITEM_NOT_FOUND));

            Map<String, String[]> parameterMap = request.getParameterMap();
            String name = request.getParameter("name");
            String[] strTagIds = parameterMap.get("tagId");
            List<Long> tagIds = Arrays.stream(strTagIds)
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            List<Tag> tags = tagRepository.findAllById(tagIds);
            item.setName(name);
            item.setTags(tags);
            item.getCustomFieldValues().removeAll(item.getCustomFieldValues());
            customFieldValueRepository.deleteAll(item.getCustomFieldValues());
            saveCustomFieldValues(request, item.getCollection(), item, file);
            item.setUpdatedAt(LocalDateTime.now());
            itemRepository.save(item);
            return new ThymeleafResponse(true, SUCCESS_MESSAGE);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getMessage());
        }
    }

    public List<ItemView> getAllItems() {
        return itemRepository.getAllItems();
    }
}
