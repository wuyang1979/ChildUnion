package com.qinzi123.controller.web;

import com.qinzi123.dto.LoginUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @title: LoginController
 * @package: com.qinzi123.controller.web
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/loginPost", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> loginPost(String user, String password, HttpSession httpSession) {
        Map<String, Object> map = new HashMap<String, Object>();

        if ("luckSystem".equals(user) && "88888888".equals(password)) {
            httpSession.setAttribute(LoginFilter.LUCK_SESSION_KEY, user);
            System.out.println("outside login success");
            map.put("success", true);
            //message:1--外部人员登录
            map.put("message", "1");

            return map;
        } else if (LoginUser.getLoginUser(user, password) == null) {
            map.put("success", false);
            map.put("message", "011");
            return map;
        } else {
            httpSession.setAttribute(LoginFilter.SESSION_KEY, user);
            System.out.println("login success");
            map.put("success", true);
            //message:0--内部人员登录
            map.put("message", "0");

            return map;
        }
    }

    @RequestMapping(value = "/loginOut", method = RequestMethod.GET)
    public String loginOut(HttpSession httpSession) {
        httpSession.removeAttribute(LoginFilter.SESSION_KEY);
        return String.format("redirect:%s", LoginFilter.LOGIN);
    }
}
