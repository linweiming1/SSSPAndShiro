package com.lwm.test;

import com.lwm.smarthome.dao.UserDao;
import com.lwm.smarthome.entity.User;
import com.lwm.smarthome.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class JPATest {
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;
    @Test
    public void test1() {
        User user = new User();
        user.setAccount("1230");
        user.setId(1L);
        userService.add(user);
    }

    @Test
    public void test2(){
    User user= userDao.findByUserName("linweiming");
        System.out.println(user);
        System.out.println(user.getAccount());
    }
}
