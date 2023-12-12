package pl.kamilkubiak2210.filmvault.domain.rating;

import jakarta.persistence.*;
import lombok.*;
import pl.kamilkubiak2210.filmvault.domain.movie.Movie;
import pl.kamilkubiak2210.filmvault.domain.user.User;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor

@Entity
@Table(name = "movie_rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
    private Integer rating;
}