package pl.kamilkubiak2210.filmvault.domain.genre;

import pl.kamilkubiak2210.filmvault.domain.genre.dto.GenreDto;

class GenreDtoMapper {
    static GenreDto map(Genre genre) {
        return new GenreDto(
                genre.getId(),
                genre.getName(),
                genre.getDescription()
        );
    }
}