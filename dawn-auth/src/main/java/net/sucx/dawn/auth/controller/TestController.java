package net.sucx.dawn.auth.controller;

import com.alibaba.fastjson.JSONObject;
import net.sucx.dawn.common.annotation.HasRole;

import net.sucx.dawn.jwt.exception.DawnJwtServiceException;
import net.sucx.dawn.jwt.service.JwkUtil;
import net.sucx.dawn.jwt.service.JwtService;
import org.jose4j.jwt.JwtClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
	JwtService jwtService;

    @GetMapping("/login")
    public Map<String, Object> test1(@PathParam("loginName") String loginName, HttpServletResponse httpServletResponse) throws DawnJwtServiceException {
    	Map<String,Object> userInfo = new HashMap<>();
    	userInfo.put("login_name",loginName);
    	userInfo.put("user_name","李二狗");
    	userInfo.put("role_name","admin,user");
        String token = jwtService.createToken(userInfo);
        Map<String, Object> result = new HashMap<>();
        result.put("Authorization", "Bearer " + token);
        result.put("msg", "登录成功");
        Cookie cookie = new Cookie("Authorization", token);
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
        return result;
    }


    @PostMapping("/test2")
	@HasRole(roleName = "admin")
	public Object test2(@RequestBody JSONObject param) throws DawnJwtServiceException {
        JwtClaims claims = jwtService.parseToken(JwkUtil.getTokenStr(param.getString("Authorization")));
        System.out.println(claims.getRawJson());
//        return "验证成功user_id=" + claims.getClaimValue("user_id");
		return "验证成功附加内容为"+claims.getRawJson();
    }
	@HasRole(roleName = "123")
    @GetMapping("/test3")
    public Object test3() {
        return "我是需要被拦截的内容";
    }
}
