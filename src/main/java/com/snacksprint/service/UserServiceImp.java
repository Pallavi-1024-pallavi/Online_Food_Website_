package com.snacksprint.service;

import com.snacksprint.config.JwtProvider;
import com.snacksprint.model.user;
import com.snacksprint.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Override
    public user findUserByJwtToken(String jwt) throws Exception {
        String email=jwtProvider.getEmailFromJwtToken(jwt);
        user user=findUserByEmail(email);




        return user;
    }

    @Override
    public user findUserByEmail(String email) throws Exception {
        user user = userRepository.findByEmail(email);
        if(user==null){
            throw new Exception("User not found");

        }


        return user;
    }
}
