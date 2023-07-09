package com.paymybuddy.transferapp.config;

import com.paymybuddy.transferapp.exceptions.NotFoundException;
import com.paymybuddy.transferapp.model.User;
import com.paymybuddy.transferapp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if (user == null) {
            throw new NotFoundException("Invalid credentials");
        }

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole());

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(grantedAuthority);

        return new CustomUserDetail(user.getEmail(), user.getPassword(), roles);
    }
}

