package com.itransition.coursework.user;

import com.itransition.coursework.collection.Collection;
import com.itransition.coursework.collection.CollectionDto;
import com.itransition.coursework.collection.CollectionService;
import com.itransition.coursework.item.ItemService;
import com.itransition.coursework.item.ItemView;
import com.itransition.coursework.topic.Topic;
import com.itransition.coursework.topic.TopicService;
import com.itransition.coursework.user.role.RoleEnum;
import com.itransition.coursework.user.role.RoleRepository;
import com.itransition.coursework.util.ThymeleafResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @GetMapping
    public String getAdminPage(@AuthenticationPrincipal User loggedInUser) {
        if (loggedInUser == null)
            return "admin/login";

        if ((loggedInUser.getRole().getName().equals(RoleEnum.ROLE_SUPER_ADMIN)
                || loggedInUser.getRole().getName().equals(RoleEnum.ROLE_ADMIN))
                && loggedInUser.getIsActive())
            return "admin/index";
        else
            return "admin/login";
    }


    @GetMapping("/users")
    public String getUsersTable(@RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) int size,
                                @RequestParam(name = "page", defaultValue = "1") int page,
                                Model model) {
        Page<User> users = userService.getAllUsers(size, page);
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

    @GetMapping("topics")
    public String getAllTopics(@RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) int size,
                               @RequestParam(name = "page", defaultValue = "1") int page,
                               Model model) {

        Page<Topic> topics = topicService.getAllTopics(size, page);
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

    @GetMapping("collections")
    public String getCollections(Model model) {
        model.addAttribute("collectionDto", new CollectionDto());
        model.addAttribute("topics",
                topicService.getAllEnabledTopics());
        model.addAttribute("collections",
                collectionService.getAllCollectionsForAdmin());
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

    @GetMapping("collections/get-collection-items/{id}")
    private String getItemsOfSingleCollection(@PathVariable Long id,
                                              RedirectAttributes attributes,
                                              Model model) {
        List<ItemView> items = itemService.getItemsOfSingleCollection(id);
        Collection collection = collectionService.getSingleCollection(id);
        System.out.println(items.get(0).getTag());
        model.addAttribute("collection", collection);
        model.addAttribute("items", items);
        attributes.addFlashAttribute("response", COLLECTION_NOT_FOUND);
        return collection != null ? "admin/single-collection-items" : "redirect:/admin/collections";
    }


}
