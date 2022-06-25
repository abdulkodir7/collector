package com.itransition.coursework.user;

import com.itransition.coursework.util.ThymeleafResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Abdulqodir Ganiev 6/22/2022 12:06 AM
 */

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class AuthController {

    private final UserService userService;

    @GetMapping
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

    @PostMapping("/register-user")
    public String registerUser(UserRegistrationDto dto,
                               RedirectAttributes attributes) {
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
