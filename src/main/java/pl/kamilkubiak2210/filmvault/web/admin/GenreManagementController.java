package pl.kamilkubiak2210.filmvault.web.admin;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.kamilkubiak2210.filmvault.domain.genre.Genre;
import pl.kamilkubiak2210.filmvault.domain.genre.GenreService;
import pl.kamilkubiak2210.filmvault.domain.genre.dto.GenreDto;

@Controller
public class GenreManagementController {
    private final GenreService genreService;

    public GenreManagementController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/admin/dodaj-gatunek")
    public String addGenreForm(Model model) {
        model.addAttribute("genre", new GenreDto());
        return "admin/genre-form";
    }

    @PostMapping("/admin/dodaj-gatunek")
    public String addGenre(GenreDto genre, RedirectAttributes redirectAttributes) {
        genreService.addGenre(genre, new Genre());
        alert(redirectAttributes, "Gatunek %s został zapisany".formatted(genre.getName()));
        return "redirect:/admin";
    }

    @GetMapping("/admin/gatunki-filmowe/edytuj/{id}")
    public String getGenreList(@PathVariable Long id, Model model) {
        GenreDto genreDto = genreService.findGenreById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("genre", genreDto);
        return "admin/genre-edit-form";
    }

    @PostMapping("/admin/edytuj-gatunek")
    public String editGenre(GenreDto genre, RedirectAttributes redirectAttributes) {
        genreService.editGenre(genre);
        alert(redirectAttributes, "Gatunek został zedytowany");
        return "redirect:/admin";
    }

    @PostMapping("/admin/edytuj-gatunek/usun")
    public String deleteGenre(GenreDto genre, RedirectAttributes redirectAttributes) {
        boolean genreDeleted = genreService.deleteGenre(genre);
        if (genreDeleted) {
            alert(redirectAttributes, "Gatunek został usunięty");
            return "redirect:/admin";
        } else {
            alert(redirectAttributes, "Aby usunąć ten gatunek żaden z filmów nie może go posiadać");
            return "redirect:/admin/gatunki-filmowe/edytuj/" + genre.getId();
        }
    }

    private static void alert(RedirectAttributes redirectAttributes, String alertDescription) {
        redirectAttributes.addFlashAttribute(AdminController.NOTIFICATION_ATTRIBUTE, alertDescription);
    }
}