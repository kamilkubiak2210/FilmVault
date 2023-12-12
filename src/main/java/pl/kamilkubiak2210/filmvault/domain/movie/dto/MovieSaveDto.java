package pl.kamilkubiak2210.filmvault.domain.movie.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter

public class MovieSaveDto {
    private String title;
    private String originalTitle;
    private String shortDescription;
    private String description;
    private String youtubeTrailerId;
    private Integer releaseYear;
    private String genre;
    private boolean promoted;
    private MultipartFile poster;
}