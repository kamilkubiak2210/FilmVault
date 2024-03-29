package pl.kamilkubiak2210.filmvault.domain.movie;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kamilkubiak2210.filmvault.domain.genre.Genre;
import pl.kamilkubiak2210.filmvault.domain.genre.GenreRepository;
import pl.kamilkubiak2210.filmvault.domain.movie.dto.MovieDto;
import pl.kamilkubiak2210.filmvault.domain.movie.dto.MovieSaveDto;
import pl.kamilkubiak2210.filmvault.storage.FileStorageService;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final FileStorageService fileStorageService;

    public MovieService(MovieRepository movieRepository,
                        GenreRepository genreRepository,
                        FileStorageService fileStorageService) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.fileStorageService = fileStorageService;
    }

    public List<MovieDto> findAllPromotedMovies() {
        return movieRepository.findAllByPromotedIsTrue().stream()
                .map(MovieDtoMapper::map)
                .toList();
    }

    public Optional<MovieDto> findMovieById(long id) {
        return movieRepository.findById(id).map(MovieDtoMapper::map);
    }

    public List<MovieDto> findMoviesByGenreName(String genre) {
        return movieRepository.findAllByGenre_NameIgnoreCase(genre).stream()
                .map(MovieDtoMapper::map)
                .toList();
    }

    @Transactional
    public void addMovie(MovieSaveDto movieToSave, Movie movie) {
        movie.setTitle(movieToSave.getTitle());
        movie.setOriginalTitle(movieToSave.getOriginalTitle());
        movie.setPromoted(movieToSave.isPromoted());
        movie.setReleaseYear(movieToSave.getReleaseYear());
        movie.setShortDescription(movieToSave.getShortDescription());
        movie.setDescription(movieToSave.getDescription());
        movie.setYoutubeTrailerId(movieToSave.getYoutubeTrailerId());
        Genre genre = genreRepository.findByNameIgnoreCase(movieToSave.getGenre()).orElseThrow();
        movie.setGenre(genre);
        if (movieToSave.getPoster() != null && !movieToSave.getPoster().isEmpty()) {
            String savedFileName = fileStorageService.saveImage(movieToSave.getPoster());
            movie.setPoster(savedFileName);
        }
        movieRepository.save(movie);
    }

    @Transactional
    public void editMovie(MovieSaveDto movieDto, Long movieId) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        movieOptional.ifPresent(movie -> addMovie(movieDto, movie));
    }

    public List<MovieDto> findTopMovies(int size) {
        Pageable page = Pageable.ofSize(size);
        return movieRepository.findTopByRating(page).stream()
                .map(MovieDtoMapper::map)
                .toList();
    }

    @Transactional
    public void deleteMovie(Long movieId) {
        Optional<Movie> movieOptional = movieRepository.findById(movieId);
        movieOptional.ifPresent(movieRepository::delete);
    }
}