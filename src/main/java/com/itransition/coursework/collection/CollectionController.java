package com.itransition.coursework.collection;

import com.itransition.coursework.custom_field.CustomFieldType;
import com.itransition.coursework.item.ItemService;
import com.itransition.coursework.item.projection.ItemView;
import com.itransition.coursework.tag.Tag;
import com.itransition.coursework.tag.TagService;
import com.itransition.coursework.topic.Topic;
import com.itransition.coursework.topic.TopicService;
import com.itransition.coursework.user.User;
import com.itransition.coursework.util.ThymeleafResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
 * Abdulqodir Ganiev 7/2/2022 12:31 AM
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("collections")
public class CollectionController {

    private final CollectionRepository collectionRepository;
    private final CollectionService collectionService;
    private final ItemService itemService;
    private final TopicService topicService;

    @GetMapping
    public String getAllCollections(Model model) {

        model.addAttribute("collections",
                collectionRepository.findAll());
        return "client/collections";
    }

    @GetMapping("{id}")
    public String getSingleCollection(@PathVariable Long id,
                                      Model model) {
        List<ItemView> items = itemService.getItemsOfSingleCollection(id);
        model.addAttribute("collection",
                collectionService.getSingleCollection(id));
        model.addAttribute("items", items);
        return "client/single-collection";
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        List<Topic> topics = topicService.getAllEnabledTopics();
        model.addAttribute("topics", topics);
        model.addAttribute("types", CustomFieldType.values());
        return "client/collection-create";
    }

    @PostMapping("/create")
    public String saveCollection(MultipartFile image,
                                 HttpServletRequest request,
                                 @AuthenticationPrincipal User currentUser,
                                 RedirectAttributes attributes) {
        ThymeleafResponse response = collectionService.saveCollection(image, request, currentUser);
        attributes.addFlashAttribute("response", response);
        return "redirect:/profile";
    }
}
