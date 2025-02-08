package com.hms.controller;

import com.hms.entity.AppUser;
import com.hms.payload.LoginDto;
import com.hms.payload.TokenDto;
import com.hms.repository.AppUserRepository;
import com.hms.service.JWTService;
import com.hms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private JWTService jwtSerive;


    @PostMapping("/signUp")
    public ResponseEntity<?> createUser(
            @RequestBody AppUser appUser
    ) {
        Optional<AppUser> opUsername = appUserRepository.findByUsername(appUser.getUsername());
        if (opUsername.isPresent()) {
            return new ResponseEntity<>("username already exits", HttpStatus.BAD_REQUEST);
        }
        Optional<AppUser> byEmail = appUserRepository.findByEmail(appUser.getEmail());
        if (byEmail.isPresent()) {
            return new ResponseEntity<>("Email already exits", HttpStatus.BAD_REQUEST);
        }
        String hashpw = BCrypt.hashpw(appUser.getPassword(), BCrypt.gensalt(5));
        appUser.setPassword(hashpw);
        AppUser save = appUserRepository.save(appUser);

        return new ResponseEntity<>(appUser, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<String> getMessage() {
        return new ResponseEntity<>("sahir", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginDto loginDto
    ) {
        boolean b = userService.verifyLogin(loginDto);
        if (b) {
            String token = jwtSerive.generateToken(loginDto.getUsername());
            TokenDto tokenDto= new TokenDto();
            tokenDto.setToken(token);
            tokenDto.setType("JWT");
            return new ResponseEntity<>(tokenDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Incorrect username of password", HttpStatus.FORBIDDEN);
        }
    }
}


