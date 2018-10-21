package com.lwm.smarthome.service;

import com.lwm.smarthome.dao.PermissionDao;
import com.lwm.smarthome.entity.Permission;
import com.lwm.smarthome.entity.Role;
import com.lwm.smarthome.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/*
* 权限的service层
* */
@Service
public class PermissionService {

    private Permission permission;
    @Autowired
    PermissionDao permissionDao;

    //根据用户获取权限资源
    public Set<String> getPermissionBySysUser(User sysUser) {
        Set<String> permissionsSet = new HashSet<>();
        Set<Role> roles = new HashSet<>();
        roles = sysUser.getRoles();
        Iterator iterator = roles.iterator();
        while (iterator.hasNext()) {
            Role role = (Role) iterator.next();
            Set<Permission> permissionSet = new HashSet<>();
            permissionSet = role.getPermissions();
            Iterator iterator1 = permissionSet.iterator();
            while (iterator1.hasNext()) {
                Permission permission = (Permission) iterator1.next();
                permissionsSet.add(permission.getResource());
            }
        }
        return permissionsSet;
    }

    //根据用户名取得权限资源名
    public Set<Permission> getPermissionNameBySysUser(User sysUser) {
        Set<Permission> permissionsSet = new HashSet<>();
        Set<Role> roles = new HashSet<>();
        roles = sysUser.getRoles();
        Iterator iterator = roles.iterator();
        while (iterator.hasNext()) {
            Role role = (Role) iterator.next();
            Set<Permission> permissionSet = new HashSet<>();
            permissionSet = role.getPermissions();
            Iterator iterator1 = permissionSet.iterator();
            while (iterator1.hasNext()) {
                Permission permission = (Permission) iterator1.next();
                permissionsSet.add(permission);
            }

        }
        return permissionsSet;
    }

    public Permission findPermissionByPerName(String perName) {
        return permissionDao.findByResource(perName);
    }
}
