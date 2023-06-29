package com.paymybuddy.transferapp.config;

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
        /*GrantedAuthority grantedAuthority = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        };*/

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole());

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(grantedAuthority);

        // FIXME
        // If username = test | Test -> invalid authentication
        // If username = toto -> exception user is null
        System.out.println(roles.get(0).getAuthority());
        return new CustomUserDetail(user.getEmail(), user.getPassword(), roles);
//        return new CustomUserDetail("toto", "$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW", new ArrayList<>());
    }
}

