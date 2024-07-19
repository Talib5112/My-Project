package com.travelbnb.controller;

import com.travelbnb.payload.AppUserDto;
import com.travelbnb.payload.JWTTokenDto;
import com.travelbnb.payload.LoginDto;
import com.travelbnb.repository.AppUserRepository;
import com.travelbnb.service.Impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
   // @Autowired
    private AppUserRepository appUserRepository;
 //   @Autowired
    private UserServiceImpl userServiceImpl;

    public UserController(AppUserRepository appUserRepository, UserServiceImpl userServiceImpl) {
        this.appUserRepository = appUserRepository;
        this.userServiceImpl = userServiceImpl;
    }
    //http://localhost:8080/api/v1/user
    @PostMapping("/createUser")
    public ResponseEntity<?>createUser(
            @RequestBody AppUserDto userDto
    ){
        if(appUserRepository.existsByEmail(userDto.getEmail())){
            return new ResponseEntity<>("exists email", HttpStatus.BAD_REQUEST);
        }
        if(appUserRepository.existsByUsername(userDto.getUsername())){
            return new ResponseEntity<>("exists username",HttpStatus.BAD_REQUEST);
        }
        AppUserDto createdUser = userServiceImpl.create(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    //http://localhost:8080/api/v1/user/login
    @PostMapping ("/login")
    public ResponseEntity<?> verifyLogin(@RequestBody LoginDto loginDto) {
       String token = userServiceImpl.verifyLogin(loginDto);
       if(token != null){
           JWTTokenDto jwtToken = new JWTTokenDto();
           jwtToken.setType("JWT Token");
           jwtToken.setToken(token);

           return new ResponseEntity<>(jwtToken,HttpStatus.OK);
       }else {
           return new ResponseEntity<>("Invalid token", HttpStatus.OK);
       }
       // I am applying CRUD operation in my project



    }
    //getUserById
    //http://localhost:8080/api/v1/user/2
    @GetMapping("/{id}")
    public ResponseEntity<AppUserDto>getUserById(@PathVariable Long id){
        return new ResponseEntity<>(userServiceImpl.getUserById(id),HttpStatus.OK);

    }
    //update
    @PutMapping("/{id}")
    public ResponseEntity<AppUserDto> updateById(@PathVariable Long id, @RequestBody AppUserDto appUserDto){
        appUserDto.setPassword(BCrypt.hashpw(appUserDto.getPassword(), BCrypt.gensalt(10)));
        return new ResponseEntity<>(userServiceImpl.updateById(id, appUserDto),HttpStatus.OK);

    }
    //delete
    //http://localhost:8080/api/v1/user/1
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteById(@PathVariable Long id){
        userServiceImpl.deleteById(id);
        return new ResponseEntity<>("Deleted Record....",HttpStatus.OK);

    }
    //getAllUsersList
    //http://localhost:8080/api/v1/user
    @GetMapping
    public ResponseEntity<List<AppUserDto>> getAllUsers(){
        return new ResponseEntity<>(userServiceImpl.getAllUsers(),HttpStatus.OK);
    }
}
