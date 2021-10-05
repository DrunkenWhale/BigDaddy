package com.example.bigdaddy.controller;


import com.example.bigdaddy.model.User;
import com.example.bigdaddy.repository.UserRepository;
import com.example.bigdaddy.util.JwtUtils;
import com.example.bigdaddy.util.PasswordEncryptor;
import com.example.bigdaddy.util.response.TokenResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/auth/login")
@RestController
public class LoginController {

    final    // 要记得加注解呐
    UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping
    @ResponseBody
    public TokenResponse login(
            @RequestParam(value = "mailbox" ,required = true) String mailbox,
            @RequestParam(value = "password",required = true) String password){
            Optional<User> optionalUser = userRepository.findById(mailbox);
            if(optionalUser.isPresent()){
                // 用户存在
                User user = optionalUser.get();
                if (PasswordEncryptor.checkPasswordHash(password,user.getPassword())){ // 使用equal可以安全判空
                    // 密码正确
                    return new TokenResponse(1,"Succeed", JwtUtils.generateJwt(mailbox));
                }else{
                    return new TokenResponse(0,"PasswordWrong",null);
                }
            }else{
                return new TokenResponse(0,"PasswordWrong",null);
            }
    }


}
