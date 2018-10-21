package com.lwm.smarthome.entity;

import javax.persistence.*;
import java.util.Set;

/*
* 权限实体类
* */
@Entity
@Table(name = "permission")
public class Permission {
    private Long permissionId;
    private String permissionName;
    private String resource;
    private Set<Role> roles;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    @Column(name = "permission_name")
    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @Column(name = "resource")
    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "role_permission", joinColumns = {
            @JoinColumn(name = "permission_id", referencedColumnName = "permissionId")}, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "roleId")})
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
