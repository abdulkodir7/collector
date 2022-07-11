package com.itransition.coursework.user;

import com.itransition.coursework.collection.CollectionRepository;
import com.itransition.coursework.collection.CollectionService;
import com.itransition.coursework.collection.projection.CollectionView;
import com.itransition.coursework.topic.Topic;
import com.itransition.coursework.topic.TopicService;
import com.itransition.coursework.user.User;
import com.itransition.coursework.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Abdulqodir Ganiev 7/4/2022 1:25 AM
 */

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CollectionService collectionService;
    private final TopicService topicService;

    @GetMapping("/profile")
    public String getProfilePage(@AuthenticationPrincipal User user,
                                 Model model) {
        user = userService.getCurrentUser(user);
        List<Topic> topics = topicService.getAllEnabledTopics();
        List<CollectionView> collections = collectionService.getAuthorCollections(user.getId());
        model.addAttribute("topics", topics);
        model.addAttribute("collections", collections);
        model.addAttribute("currentUser", user);
        return "client/profile";
    }

    @GetMapping({
            "/nullvendor/css/rtl/theme-default.css",
            "/nullvendor/css/rtl/core.css"
    })
    public String getIndexPage() {
        return "redirect:/";
    }
}
