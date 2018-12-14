package com.derevets.artem.service;


import com.derevets.artem.model.User;
import com.derevets.artem.model.UserDetailsEntity;
import com.derevets.artem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * This class loads the requested user by using a Spring Data JPA repository.
 *
 * @author Artem Derevets
 */
@Slf4j
@Service
public class RepositoryUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository repository;

    /**
     * Loads the user information.
     *
     * @param username The username of the requested user.
     * @return The information of the user.
     * @throws UsernameNotFoundException Thrown if no user is found with the given username.
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Loading user by username: {}", username);
        User user = repository.findByEmail(username);
        log.debug("Found user: {}", user);

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }


        UserDetailsEntity userDetailsEntity = UserDetailsEntity.builder()
                .email(user.getEmail())
                .id(user.getId())
                .password(user.getPassword())
                .role(user.getRole())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .authorities(authorities)
                .isAccountNonExpired(user.getIsAccountNonExpired())
                .isAccountNonLocked(user.getIsAccountNonLocked())
                .isCredentialsNonLocked(user.getIsCredentialsNonLocked())
                .isEnabled(user.getIsEnabled())
                .build();

        log.debug("Returning user details: {}", userDetailsEntity);

        return userDetailsEntity;
    }
}
