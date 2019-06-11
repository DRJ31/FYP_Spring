package com.suzumiya.service;

import com.suzumiya.dao.UserDao;
import com.suzumiya.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class UserService {
    private List<User> users;
    private UserDao userDao;

    public UserService() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
        this.userDao = (UserDao) ac.getBean("userDao");
        this.setUsers();
    }

    public List<User> getUsers() {
        return users;
    }

    private void setUsers() {
        this.users = userDao.selectAll();
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getFirstName() {
        return this.users.get(0).getName();
    }
}
