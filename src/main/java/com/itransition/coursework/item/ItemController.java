package com.itransition.coursework.item;

import com.itransition.coursework.collection.CollectionService;
import com.itransition.coursework.custom_field.CustomField;
import com.itransition.coursework.custom_field.CustomFieldRepository;
import com.itransition.coursework.custom_field.CustomFieldView;
import com.itransition.coursework.item.projection.SingleItemView;
import com.itransition.coursework.tag.Tag;
import com.itransition.coursework.tag.TagService;
import com.itransition.coursework.util.ThymeleafResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Abdulqodir Ganiev 7/2/2022 1:27 AM
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("items")
public class ItemController {

    private final ItemService itemService;
    private final CustomFieldRepository customFieldRepository;
    private final TagService tagService;

    @GetMapping
    public String getAllItems(Model model) {
        model.addAttribute("items", itemService.getAllItems());
        return "client/items";
    }

    @GetMapping("{id}")
    public String getSingleItems(Model model, @PathVariable Long id) {
        SingleItemView singleItem = itemService.getSingleItem(id);
        model.addAttribute("item", singleItem);
        return "client/single-item";
    }

    @GetMapping("/add-item/{collectionId}")
    public String getItemCreatePage(Model model, @PathVariable Long collectionId) {
        List<Tag> tags = tagService.getAllTags();
        List<CustomFieldView> customFields = customFieldRepository.getItemCustomFields(collectionId);
        model.addAttribute("customFields", customFields);
        model.addAttribute("tags", tags);
        model.addAttribute("collectionId", collectionId);
        return "client/item-create";
    }

    @PostMapping("/create")
    public String saveItem(MultipartFile file,
                           HttpServletRequest request,
                           RedirectAttributes attributes) {
        String collectionId = request.getParameter("collectionId");
        ThymeleafResponse response = itemService.saveItem(file, request);
        attributes.addFlashAttribute("response", response);
        return "redirect:/collections/" + collectionId;
    }
}
