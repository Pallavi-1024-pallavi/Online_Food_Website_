package com.snacksprint.service;

import com.snacksprint.model.user;

public interface UserService {
    public user findUserByJwtToken(String jwt) throws Exception;

    public user findUserByEmail(String email) throws Exception;


}
