package com.suzumiya.service;

import com.suzumiya.dao.UserMapper;
import com.suzumiya.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class UserService {
    private List<User> users;
    private UserMapper userMapper;

    public UserService() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
        this.userMapper = (UserMapper) ac.getBean("userDao");
        this.setUsers();
    }

    public List<User> getUsers() {
        return users;
    }

    private void setUsers() {
        this.users = userMapper.selectAll();
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getFirstName() {
        return this.users.get(0).getName();
    }
}
