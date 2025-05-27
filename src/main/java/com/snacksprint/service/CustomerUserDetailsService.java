package com.snacksprint.service;


import com.snacksprint.model.USER_ROLE;
import com.snacksprint.model.user;
import com.snacksprint.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        user user =userRepository.findByEmail(username);
        if(user==null)
        {
            throw new UsernameNotFoundException("user not found"+username);

        }
        USER_ROLE role=user.getRole();
        if(role==null)role=USER_ROLE.ROLE_CUSTOMER;
        List<GrantedAuthority>authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.toString()));






        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);

    }
}
