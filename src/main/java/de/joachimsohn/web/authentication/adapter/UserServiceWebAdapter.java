package de.joachimsohn.web.authentication.adapter;

import de.joachimsohn.domain.user.UserService;
import de.joachimsohn.web.authentication.mapper.LoginMapper;
import de.joachimsohn.web.authentication.model.LoginFormDto;
import de.joachimsohn.web.authentication.model.RegisterFormDto;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public final class UserServiceWebAdapter {

    private final UserService service;
    private final LoginMapper mapper;

    public String login(final @NotNull LoginFormDto formData) {
        return service.login(mapper.toDomain(formData));
    }

    public String register(final RegisterFormDto formData) {
        return service.register(mapper.toDomain(formData));
    }
}
