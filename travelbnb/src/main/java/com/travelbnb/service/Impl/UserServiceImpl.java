package com.travelbnb.service.Impl;


import com.travelbnb.entity.AppUser;
import com.travelbnb.exception.ResourceNotFoundException;
import com.travelbnb.payload.AppUserDto;
import com.travelbnb.payload.LoginDto;
import com.travelbnb.repository.AppUserRepository;
import com.travelbnb.service.UserService1;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService1 {


    private AppUserRepository appUserRepository;
    private JWTServiceImpl jwtServiceImpl;

    public UserServiceImpl(AppUserRepository appUserRepository, JWTServiceImpl jwtServiceImpl) {
        this.appUserRepository = appUserRepository;
        this.jwtServiceImpl = jwtServiceImpl;
    }
//CreateUser
    @Override
    public AppUserDto create(AppUserDto userDto) {
        AppUser appUser = mapToEntity(userDto);
        AppUser savedUser = appUserRepository.save(appUser);
        return mapToDto(savedUser);
    }
//verifyLogin
    @Override
    public String verifyLogin(LoginDto loginDto) {
        Optional<AppUser> opUserName = appUserRepository.findByUsername(loginDto.getUsername());
        if(opUserName.isPresent()){
            AppUser appUser = opUserName.get();
            System.out.println(appUser);
         if(BCrypt.checkpw(loginDto.getPassword(),appUser.getPassword())) {
            String token = jwtServiceImpl.generateToken(appUser);
            return token;
         }

        }
        return null;
    }

    @Override
    public List<AppUserDto> getAllUsers() {
        List<AppUser> all = appUserRepository.findAll();
        return all.stream().map(
                appUser -> mapToDto(appUser)
        ).toList();
    }


    //getUserById
    @Override
    public AppUserDto getUserById(Long id) {
        AppUser appUser = appUserRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("user not found with id : " + id)
        );
        return mapToDto(appUser);
    }
    //updateById
    @Override
    public AppUserDto updateById(Long id, AppUserDto appUserDto) {
        AppUser appUser = appUserRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("user not found with id : " + id));
        appUser.setId(appUserDto.getId());
        appUser.setName(appUserDto.getName());
        appUser.setUsername(appUserDto.getUsername());
        appUser.setEmail(appUserDto.getEmail());
        appUser.setPassword(appUserDto.getPassword());
        appUser.setRole(appUserDto.getRole());

        AppUser updateUser = appUserRepository.save(appUser);
        return mapToDto(updateUser);
    }


    //deleteById
    @Override
    public void deleteById(Long id) {
        appUserRepository.deleteById(id);


    }


    //for using crud operation
    AppUserDto mapToDto(AppUser appUser){
        AppUserDto appUserDto = new AppUserDto();
        appUserDto.setId(appUser.getId());
        appUserDto.setName(appUser.getName());
        appUserDto.setUsername(appUser.getUsername());
        appUserDto.setEmail(appUser.getEmail());
        appUserDto.setPassword(appUser.getPassword());
        appUserDto.setRole(appUser.getRole());
        return appUserDto;
    }

    AppUser mapToEntity(AppUserDto appUserDto) {
        AppUser appUser = new AppUser();
        appUser.setId(appUserDto.getId());
        appUser.setName(appUserDto.getName());
        appUser.setUsername(appUserDto.getUsername());
        appUser.setEmail(appUserDto.getEmail());
        appUser.setPassword(BCrypt.hashpw(appUserDto.getPassword(),BCrypt.gensalt(10)));
        appUser.setRole(appUserDto.getRole());
        return appUser;
    }
}

