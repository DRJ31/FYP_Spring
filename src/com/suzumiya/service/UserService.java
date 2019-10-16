package com.suzumiya.service;

import com.suzumiya.dao.SchoolDao;
import com.suzumiya.dao.SyllabusDao;
import com.suzumiya.dao.UserDao;
import com.suzumiya.model.Role;
import com.suzumiya.model.School;
import com.suzumiya.model.Syllabus;
import com.suzumiya.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    private List<User> users;
    private UserDao userDao;
    private User user;

    public UserService() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
        this.userDao = (UserDao) ac.getBean("userDao");
        this.setUsers();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUser() {
        return user;
    }

    public List<User> getUsers() {
        return users;
    }

    private void setUsers() {
        this.users = userDao.selectAll();
    }

    private void setUser(int uid) {
        this.user = userDao.selectById(uid);
    }

    private void setUserByName(User user){
        this.user = userDao.userLogin(user);
    }

    public Map<String, List<User>> getUsersMap() {
        Map<String, List<User>> result = new HashMap<>();
        result.put("users", users);
        return result;
    }

    public Map<String, User> getUserMap(int uid) {
        Map<String, User> result = new HashMap<>();
        setUser(uid);
        result.put("user", user);
        return result;
    }

    public User getUserByName(User u){
        setUserByName(u);
        return this.user;
    }

    public void insertUser(User user){
        userDao.insertUser(user);
    }

    public void deleteUser(int id){
        userDao.deleteUser(id);
    }

    public User checkUserDuplicate(User user){
        return userDao.checkUserDuplicate(user);
    }

    public void updateSchoolRole(int id){
        userDao.updateSchoolRole(id);
    }

    public void updateSchoolId(User user){
        userDao.updateSchoolId(user);
    }

    public void updatePassword(User user){
        userDao.updatePassword(user);
    }
}
