package pl.kamilkubiak2210.filmvault.domain.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)

public class UserCredentialsDto {
    private final String email;
    private final String password;
    private final Set<String> roles;
}