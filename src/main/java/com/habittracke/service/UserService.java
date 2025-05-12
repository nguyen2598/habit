package com.habittracke.service;

import com.habittracke.dto.request.UserCreate;
import com.habittracke.dto.response.AccountResponse;
import com.habittracke.dto.response.UserDTO;


public interface UserService {
    AccountResponse login(UserCreate request);
    AccountResponse register(UserCreate request);
    UserDTO getUserById(String id);
}
