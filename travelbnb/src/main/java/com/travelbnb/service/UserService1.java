package com.travelbnb.service;

import com.travelbnb.payload.AppUserDto;
import com.travelbnb.payload.LoginDto;

import java.util.List;

public interface UserService1 {
    //create user
    AppUserDto create(AppUserDto userDto);
    //show user by id
    AppUserDto getUserById(Long id);

    //updateuser by id
    AppUserDto updateById(Long id,AppUserDto appUserDto);

    //delete by id
    void deleteById(Long id);

    String verifyLogin(LoginDto loginDto);

    //getAllUsers
    List<AppUserDto> getAllUsers();
}

