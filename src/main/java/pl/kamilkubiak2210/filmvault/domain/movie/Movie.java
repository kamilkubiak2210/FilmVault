package pl.kamilkubiak2210.filmvault.domain.movie;

import lombok.*;
import pl.kamilkubiak2210.filmvault.domain.genre.Genre;
import pl.kamilkubiak2210.filmvault.domain.rating.Rating;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String originalTitle;
    private String shortDescription;
    private String description;
    private String youtubeTrailerId;
    private Integer releaseYear;
    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<Rating> ratings = new HashSet<>();
    private boolean promoted;
    private String poster;
}