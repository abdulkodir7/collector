package com.itransition.coursework.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itransition.coursework.collection.Collection;
import com.itransition.coursework.collection.CollectionRepository;
import com.itransition.coursework.util.ThymeleafResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static com.itransition.coursework.util.Constants.COLLECTION_NOT_FOUND;

/**
 * Abdulqodir Ganiev 6/24/2022 12:12 AM
 */

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final CollectionRepository collectionRepository;

    public List<ItemView> getItemsOfSingleCollection(Long id) {
        return itemRepository.getItemsOfSingleCollection(id);
    }

    public ThymeleafResponse saveItem(MultipartFile file, HttpServletRequest request) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            String collectionId = request.getParameter("collectionId");
            String collectionName = request.getParameter("collectionName");
            String tagIds = request.getParameter("tagIds");

            Collection collection = collectionRepository.findById(Long.parseLong(collectionId))
                    .orElseThrow(() -> new ResourceAccessException(COLLECTION_NOT_FOUND));


            return null;

        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getMessage());
        }
    }
}
