package pl.kamilkubiak2210.filmvault.domain.genre;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import pl.kamilkubiak2210.filmvault.domain.genre.dto.GenreDto;
import pl.kamilkubiak2210.filmvault.domain.movie.Movie;
import pl.kamilkubiak2210.filmvault.domain.movie.MovieRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;

    public GenreService(GenreRepository genreRepository, MovieRepository movieRepository) {
        this.genreRepository = genreRepository;
        this.movieRepository = movieRepository;
    }

    public Optional<GenreDto> findGenreByName(String name) {
        return genreRepository.findByNameIgnoreCase(name)
                .map(GenreDtoMapper::map);
    }

    public List<GenreDto> findAllGenres() {
        return StreamSupport.stream(genreRepository.findAll().spliterator(), false)
                .map(GenreDtoMapper::map)
                .toList();
    }

    @Transactional
    public void addGenre(GenreDto genreDto, Genre genre) {
        genre.setName(genreDto.getName());
        genre.setDescription(genreDto.getDescription());
        genreRepository.save(genre);
    }

    public Optional<GenreDto> findGenreById(Long id) {
        return genreRepository.findById(id).map(GenreDtoMapper::map);
    }

    @Transactional
    public void editGenre(GenreDto genreDto) {
        Genre foundGenre = genreRepository.findById(genreDto.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        addGenre(genreDto, foundGenre);
    }

    @Transactional
    public boolean deleteGenre(GenreDto genre) {
        Optional<Genre> optionalGenre = genreRepository.findById(genre.getId());
        if (optionalGenre.isPresent()) {
            Genre foundGenre = optionalGenre.get();
            List<Movie> allByGenreNameIgnoreCase = movieRepository.findAllByGenre_NameIgnoreCase(foundGenre.getName());
            if (allByGenreNameIgnoreCase.isEmpty()) {
                genreRepository.delete(foundGenre);
                return true;
            }
        }
        return false;
    }
}