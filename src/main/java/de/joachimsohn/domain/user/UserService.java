package de.joachimsohn.domain.user;

import de.joachimsohn.domain.user.model.User;
import de.joachimsohn.services.AuthenticationManager;
import de.joachimsohn.services.JWTService;
import de.joachimsohn.services.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
@RequiredArgsConstructor
public final class UserService implements org.springframework.security.authentication.AuthenticationManager {

    private final UserRepository users;
    private final JWTService jwtService;

    public @NotNull String login(final @NotNull User user) {
        final var dbUser = users.findByUsername(user.getUsername());
        if (dbUser.isPresent()) {
            final var u = dbUser.get();
            try {
                if (AuthenticationManager.authenticate(user.getPassword(), u.getPassword(), u.getSalt(), u.getIterations())) {
                    return jwtService.GenerateToken(u.getUsername());
                }
            } catch (Exception e) {
                throw new RuntimeException("Could not login");
            }
        }
        throw new RuntimeException("Could not login");
    }

    public @NotNull String register(final @NotNull User user) {
        final var dbUser = users.findByUsername(user.getUsername());
        if (dbUser.isEmpty()) {
            try {
                users.save(AuthenticationManager.hashPassword(user));
                return jwtService.GenerateToken(user.getUsername());
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("User already exists");
        }
    }

    @Override public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        return null;
    }
}
