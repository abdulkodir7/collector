package com.itransition.coursework.item;

import com.itransition.coursework.comment.CommentService;
import com.itransition.coursework.custom_field.CustomFieldRepository;
import com.itransition.coursework.custom_field.CustomFieldView;
import com.itransition.coursework.item.projection.SingleItemView;
import com.itransition.coursework.tag.Tag;
import com.itransition.coursework.tag.TagService;
import com.itransition.coursework.user.User;
import com.itransition.coursework.util.ThymeleafResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    private final CommentService commentService;

    @GetMapping
    public String getAllItems(Model model) {
        model.addAttribute("items", itemService.getAllItems());
        return "client/item/items";
    }

    @GetMapping("{id}")
    public String getSingleItems(Model model, @PathVariable Long id) {
        SingleItemView singleItem = itemService.getSingleItem(id);
        model.addAttribute("item", singleItem);
        return "client/item/single-item";
    }

    @GetMapping("/add-item/{collectionId}")
    public String getItemCreatePage(Model model, @PathVariable Long collectionId) {
        List<Tag> tags = tagService.getAllTags();
        List<CustomFieldView> customFields = customFieldRepository.getItemCustomFields(collectionId);
        model.addAttribute("customFields", customFields);
        model.addAttribute("tags", tags);
        model.addAttribute("collectionId", collectionId);
        return "client/item/item-create";
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

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       Model model) {
        SingleItemView item = itemService.getSingleItem(id);
        List<Tag> tags = tagService.getAllTags();
        model.addAttribute("item", item);
        model.addAttribute("tags", tags);
        return "client/item/item-edit";
    }

    @PostMapping("/edit/{id}")
    public String editItem(@PathVariable Long id,
                           MultipartFile file,
                           HttpServletRequest request,
                           RedirectAttributes attributes) {
        ThymeleafResponse response = itemService.editItem(id, request, file);
        attributes.addFlashAttribute("response", response);
        return "redirect:/items/" + id;
    }

    @GetMapping("/delete/{itemId}/{collectionId}")
    public String delete(@PathVariable Long collectionId,
                         @PathVariable Long itemId,
                         RedirectAttributes attributes) {
        ThymeleafResponse response = itemService.deleteItem(itemId);
        attributes.addFlashAttribute("response", response);
        return "redirect:/collections/" + collectionId;
    }

    @GetMapping("/like/{itemId}")
    public String likeItem(@PathVariable Long itemId,
                           @AuthenticationPrincipal User user,
                           RedirectAttributes attributes) {
        ThymeleafResponse response = itemService.likeItem(itemId, user);
        attributes.addFlashAttribute("response", response);
        return "redirect:/items/" + itemId;
    }

    @PostMapping("/comment/{itemId}")
    public String commentItem(@PathVariable Long itemId,
                              @AuthenticationPrincipal User user,
                              @RequestParam String comment,
                              RedirectAttributes attributes) {
        ThymeleafResponse response = commentService.addNewComment(comment, itemId, user);
        attributes.addFlashAttribute("response", response);
        return "redirect:/items/" + itemId;
    }

    @GetMapping("/delete-comment/{commentId}/{itemId}")
    public String deleteComment(@PathVariable Long commentId,
                                @PathVariable Long itemId,
                                RedirectAttributes attributes) {
        ThymeleafResponse response = commentService.deleteComment(commentId);
        attributes.addFlashAttribute("response", response);
        return "redirect:/items/" + itemId;
    }
}
