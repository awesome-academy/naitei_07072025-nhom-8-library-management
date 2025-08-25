package com.group8.library_management.controller.admin;

import com.group8.library_management.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;
    private final MessageSource messageSource;

    public UserController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    private String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }

    @GetMapping
    public String getUsersPage(@RequestParam(required = false) String searchName,
                               @RequestParam(required = false) String sortBy,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               Model model, HttpServletRequest request) {

        var users = userService.getAllUsers(page, size, searchName, sortBy);



        model.addAttribute("title", "Users");
        model.addAttribute("users", users.getContent());
        model.addAttribute("currentPage", users.getNumber());
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("totalElements", users.getTotalElements());
        model.addAttribute("searchName", searchName);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("size", size);
        model.addAttribute("request", request);
        return "users";
    }

    @PutMapping("/{id}/deactivate")
    public String deactivateUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        userService.deactivateUser(id);
        redirectAttributes.addFlashAttribute("successMessage", getMessage("user.deactivate.success"));
        return "redirect:/admin/users";
    }

    @PutMapping("/{id}/reactivate")
    public String reactivateUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        userService.reactivateUser(id);
        redirectAttributes.addFlashAttribute("successMessage", getMessage("user.reactivate.success"));
        return "redirect:/admin/users";
    }
}
