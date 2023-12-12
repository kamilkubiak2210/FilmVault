package pl.kamilkubiak2210.filmvault.domain.movie.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)

public class MovieDto {
    private Long id;
    private String title;
    private String originalTitle;
    private String shortDescription;
    private String description;
    private String youtubeTrailerId;
    private Integer releaseYear;
    private String genre;
    private boolean promoted;
    private String poster;
    private double avgRating;
    private int ratingCount;
}