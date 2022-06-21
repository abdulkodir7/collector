package com.itransition.coursework.user;

import com.itransition.coursework.category.Category;
import com.itransition.coursework.category.CategoryService;
import com.itransition.coursework.user.role.RoleRepository;
import com.itransition.coursework.util.ThymeleafResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private final CategoryService categoryService;

    @GetMapping
    public String getAdminPage() {
        return "admin/index";
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

    @GetMapping("categories")
    public String getAllCategories(@RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) int size,
                                   @RequestParam(name = "page", defaultValue = "1") int page,
                                   Model model) {

        Page<Category> categories = categoryService.getAllCategories(size, page);
        model.addAttribute("categories", categories);
        return "admin/categories";
    }

    @PostMapping("/categories/save-category")
    public String saveCategory(@RequestParam(name = "id", required = false) Long id,
                           @RequestParam(name = "name") String name,
                           RedirectAttributes attributes) {
        ThymeleafResponse response = categoryService.saveCategory(id, name);
        attributes.addFlashAttribute("response", response);
        return "redirect:/admin/categories";
    }

    @GetMapping("categories/delete-category/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes attributes) {
        attributes.addFlashAttribute("response", categoryService.deleteCategory(id));
        return "redirect:/admin/categories";
    }

    @GetMapping("categories/disable-category/{id}")
    public String disableCategory(@PathVariable Long id, RedirectAttributes attributes) {
        attributes.addFlashAttribute("response", categoryService.disableCategory(id));
        return "redirect:/admin/categories";
    }

    @GetMapping("categories/enable-category/{id}")
    public String enableCategory(@PathVariable Long id, RedirectAttributes attributes) {
        attributes.addFlashAttribute("response", categoryService.enableCategory(id));
        return "redirect:/admin/categories";
    }
}
