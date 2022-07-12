package com.itransition.coursework.search;

import com.itransition.coursework.item.Item;
import com.itransition.coursework.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.itransition.coursework.util.Constants.MAX_SEARCH_LIMIT;
import static com.itransition.coursework.util.Constants.SEARCHABLE_FIELDS;

/**
 * Abdulqodir Ganiev 7/12/2022 1:35 PM
 */

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ItemRepository itemRepository;

    public List<Item> searchForAdmin(String text) {
        return itemRepository.searchBy(
                text, MAX_SEARCH_LIMIT, SEARCHABLE_FIELDS);
    }
}
