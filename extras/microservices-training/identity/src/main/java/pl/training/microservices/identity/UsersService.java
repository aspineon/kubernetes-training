package pl.training.microservices.identity;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {

    private static final String DEFAULT_ROLE = "ROLE_USER";

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public User addUser(User user) {
        encodePassword(user);
        user.setRole(DEFAULT_ROLE);
        user.setEnabled(true);
        return usersRepository.saveAndFlush(user);
    }

    private void encodePassword(User user) {
        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.getByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
