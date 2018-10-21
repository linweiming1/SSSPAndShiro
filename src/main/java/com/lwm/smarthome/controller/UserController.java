package com.lwm.smarthome.controller;

import com.lwm.smarthome.entity.Role;
import com.lwm.smarthome.entity.User;
import com.lwm.smarthome.pojo.UserResp;
import com.lwm.smarthome.service.UserService;
import com.lwm.smarthome.shiro.PermissionName;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@RestController
public class UserController {
    @Autowired
    private UserService userService;
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    //测试
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        logger.info("hello");
        return "hello world";
    }

    //登陆
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(@RequestParam("name")String userName,@RequestParam("password")String passWord){
        //shiro中认证
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord);
        currentUser.login(token);
        return "success";
    }
   //退出登录
    @RequestMapping("/logout")
    public void logout(){
        logger.info("退出登录");
    }
    //添加用户
    @RequiresPermissions("user:add")
    @PermissionName("添加用户")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String saveUser(@RequestBody User user) {
        return userService.add(user);
    }
    //获取用户
    @RequiresPermissions("user:get")
    @PermissionName("获取用户")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public UserResp add(@PathVariable Long id) {
        List list=new LinkedList();
        Map role=new HashMap();
        UserResp userResp=new UserResp();
        User user= userService.getOneUser(id);
        userResp.setId(user.getId());
        userResp.setAccount(user.getAccount());
        userResp.setPassword(user.getPassword());
        userResp.setUserName(user.getUserName());
        for (Role  s:user.getRoles()){
           list.add(s.getRoleName());
        }
        role.put("角色",list);
       userResp.setRole(role);
        return userResp;
    }
}


