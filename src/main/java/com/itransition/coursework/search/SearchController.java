package com.itransition.coursework.search;

import com.itransition.coursework.item.Item;
import com.itransition.coursework.user.User;
import com.itransition.coursework.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Abdulqodir Ganiev 7/12/2022 1:07 PM
 */

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;
    private final UserService userService;

    @GetMapping("/admin-search")
    public String searchForAdmin(@RequestParam String search,
                                 @AuthenticationPrincipal User loggedInUser,
                                 Model model) {
        model.addAttribute("currentUser",
                userService.getCurrentUser(loggedInUser));
        List<Item> items = searchService.searchForAdmin(search);
        model.addAttribute("items", items);
        return "admin/search-results";
    }

    @GetMapping("/client-search")
    public String searchForClient(@RequestParam String search,
                                 Model model) {
        List<Item> items = searchService.searchForAdmin(search);
        model.addAttribute("items", items);
        return "client/item/search-results";
    }
}
