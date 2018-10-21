package com.lwm.test;

import com.lwm.smarthome.dao.UserDao;
import com.lwm.smarthome.entity.Permission;
import com.lwm.smarthome.entity.User;
import com.lwm.smarthome.service.PermissionService;
import com.lwm.smarthome.service.RoleService;
import com.lwm.smarthome.shiro.PermissionName;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PermissionTest {

private static Logger logger= LoggerFactory.getLogger(PermissionTest.class);
    @Autowired
    UserDao sysUserDao;
    @Autowired
    PermissionService permissionService;
    @Autowired
    RoleService roleService;
    RequestMappingHandlerMapping requestMappingHandlerMapping = new RequestMappingHandlerMapping();
/*用于更新权限列表到数据库中*/
    @Test
    public void reload() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
        Collection<HandlerMethod> handlerMethods = handlerMethodMap.values();
        for (HandlerMethod handlerMethod : handlerMethods) {
            RequiresPermissions anno = handlerMethod.getMethodAnnotation(RequiresPermissions.class);
            if (anno != null) {
                String resource = anno.value()[0];
                Permission p = new Permission();
                p.setResource(resource);
                String permissionName = handlerMethod.getMethodAnnotation(PermissionName.class).value();
                p.setPermissionName(permissionName);
                System.out.println("资源" + resource);
                System.out.println("资源名" + permissionName);
            }
        }
    }

    @Test
    public void testRoleAndPermission() {
        User sysUser=sysUserDao.findByUserName("linweiming");
        Set<String> permissionSet=new HashSet<>();
        List<String> roleList=new LinkedList<>();
        permissionSet=permissionService.getPermissionBySysUser(sysUser);
        roleList=roleService.getRolesBySysUser(sysUser);
        System.out.println(permissionSet);
        System.out.println(roleList);
    }

}
