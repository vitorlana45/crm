package com.lanDev.crm.adapter.inbound.web.security;

import com.lanDev.crm.domain.model.user.User;
import com.lanDev.crm.domain.port.outbound.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthorizationService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User domainUser = userRepository.getUserByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("Usuário não encontrado: " + username)
        );

        return new CustomUserDetails(domainUser);
    }
}
