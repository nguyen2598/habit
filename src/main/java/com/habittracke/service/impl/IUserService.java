package com.habittracke.service.impl;

import com.habittracke.dto.request.UserCreate;
import com.habittracke.dto.response.AccountResponse;
import com.habittracke.dto.response.UserDTO;
import com.habittracke.dto.response.UserResponse;
import com.habittracke.entity.sql.User;
import com.habittracke.exception.UserException;
import com.habittracke.mapper.UserMapper;
import com.habittracke.repository.UserRepository;
import com.habittracke.service.UserService;
import com.habittracke.utils.JwtToken;
import com.habittracke.utils.Translator;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service

@AllArgsConstructor
public class IUserService implements UserService {
    public final UserRepository userRepository;
    public final UserMapper userMapper;
    public final PasswordEncoder passwordEncoder;
    public final JwtToken jwtToken;

    @Override
    public AccountResponse login(UserCreate request) {
        return null;
    }

    @Override
    public AccountResponse register(UserCreate request) {

        if (request.getEmail().isEmpty()) {
            throw new UserException("[REGIS000001] email is empty");
        }
        if (request.getPassword().isEmpty()) {
            throw new UserException("[REGIS000002] password is empty");
        }
        if (request.getUsername().isEmpty()) {
            throw new UserException("[REGIS000003] username is empty");
        }
        Optional<User> userData = userRepository.findByUsernameOrEmail(request.getUsername(), request.getEmail());
        if (userData.isPresent()) {
            throw new UserException("[REGIS000004]" + Translator.toLocaleVi("user.unique"));
        }
        try {
            User user = User.builder()
                    .id(UUID.randomUUID().toString())
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .email(request.getEmail())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            User savedUser = userRepository.save(user);
            UserResponse userResponse = userMapper.userToUserResponse(savedUser);
            String token = jwtToken.generateToken(userMapper.userToUserDTO(savedUser));
            return new AccountResponse(token, userResponse);
        } catch (Exception ex) {
            throw new UserException("[REGIS000001] " + ex.getMessage());
        }
    }

    @Override
    public UserDTO getUserById(String id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserException("[GUI000001] " + "user.not.found");
        }
        return userMapper.userToUserDTO(user);
    }
}
