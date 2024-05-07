package de.joachimsohn.web.authentication.mapper;

import de.joachimsohn.domain.user.model.User;
import de.joachimsohn.web.authentication.model.LoginFormDto;
import de.joachimsohn.web.authentication.model.RegisterFormDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public final class LoginMapper {

    public @NotNull User toDomain(final @NotNull LoginFormDto source) {
        return User.builder()
                .username(source.getUsername())
                .password(source.getPassword())
                .build();
    }

    public @NotNull User toDomain(final @NotNull RegisterFormDto source) {
        return User.builder()
                .username(source.getUsername())
                .password(source.getPassword())
                .email(source.getEmail())
                .firstname(source.getFirstname())
                .lastname(source.getLastname())
                .build();
    }
}
