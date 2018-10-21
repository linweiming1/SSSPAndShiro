package com.lwm.smarthome.entity;

import javax.persistence.*;
import java.util.Set;

/*
* 角色实体类
* */
@Entity
@Table(name = "role")
public class Role {
    private Long roleId;
    private String roleName;
    private String roleDesc;
    private Set<Permission> permissions;
    private Set<User> sysUsers;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getRoleId() {
        return roleId;
    }


    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Column(name = "role_name")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Column(name = "role_desc")
    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission", joinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "roleId")}, inverseJoinColumns = {
            @JoinColumn(name = "permission_id", referencedColumnName = "permissionId")})
    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "roleId")}, inverseJoinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id")})
    public Set<User> getSysUsers() {
        return sysUsers;
    }

    public void setSysUsers(Set<User> sysUsers) {
        this.sysUsers = sysUsers;
    }
}
