package com.itransition.coursework.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Abdulqodir Ganiev 6/13/2022 6:05 PM
 */

@Controller
public class PageController {

    @GetMapping("/")
    public String getHomePage() {
        return "client/index";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "client/login";
    }

    @GetMapping("/sign-up")
    public String getSignUpPage() {
        return "client/sign-up";
    }

    @GetMapping("/admin")
    public String getAdminPage() {
        return "admin/index";
    }

    @GetMapping("/admin/users")
    public String getUsersTable() {
        return "admin/users";
    }
}
