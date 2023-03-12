package com.vueadmin.controller;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.code.kaptcha.Producer;
import com.vueadmin.common.lang.Const;
import com.vueadmin.common.lang.Result;
import com.vueadmin.entity.Role;
import com.vueadmin.entity.RoleUser;
import com.vueadmin.entity.User;
import com.vueadmin.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;


@RestController
public class AuthController extends BaseController{
    @Autowired
    Producer producer;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;



    @GetMapping("/captcha")
    public Result captcha() throws IOException {

        //生成32位随机验证码
        String key = UUID.randomUUID().toString();
        //生成5位数验证码
        String code = producer.createText();

        BufferedImage image =producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image,"jpg",outputStream);

        Base64Encoder encoder = new Base64Encoder();
        String str = "data:image/jpeg;base64,";

        String base64Img = str + encoder.encode(outputStream.toByteArray());

        redisUtil.hset(Const.CAPTCHA_KEY,key,code,120);

        //返回图片、key
        return Result.succ(
                MapUtil.builder()
                        .put("token",key)
                        .put("captchaImg",base64Img)
                        .build()
        );
    }


    @GetMapping("/sys/userInfo")
    public Result userInfo(Principal principal){
        User user = userService.getByUsername(principal.getName());

        return Result.succ(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .map()
        );
    }


    @GetMapping("/logon/getrolelist")
    public Result getrolelist(String name) {

        Page<Role> pageData = roleService.page(getPage(),
                new QueryWrapper<Role>()
                        .like(StrUtil.isNotBlank(name),"name",name)
        );

        return Result.succ(pageData);
    }

    @PostMapping("/logon")
    @Transactional
    public Result logon(@Validated @RequestBody User user) {

        // 设置默认密码
        String password = passwordEncoder.encode(Const.DEFULT_PASSWORD);
        user.setPassword(password);

        userService.save(user);

        Role userrole = roleService.getOne(new QueryWrapper<Role>().in("name", user.getRole()));
        RoleUser roleUser = new RoleUser();
        roleUser.setRoleId(userrole.getId());
        roleUser.setUserId(user.getId());
        roleUserService.save(roleUser);

        return Result.succ(user);
    }
}
