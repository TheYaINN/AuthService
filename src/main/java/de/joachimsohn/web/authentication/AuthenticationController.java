package de.joachimsohn.web.authentication;

import de.joachimsohn.web.authentication.adapter.UserServiceWebAdapter;
import de.joachimsohn.web.authentication.model.LoginFormDto;
import de.joachimsohn.web.authentication.model.RegisterFormDto;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("auth/")
final class AuthenticationController {

    private final UserServiceWebAdapter webAdapter;

    @PostMapping("login")
    public @NotNull ResponseEntity<Void> login(final @RequestBody @NotNull LoginFormDto formData) {
        return ResponseEntity.noContent()
                .headers(httpHeaders -> httpHeaders.setBearerAuth(webAdapter.login(formData)))
                .build();
    }

    @PostMapping("register")
    public @NotNull ResponseEntity<Void> register(final @RequestBody @NotNull RegisterFormDto formData) {
        return ResponseEntity.noContent()
                .headers(httpHeaders -> httpHeaders.setBearerAuth(webAdapter.register(formData)))
                .build();
    }
}
