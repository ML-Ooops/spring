package org.example.mlooops.service;


import org.example.mlooops.dto.CustomUserDetails;
import org.example.mlooops.entity.UserEntity;
import org.example.mlooops.entity.UserInterestEntity;
import org.example.mlooops.repository.UserInterestRepositoty;
import org.example.mlooops.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    private UserInterestRepositoty userInterestRepositoty;

    public CustomUserDetailsService(UserRepository userRepository , UserInterestRepositoty userInterestRepositoty){
        this.userRepository=userRepository;
        this.userInterestRepositoty=userInterestRepositoty;

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity= userRepository.findByEmail(email);

        if(userEntity !=null){
            return new CustomUserDetails(userEntity);
        }

        return null;
    }

    public float[] getUserCategory(String email){
        UserEntity userEntity= userRepository.findByEmail(email);
        int user_id=userEntity.getUserId();
        float[] temp = new float[6];
        if(userInterestRepositoty.existsByUserId(user_id) != null){
            UserInterestEntity[] userInterestEntity=userInterestRepositoty.findAllByUserId(user_id);
            System.out.println("length : "+userInterestEntity.length);
            for(UserInterestEntity userInterestEntity1:userInterestEntity){
                temp[userInterestEntity1.getInterestId()-1]=userInterestEntity1.getCategoryDouble();
            }
            return temp;
        }
        return temp;

    }
}