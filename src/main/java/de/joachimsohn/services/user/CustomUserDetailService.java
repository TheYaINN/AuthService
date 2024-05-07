package de.joachimsohn.services.user;

import de.joachimsohn.security.model.AuthorisedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public final class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final var user = userRepository.findByUsername(username);
        return user.map(AuthorisedUser::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
