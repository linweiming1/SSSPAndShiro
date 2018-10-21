package com.lwm.smarthome.dao;

import com.lwm.smarthome.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
* 权限的dao层
* */
@Repository
public interface PermissionDao extends JpaRepository<Permission,Long> {
    Permission findByResource(String resource);
}
