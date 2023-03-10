package com.vueadmin.controller;

import com.vueadmin.common.lang.Result;
import com.vueadmin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @PreAuthorize("hasRole('admin')")
    @GetMapping("/test")
    public Result test() {
        return Result.succ(userService.list());
    }

    @PreAuthorize("hasAuthority('sys:user:list')")
    @GetMapping("/test/pass")
    public Result pass() {

        String password =  bCryptPasswordEncoder.encode("123456");

        boolean matches = bCryptPasswordEncoder.matches("123456",password);

        System.out.println("匹配结果：" + matches);

        return Result.succ(password);
    }
}
