package com.itransition.coursework.tag;

import com.itransition.coursework.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Abdulqodir Ganiev 7/11/2022 10:51 PM
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {

    private final ItemService itemService;

    @GetMapping("{tagId}")
    public String getTagItems(@PathVariable Long tagId,
                              Model model) {
        model.addAttribute("items",
                itemService.getSingleTagItems(tagId));
        return "client/item/single-tag-items";
    }
}
