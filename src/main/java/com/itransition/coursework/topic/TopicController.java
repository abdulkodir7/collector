package com.itransition.coursework.topic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Abdulqodir Ganiev 7/2/2022 4:32 PM
 */

@Controller
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @GetMapping("/topics")
    public String getAllTopics(Model model) {
        model.addAttribute("topics", topicService.getAllEnabledTopics());
        return "client/topics";
    }
}
