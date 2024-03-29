package pl.kamilkubiak2210.filmvault.web.admin;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.kamilkubiak2210.filmvault.domain.genre.GenreService;
import pl.kamilkubiak2210.filmvault.domain.genre.dto.GenreDto;
import pl.kamilkubiak2210.filmvault.domain.movie.Movie;
import pl.kamilkubiak2210.filmvault.domain.movie.MovieService;
import pl.kamilkubiak2210.filmvault.domain.movie.dto.MovieDto;
import pl.kamilkubiak2210.filmvault.domain.movie.dto.MovieSaveDto;

import java.util.List;

@Controller
public class MovieManagementController {
    private final MovieService movieService;
    private final GenreService genreService;

    public MovieManagementController(MovieService movieService, GenreService genreService) {
        this.movieService = movieService;
        this.genreService = genreService;
    }

    @GetMapping("/admin/dodaj-film")
    public String addMovieForm(Model model) {
        List<GenreDto> allGenres = genreService.findAllGenres();
        model.addAttribute("genres", allGenres);
        model.addAttribute("movie", new MovieSaveDto());
        return "admin/movie-form";
    }

    @PostMapping("/admin/dodaj-film")
    public String addMovie(MovieSaveDto movie, RedirectAttributes redirectAttributes) {
        movieService.addMovie(movie, new Movie());
        redirectAttributes.addFlashAttribute(
                AdminController.NOTIFICATION_ATTRIBUTE,
                "Film %s został zapisany".formatted(movie.getTitle()));
        return "redirect:/admin";
    }

    @GetMapping("/admin/edytuj-film/{id}")
    public String editFilm(@PathVariable Long id, Model model) {
        MovieDto movieById = movieService.findMovieById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<GenreDto> allGenres = genreService.findAllGenres();
        model.addAttribute("genres", allGenres);
        model.addAttribute("movie", movieById);
        model.addAttribute("movieId", movieById.getId());
        return "admin/movie-edit-form";
    }

    @PostMapping("/admin/edytuj-film/")
    public String editMovie(MovieSaveDto movie, Long movieId, RedirectAttributes redirectAttributes) {
        movieService.editMovie(movie, movieId);
        redirectAttributes.addFlashAttribute(
                AdminController.NOTIFICATION_ATTRIBUTE, "Film został zedytowany");
        return "redirect:/admin";
    }

    @PostMapping("/admin/edytuj-film/usun")
    public String deleteMovie(Long movieId, RedirectAttributes redirectAttributes) {
        movieService.deleteMovie(movieId);
        redirectAttributes.addFlashAttribute(
                AdminController.NOTIFICATION_ATTRIBUTE,
                "Film  został usunięty");
        return "redirect:/admin";
    }
}