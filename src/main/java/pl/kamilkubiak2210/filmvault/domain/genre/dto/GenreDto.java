package pl.kamilkubiak2210.filmvault.domain.genre.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor()
public class GenreDto {
    private Long id;
    private String name;
    private String description;
}