package pl.kamilkubiak2210.filmvault.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserRegistrationDto {
    private String email;
    private String password;
}