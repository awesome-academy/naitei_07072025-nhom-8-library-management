package com.group8.library_management.controller.admin;

import com.group8.library_management.entity.Author;
import com.group8.library_management.service.impl.AuthorServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/authors")
public class AuthorController {

    private final AuthorServiceImpl authorService;

    public AuthorController(AuthorServiceImpl authorService) {
        this.authorService = authorService;
    }


    @PostMapping
    public String createAuthor( @ModelAttribute("author") Author author, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("title", "Add New Author");
            model.addAttribute("contentFragment", "fragments/author-form :: content");
            return "authors";
        }
        try {
            authorService.createAuthor(author);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("title", "Add New Author");
            model.addAttribute("contentFragment", "fragments/author-form :: content");
            return "authors";
        }
        return "redirect:/admin/authors";
    }
}
