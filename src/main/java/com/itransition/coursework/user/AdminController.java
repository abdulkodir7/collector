package com.itransition.coursework.user;

import com.itransition.coursework.collection.CollectionService;
import com.itransition.coursework.collection.dto.EditCollectionDto;
import com.itransition.coursework.collection.projection.SingleCollectionView;
import com.itransition.coursework.comment.CommentService;
import com.itransition.coursework.custom_field.CustomFieldType;
import com.itransition.coursework.item.ItemService;
import com.itransition.coursework.item.projection.ItemView;
import com.itransition.coursework.item.SingleItemView;
import com.itransition.coursework.tag.Tag;
import com.itransition.coursework.tag.TagService;
import com.itransition.coursework.topic.Topic;
import com.itransition.coursework.topic.TopicService;
import com.itransition.coursework.user.role.RoleEnum;
import com.itransition.coursework.user.role.RoleRepository;
import com.itransition.coursework.util.ThymeleafResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.itransition.coursework.util.Constants.COLLECTION_NOT_FOUND;
import static com.itransition.coursework.util.Constants.DEFAULT_PAGE_SIZE;

/**
 * Abdulqodir Ganiev 6/17/2022 4:34 PM
 */

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final TopicService topicService;
    private final CollectionService collectionService;
    private final ItemService itemService;
    private final TagService tagService;
    private final CommentService commentService;

    @GetMapping
    public String getAdminPage(@AuthenticationPrincipal User loggedInUser,
                               Model model) {
        if (loggedInUser == null)
            return "admin/login";

        if ((loggedInUser.getRole().getName().equals(RoleEnum.ROLE_SUPER_ADMIN)
                || loggedInUser.getRole().getName().equals(RoleEnum.ROLE_ADMIN))
                && loggedInUser.getIsActive()) {
            User user = userService.getCurrentUser(loggedInUser);
            model.addAttribute("currentUser", user);
            return "admin/index";
        } else
            return "admin/login";
    }


    /**
     * USER
     */
    @GetMapping("profile")
    public String getProfilePage(@AuthenticationPrincipal User currentUser,
                                 Model model) {
        User user = userService.getCurrentUser(currentUser);
        model.addAttribute("currentUser", user);
        return "admin/profile";
    }

    @PostMapping("edit-profile")
    public String editProfile(MultipartFile profileImage,
                              @RequestParam Long id,
                              @RequestParam String name,
                              @RequestParam String email,
                              RedirectAttributes attributes) {
        ThymeleafResponse response = userService.editAdminProfile(profileImage, id, name, email);
        attributes.addFlashAttribute("response", response);
        return "redirect:/admin/profile";
    }

    @GetMapping("/users")
    public String getUsersTable(@RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) int size,
                                @RequestParam(name = "page", defaultValue = "1") int page,
                                Model model, @AuthenticationPrincipal User loggedInUser) {
        User user = userService.getCurrentUser(loggedInUser);
        Page<User> users = userService.getAllUsers(size, page);
        model.addAttribute("currentUser", user);
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("users", users);
        return "admin/users";
    }

    @PostMapping("/users/save-user")
    public String saveUser(@RequestParam(name = "id", required = false) Long id,
                           @RequestParam(name = "name") String name,
                           @RequestParam(name = "email") String email,
                           @RequestParam(name = "password") String password,
                           @RequestParam(name = "confirmPassword") String confirmPassword,
                           @RequestParam(name = "role") Long role,
                           RedirectAttributes attributes) {
        ThymeleafResponse response = userService.saveUser(id, name, email, password, confirmPassword, role);
        attributes.addFlashAttribute("response", response);
        return "redirect:/admin/users";
    }

    @GetMapping("users/delete-user/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes attributes) {
        attributes.addFlashAttribute("response", userService.deleteUser(id));
        return "redirect:/admin/users";
    }

    @GetMapping("users/block-user/{id}")
    public String blockUser(@PathVariable Long id, RedirectAttributes attributes) {
        attributes.addFlashAttribute("response", userService.blockUser(id));
        return "redirect:/admin/users";
    }

    @GetMapping("users/unblock-user/{id}")
    public String unblockUser(@PathVariable Long id, RedirectAttributes attributes) {
        attributes.addFlashAttribute("response", userService.unblockUser(id));
        return "redirect:/admin/users";
    }

    /**
     * TOPIC
     */
    @GetMapping("topics")
    public String getAllTopics(@RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) int size,
                               @RequestParam(name = "page", defaultValue = "1") int page,
                               Model model, @AuthenticationPrincipal User loggedInUser) {

        Page<Topic> topics = topicService.getAllTopics(size, page);
        User user = userService.getCurrentUser(loggedInUser);
        model.addAttribute("currentUser", user);
        model.addAttribute("topics", topics);
        return "admin/topics";
    }

    @PostMapping("/topics/save-topic")
    public String saveTopic(@RequestParam(name = "id", required = false) Long id,
                            @RequestParam(name = "name") String name,
                            RedirectAttributes attributes) {
        ThymeleafResponse response = topicService.saveTopic(id, name);
        attributes.addFlashAttribute("response", response);
        return "redirect:/admin/topics";
    }

    @GetMapping("topics/delete-topic/{id}")
    public String deleteTopic(@PathVariable Long id, RedirectAttributes attributes) {
        attributes.addFlashAttribute("response", topicService.deleteTopic(id));
        return "redirect:/admin/topics";
    }

    @GetMapping("topics/disable-topic/{id}")
    public String disableTopic(@PathVariable Long id, RedirectAttributes attributes) {
        attributes.addFlashAttribute("response", topicService.disableTopic(id));
        return "redirect:/admin/topics";
    }

    @GetMapping("topics/enable-topic/{id}")
    public String enableTopic(@PathVariable Long id, RedirectAttributes attributes) {
        attributes.addFlashAttribute("response", topicService.enableTopic(id));
        return "redirect:/admin/topics";
    }

    /**
     * COLLECTIONS
     */
    @GetMapping("collections")
    public String getCollections(Model model, @AuthenticationPrincipal User loggedInUser) {
        model.addAttribute("currentUser",
                userService.getCurrentUser(loggedInUser));
        model.addAttribute("topics",
                topicService.getAllEnabledTopics());
        model.addAttribute("collections",
                collectionService.getAllCollectionsForAdmin());
        model.addAttribute("customFieldTypes", CustomFieldType.values());
        return "admin/collections";
    }

    @PostMapping("collections/save-collection")
    public String saveCollection(MultipartFile image,
                                 HttpServletRequest request,
                                 @AuthenticationPrincipal User currentUser,
                                 RedirectAttributes attributes) {
        ThymeleafResponse response = collectionService.saveCollection(image, request, currentUser);
        attributes.addFlashAttribute("response", response);
        return "redirect:/admin/collections";
    }

    @GetMapping("collections/delete-collection/{id}")
    public String deleteCollection(@PathVariable Long id,
                                   RedirectAttributes attributes) {
        ThymeleafResponse response = collectionService.deleteCollection(id);
        attributes.addFlashAttribute("response", response);
        return "redirect:/admin/collections";
    }

    @PostMapping("collections/edit/{id}")
    public String editCollection(@PathVariable Long id,
                                 EditCollectionDto dto,
                                 RedirectAttributes attributes) {
        ThymeleafResponse response = collectionService.editCollection(id, dto);
        attributes.addFlashAttribute("attributes", response);
        return "redirect:/admin/collections/get-collection-items/" + id;
    }

    /**
     * ITEMS
     */

    @GetMapping("items")
    public String getItems(Model model, @AuthenticationPrincipal User loggedInUser) {
        List<ItemView> items = itemService.getAllItems();
        User user = userService.getCurrentUser(loggedInUser);
        model.addAttribute("currentUser", user);
        model.addAttribute("items", items);
        return "admin/items";
    }

    @GetMapping("collections/get-collection-items/{id}")
    public String getItemsOfSingleCollection(@PathVariable Long id,
                                             RedirectAttributes attributes,
                                             Model model, @AuthenticationPrincipal User loggedInUser) {
        List<ItemView> items = itemService.getItemsOfSingleCollection(id);
        SingleCollectionView collection = collectionService.getSingleCollection(id);
        List<Tag> tags = tagService.getAllTags();
        List<Topic> topics = topicService.getAllEnabledTopics();
        User user = userService.getCurrentUser(loggedInUser);
        model.addAttribute("currentUser", user);
        model.addAttribute("collection", collection);
        model.addAttribute("items", items);
        model.addAttribute("tags", tags);
        model.addAttribute("topics", topics);
        model.addAttribute("editCollectionDto", new EditCollectionDto());
        attributes.addFlashAttribute("response", COLLECTION_NOT_FOUND);
        return collection != null ? "admin/single-collection-items" : "redirect:/admin/collections";
    }

    @PostMapping("items/save-item")
    public String saveItem(MultipartFile file,
                           HttpServletRequest request,
                           RedirectAttributes attributes) {
        String collectionId = request.getParameter("collectionId");
        ThymeleafResponse response = itemService.saveItem(file, request);
        attributes.addFlashAttribute("response", response);
        return "redirect:/admin/collections/get-collection-items/" + collectionId;
    }

    @PostMapping("items/edit/{id}")
    public String editItem(@PathVariable Long id,
                           MultipartFile file,
                           HttpServletRequest request,
                           RedirectAttributes attributes) {
        ThymeleafResponse response = itemService.editItem(id, request, file);
        attributes.addFlashAttribute("response", response);
        return "redirect:/admin/items/get-single-item/" + id;
    }

    @GetMapping("items/get-single-item/{id}")
    public String getSingleItem(@PathVariable Long id, Model model,
                                @AuthenticationPrincipal User loggedInUser) {
        SingleItemView singleItem = itemService.getSingleItem(id);
        User user = userService.getCurrentUser(loggedInUser);
        model.addAttribute("currentUser", user);
        model.addAttribute("item", singleItem);
        model.addAttribute("tags", tagService.getAllTags());
        return "admin/single-item";
    }

    @GetMapping("items/delete/{id}/{collectionId}")
    public String deleteItem(@PathVariable Long id,
                             @PathVariable String collectionId,
                             RedirectAttributes attributes) {
        ThymeleafResponse response = itemService.deleteItem(id);
        attributes.addFlashAttribute("response", response);
        return "redirect:/admin/collections/get-collection-items/" + collectionId;
    }

    @GetMapping("items/delete-item/{id}")
    public String deleteItem(@PathVariable Long id,
                             RedirectAttributes attributes) {
        ThymeleafResponse response = itemService.deleteItem(id);
        attributes.addFlashAttribute("response", response);
        return "redirect:/admin/items";
    }

    /**
     * COMMENT
     */
    @PostMapping("comment/add-comment")
    public String addComment(@RequestParam(name = "comment") String comment,
                             @RequestParam(name = "itemId") Long id,
                             @AuthenticationPrincipal User currentUser,
                             RedirectAttributes attributes) {
        ThymeleafResponse response = commentService.addNewComment(comment, id, currentUser);
        attributes.addFlashAttribute("response", response);
        return "redirect:/admin/items/get-single-item/" + id;
    }

    @PostMapping("comment/edit/{id}")
    public String editComment(@PathVariable Long id,
                              @RequestParam(name = "comment") String comment,
                              @RequestParam(name = "itemId") Long itemId,
                              RedirectAttributes attributes) {
        ThymeleafResponse response = commentService.editComment(id, comment);
        attributes.addFlashAttribute("response", response);
        return "redirect:/admin/items/get-single-item/" + itemId;
    }

    @GetMapping("comment/delete/{commentId}/{itemId}")
    public String deleteComment(RedirectAttributes attributes,
                                @PathVariable Long commentId,
                                @PathVariable Long itemId) {
        ThymeleafResponse response = commentService.deleteComment(commentId);
        attributes.addFlashAttribute("response", response);
        return "redirect:/admin/items/get-single-item/" + itemId;
    }

    /**
     * TAG
     */
    @GetMapping("tags")
    public String getAllTags(Model model, @AuthenticationPrincipal User loggedInUser) {
        List<Tag> tags = tagService.getAllTags();
        User user = userService.getCurrentUser(loggedInUser);
        model.addAttribute("currentUser", user);
        model.addAttribute("tags", tags);
        return "admin/tags";
    }

    @PostMapping("tags/edit")
    public String edit(@RequestParam Long id,
                       @RequestParam String name,
                       RedirectAttributes attributes) {
        ThymeleafResponse response = tagService.edit(id, name);
        attributes.addFlashAttribute("response", response);
        return "redirect:/admin/tags";
    }

    @PostMapping("tags/create")
    public String create(@RequestParam String name,
                         RedirectAttributes attributes) {
        ThymeleafResponse response = tagService.create(name);
        attributes.addFlashAttribute("response", response);
        return "redirect:/admin/tags";
    }

    @GetMapping("tags/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes attributes) {
        ThymeleafResponse response = tagService.delete(id);
        attributes.addFlashAttribute("response", response);
        return "redirect:/admin/tags";
    }

}
