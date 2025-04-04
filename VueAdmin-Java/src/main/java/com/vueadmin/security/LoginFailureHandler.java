package com.vueadmin.security;

import cn.hutool.json.JSONUtil;
import com.vueadmin.common.lang.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();

        Result result = Result.fail(exception.getMessage());

        if(exception.getMessage()=="Bad credentials"){
            result = Result.fail("用户名或密码错误");
        } else if (exception.getMessage()=="User is disabled") {
            result = Result.fail("用户被禁用");
        }else if (exception.getMessage()=="User account is locked") {
            result = Result.fail("用户所属角色被禁用");
        }

        outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));

        outputStream.flush();
        outputStream.close();
    }
}
