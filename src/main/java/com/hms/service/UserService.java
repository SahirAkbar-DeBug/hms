package com.hms.service;

import com.hms.entity.AppUser;
import com.hms.payload.LoginDto;
import com.hms.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private AppUserRepository appUserRepository;
    public boolean verifyLogin(LoginDto loginDto){
        Optional<AppUser> opUser = appUserRepository.findByUsername(loginDto.getUsername());
        if(opUser.isPresent()){
            AppUser appUser = opUser.get();
            return BCrypt.checkpw(loginDto.getPassword(), appUser.getPassword());
        }
        else return false;
    }
}
