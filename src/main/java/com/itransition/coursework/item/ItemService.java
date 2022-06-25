package com.itransition.coursework.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Abdulqodir Ganiev 6/24/2022 12:12 AM
 */

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<ItemView> getItemsOfSingleCollection(Long id) {
        return itemRepository.getItemsOfSingleCollection(id);
    }
}
