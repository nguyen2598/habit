package com.habittracke.mapper;


import com.habittracke.dto.request.UserCreate;
import com.habittracke.dto.response.UserDTO;
import com.habittracke.dto.response.UserResponse;
import com.habittracke.entity.sql.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserCreate userToUserCreate(User user);
    UserResponse userToUserResponse(User user);
    User userCreateToUser(UserCreate userCreate);
    UserDTO userToUserDTO(User user);
}
