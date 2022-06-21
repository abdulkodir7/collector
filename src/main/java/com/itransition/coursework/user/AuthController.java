package com.itransition.coursework.user;

import com.itransition.coursework.util.ThymeleafResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Abdulqodir Ganiev 6/22/2022 12:06 AM
 */

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/")
    public String getHomePage() {
        return "client/index";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "client/login";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "client/register";
    }

    @PostMapping("/register")
    public String registerUser(UserRegistrationDto dto,
                               RedirectAttributes attributes) {
        ThymeleafResponse response = userService.registerUser(dto);
        attributes.addFlashAttribute("response", response);
        return response.getStatus() ? "redirect:/login" : "client/register";
    }
}
