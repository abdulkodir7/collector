package com.itransition.coursework.user;

import com.itransition.coursework.collection.CollectionService;
import com.itransition.coursework.collection.projection.TopCollectionView;
import com.itransition.coursework.item.ItemService;
import com.itransition.coursework.tag.TagService;
import com.itransition.coursework.topic.TopicService;
import com.itransition.coursework.util.ThymeleafResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Abdulqodir Ganiev 6/22/2022 12:06 AM
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class AuthController {

    private final UserService userService;
    private final CollectionService collectionService;
    private final ItemService itemService;
    private final TagService tagService;

    @GetMapping
    public String getHomePage(Model model) {
        List<TopCollectionView> top5 = collectionService.getTop5BiggestCollections();
        model.addAttribute("items", itemService.getLatestItems());
        model.addAttribute("tags", tagService.getAllTags());
        model.addAttribute("top5", top5);
        return "client/index";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "client/login";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("dto", new UserRegistrationDto());
        return "client/register";
    }

    @PostMapping("/register-user")
    public String registerUser(@Valid UserRegistrationDto dto,
                               RedirectAttributes attributes,
                               BindingResult result) {
        if (result.hasErrors())
            return "redirect:/register";
        ThymeleafResponse response = userService.registerUser(dto);
        attributes.addFlashAttribute("response", response);
        return response.getStatus() ? "redirect:/login" : "redirect:/register";

    }

    @PostMapping("/admin-login")
    public String adminLogin(@RequestParam(name = "email") String email,
                             @RequestParam(name = "password") String password,
                             HttpServletRequest request,
                             RedirectAttributes attributes) {
        ThymeleafResponse response = userService.adminLogin(email, password, request);
        attributes.addFlashAttribute("response", response);
        if (!response.getStatus())
            return "redirect:/admin";
        return "admin/index";
    }
}
