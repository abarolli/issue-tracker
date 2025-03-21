package io.onicodes.issue_tracker.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.onicodes.issue_tracker.models.appUser.AppUser;
import io.onicodes.issue_tracker.repositories.AppUserRepository;
import io.onicodes.issue_tracker.security.AppUserDetails;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AppUserDetailsService implements UserDetailsService {
    private final AppUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User not found");

        return new AppUserDetails(user);
    }
}
