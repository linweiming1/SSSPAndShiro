package com.lwm.smarthome.service;

import com.lwm.smarthome.dao.RoleDao;
import com.lwm.smarthome.entity.Permission;
import com.lwm.smarthome.entity.Role;
import com.lwm.smarthome.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/*
* 角色的service层
* */
@Service
public class RoleService {
    @Autowired
    RoleDao roleDao;
    @Autowired
    PermissionService permissionService;

    public List<String> getRolesBySysUser(User sysUser) {
        List<String> rolesList = new LinkedList<>();
        Set<Role> roles = new HashSet<>();
        roles = sysUser.getRoles();
        Iterator iterator = roles.iterator();
        while (iterator.hasNext()) {
            Role role = (Role) iterator.next();
            rolesList.add(role.getRoleName());
        }
        return rolesList;
    }

    public void savePermissions(Role role, String[] permissionList) {
        Role role1 = roleDao.getOne(role.getRoleId());
        Permission permission = null;
        role1.getPermissions().clear();
        for (String s : permissionList) {
            permission = permissionService.findPermissionByPerName(s);
                role1.getPermissions().add(permission);

        }
        roleDao.save(role1);
    }

    public void save(Set<Role>  roleSet){
        roleDao.save(roleSet);
    }
}
