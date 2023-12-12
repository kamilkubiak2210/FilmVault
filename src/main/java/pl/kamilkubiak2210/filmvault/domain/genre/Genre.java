package pl.kamilkubiak2210.filmvault.domain.genre;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter

@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
}