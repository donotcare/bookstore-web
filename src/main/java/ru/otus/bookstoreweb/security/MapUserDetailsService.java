package ru.otus.bookstoreweb.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;

public class MapUserDetailsService implements UserDetailsService {
    private final Map<String, UserDetails> userDetailsByUserName;

    public MapUserDetailsService(Map<String, UserDetails> userDetailsByUserName) {
        this.userDetailsByUserName = userDetailsByUserName;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserDetails userDetails = userDetailsByUserName.get(userName);
        if(userDetails == null) {
            throw new UsernameNotFoundException("Username " + userName + " was not found");
        }
        return userDetails;
    }
}
