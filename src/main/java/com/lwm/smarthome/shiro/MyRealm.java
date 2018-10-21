package com.lwm.smarthome.shiro;


import com.lwm.smarthome.dao.UserDao;
import com.lwm.smarthome.entity.User;
import com.lwm.smarthome.service.PermissionService;
import com.lwm.smarthome.service.RoleService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/*
* shiro的数据源
* */
public class MyRealm extends AuthorizingRealm {
    @Autowired
    UserDao userDao;
    @Autowired
    PermissionService permissionService;
    @Autowired
    RoleService roleService;
    private static Logger logger = LoggerFactory.getLogger(MyRealm.class);

    /**
     * 授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        User sysUser = (User) principals.fromRealm(getName()).iterator().next();
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        if (sysUser != null) {
            Set<String> permissionSet = new HashSet<>();
            List<String> roleList = new LinkedList<>();
            permissionSet = permissionService.getPermissionBySysUser(sysUser);
            roleList = roleService.getRolesBySysUser(sysUser);
            Iterator iterator = permissionSet.iterator();
            while (iterator.hasNext()) {
                String permission = (String) iterator.next();
                simpleAuthorInfo.addStringPermission(permission);
            }
            simpleAuthorInfo.addRoles(roleList);
        }

        // 若该方法什么都不做直接返回null的话,就会导致任何用户访问都会自动跳转到unauthorizedUrl指定的地址
        return simpleAuthorInfo;
    }

    /**
     * 认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String userName = token.getUsername();
        User sysUser = userDao.findByUserName(userName);
        //此处无需比对,比对的逻辑Shiro会做,我们只需返回一个和令牌相关的正确的验证信息
        //说白了就是第一个参数填登录用户名,第二个参数填合法的登录密码(可以是从数据库中取到的,本例中为了演示就硬编码了)
        if (sysUser == null) {
            return null;
        }
        //1).principle:认证的实体信息，可以是userName，也可以是数据表对应的用户的实体类对象
        AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(sysUser, sysUser.getPassword(), this.getName());

        return authcInfo;
    }

}