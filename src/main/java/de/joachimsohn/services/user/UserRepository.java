package de.joachimsohn.services.user;

import de.joachimsohn.domain.user.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

    @NotNull Optional<User> findByUsername(String username);

}
