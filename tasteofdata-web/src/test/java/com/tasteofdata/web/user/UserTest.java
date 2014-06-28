package com.tasteofdata.web.user;

import com.tasteofdata.land.module.user.entry.User;
import com.tasteofdata.land.module.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by wwj on 2014/6/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/*.xml")
//@Transactional
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class UserTest {
    @Autowired
    UserService userService;

    @Test
    public void useradd() {
        User user = new User();
        user.setUserName("aa");
        user.setEmail("aa@test.com");
        user.setPassword("123");
        userService.addUser(user);
        System.out.println("------------------"+user.getId());
    }
}
