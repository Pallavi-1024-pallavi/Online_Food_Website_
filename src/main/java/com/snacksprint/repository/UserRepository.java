package com.snacksprint.repository;


import com.snacksprint.model.user;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<user,Long> {

    public user findByEmail(String username);



}
