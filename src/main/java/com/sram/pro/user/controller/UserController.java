package com.sram.pro.user.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return "成功";
    }

    /*登录*/
    @RequestMapping("/login")
    public String logining(@RequestParam("username") String username, @RequestParam("pwd") String pwd){
        Subject curUser = SecurityUtils.getSubject();

        if (!curUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(username, pwd);//令牌
            token.setRememberMe(true);
            try {
                curUser.login(token);
            } catch (UnknownAccountException uae) {//用户名不存在
                System.out.println("There is no user with username of " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {//密码不正确
                System.out.println("Password for account " + token.getPrincipal() + " was incorrect!");
            } catch (LockedAccountException lae) {//账户被锁定
                System.out.println("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            }
        }
        return "redirect:/pages/index.html";
    }
}
