package com.lwm.smarthome.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lwm.smarthome.entity.*;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByUserName(String userName);
}