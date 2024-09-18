package org.example.mlooops.service;


import org.example.mlooops.dto.CustomUserDetails;
import org.example.mlooops.entity.UserEntity;
import org.example.mlooops.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository=userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity= userRepository.findByEmail(email);

        if(userEntity !=null){
            return new CustomUserDetails(userEntity);
        }

        return null;
    }
}