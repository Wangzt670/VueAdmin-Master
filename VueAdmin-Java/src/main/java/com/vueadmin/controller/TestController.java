package com.vueadmin.controller;


import com.vueadmin.common.lang.Result;
import com.vueadmin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    UserService userService;
    @GetMapping("/test")
    public Result test(){
        return Result.succ(userService.list());
    }
}
