package com.vueadmin.controller;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.vueadmin.common.lang.Const;
import com.vueadmin.common.lang.Result;
import com.vueadmin.entity.User;
import com.vueadmin.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/captcha")
    public Result captcha() throws IOException {

        //生成32位随机验证码
        String key = UUID.randomUUID().toString();
        //生成5位数验证码
        String code = producer.createText();

        //测试
        key = "aaaaa";
        code = "11111";

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
}
