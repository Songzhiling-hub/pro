package com.sram.pro.realm;

import com.sram.pro.user.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class LoginRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    /*
    * 授权
    * 查询登陆人是否有权限时就查询这个  如果设置了缓存,只会查一次
    * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection collection) {
        //这个里面取的就是登陆认证时SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, getName());的第一个参数
        Object user = collection.getPrimaryPrincipal();
        //将其转为自己的用户,在从数据库获取到这个用户的角色  权限
        //这里只查询了权限
        //List<Map<String, Object>> perms_list = authorizationService.listPerms("01");
        List<Map<String, Object>> perms_list = new ArrayList<>();
        Set<String> perms_set = new HashSet<>();
        for(Map<String,Object> map : perms_list){
            perms_set.add(map.get("r_permission").toString());
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //放进去即可
        info.setStringPermissions(perms_set);
        return info;
    }

    /*
    * 认证
    * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //从令牌中读取 用户名
        String principal = (String) token.getPrincipal();
        //从数据库中读取该用户的信息
        Map<String,Object> user = userService.getUser(principal);
        //数据库中获取的密码
        Object credentials = user.get("password");
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, credentials, getName());
        return simpleAuthenticationInfo;
    }
}
