package com.wj.web;

import com.wj.dao.entities.AppUser;
import com.wj.service.AccountService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    
    @Autowired
    AccountService accountService;
    
    @PostMapping("/register")
    public AppUser registerUser(@RequestBody  UserForm userForm) {
        return accountService.saveUser(userForm.getUsername(), userForm.getPassword(), userForm.getConfirmedPassword());
    }
}

