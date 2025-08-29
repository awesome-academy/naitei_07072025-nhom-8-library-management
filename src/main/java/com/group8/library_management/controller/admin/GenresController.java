package com.group8.library_management.controller.admin;

import com.group8.library_management.entity.Genre;
import com.group8.library_management.service.GenreService;
import com.group8.library_management.service.impl.GenreServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
@RequestMapping("/admin/genres")
public class GenresController {

    private final GenreService genreService;
    private final MessageSource messageSource;

    public GenresController(GenreServiceImpl genreService, MessageSource messageSource) {
        this.genreService = genreService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public String getGenresPage(Model model,
                                HttpServletRequest request) {
        model.addAttribute("topLevelGenres", genreService.getTopLevelGenres());
        model.addAttribute("allGenres", genreService.getAllGenres());
        model.addAttribute("totalElements", genreService.countGenres());
        model.addAttribute("request", request);
        return "genres";
    }

    @PostMapping("/create")
    public String createGenre(String name,
                              Integer parentId,
                              RedirectAttributes redirectAttributes,
                              Locale locale) {
        genreService.createGenre(name, parentId);
        String successMsg = messageSource.getMessage("toast.genre.success.create", new Object[]{name}, locale);
        redirectAttributes.addFlashAttribute("successMessage", successMsg);
        return "redirect:/admin/genres";
    }

    @PutMapping("/{id}/delete")
    public String deleteGenre(@PathVariable("id") Integer id,
                              RedirectAttributes redirectAttributes,
                              Locale locale) {
        String genreName = genreService.getGenreById(id).map(Genre::getName).orElse("Unknown");
        genreService.deleteGenre(id);
        String successMsg = messageSource.getMessage("toast.genre.success.delete", new Object[]{genreName}, locale);
        redirectAttributes.addFlashAttribute("successMessage", successMsg);
        return "redirect:/admin/genres";
    }

    @PutMapping("/{id}/edit")
    public String editGenre(@PathVariable("id") Integer id,
                            String name,
                            Integer parentId,
                            RedirectAttributes redirectAttributes,
                            Locale locale) {
        if (parentId != null && parentId.equals(id)) {
            String errorMsg = messageSource.getMessage("toast.genre.error.selfParent", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMsg);
            return "redirect:/admin/genres";
        }
        genreService.editGenre(id, name, parentId);
        String successMsg = messageSource.getMessage("toast.genre.success.edit", new Object[]{name}, locale);
        redirectAttributes.addFlashAttribute("successMessage", successMsg);
        return "redirect:/admin/genres";
    }
}
