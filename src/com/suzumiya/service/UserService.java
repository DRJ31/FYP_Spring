package com.suzumiya.service;

import com.alibaba.fastjson.JSONObject;
import com.suzumiya.dao.RedisDao;
import com.suzumiya.dao.UserDao;
import com.suzumiya.model.user.Avatar;
import com.suzumiya.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    private List<User> users;
    private UserDao userDao;
    private User user;
    private List<User> user_S;
    private RedisDao redisDao;

    public void setRedisDao(RedisDao redisDao) {
        this.redisDao = redisDao;
    }

    public List<User> getUser_S() {
        return user_S;
    }

    public void setUser_S(int s_id) {
        this.user_S = userDao.selectUser_S(s_id);
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
        String userJson = redisDao.get("user_" + user.getName());
        if (StringUtils.isEmpty(userJson)) {
            this.user = userDao.userLogin(user);
            if (this.user != null) {
                redisDao.set("user_" + this.user.getName(), JSONObject.toJSONString(this.user));
                redisDao.set("user_id_" + this.user.getId(), JSONObject.toJSONString(this.user));
            }
        }
        else {
            User realUser = JSONObject.parseObject(userJson, User.class);
            this.user = realUser.getPassword().equals(user.getPassword()) ? realUser : null;
        }
    }

    public User getUserByEmail(String email) {
        this.user = userDao.selectByMail(email);
        return this.user;
    }

    public Map<String, List<User>> getUsersMap() {
        Map<String, List<User>> result = new HashMap<>();
        this.setUsers();
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
        String userJson = redisDao.get("user_id_" + id);
        if (!StringUtils.isEmpty(userJson)) {
            User deleted = JSONObject.parseObject(userJson, User.class);
            redisDao.del("user_" + deleted.getName());
            redisDao.del("user_id_" + id);
        }
    }

    public User checkUserDuplicate(User user){
        return userDao.checkUserDuplicate(user);
    }

    public void updateSchoolRole(int id){
        userDao.updateSchoolRole(id);
    }

    public void updateTeacherRole(int id){
        userDao.updateTeacherRole(id);
        String userJson = redisDao.get("user_id_" + id);
        if (!StringUtils.isEmpty(userJson)) {
            User modified = JSONObject.parseObject(userJson, User.class);
            modified.setRole_id(3);
            redisDao.set("user_id_" + id, JSONObject.toJSONString(modified));
            redisDao.set("user_" + modified.getName(), JSONObject.toJSONString(modified));
        }
    }

    public void updateSchoolId(User user){
        userDao.updateSchoolId(user);
    }

    public void updatePassword(User user){
        userDao.updatePassword(user);
        String userJson = redisDao.get("user_" + user.getName());
        if (!StringUtils.isEmpty(userJson)) {
            redisDao.set("user_id_" + user.getId(), JSONObject.toJSONString(user));
            redisDao.set("user_" + user.getName(), JSONObject.toJSONString(user));
        }
    }

    public Map<String, List<User>> selectUser_S(int s_id) {
        Map<String, List<User>> result = new HashMap<>();
        setUser_S(s_id);
        result.put("users", user_S);
        return result;
    }

    public void updateUser(User user) throws Exception{
        userDao.updateUser(user);
        String jsonString = JSONObject.toJSONString(user);
        redisDao.set("user_" + user.getName(), jsonString);
        redisDao.set("user_id_" + user.getId(), jsonString);
    }

    public Avatar selectAvatar(int id){
        String avatarJson = redisDao.get("avatar_" + id);
        Avatar avatar;
        if (StringUtils.isEmpty(avatarJson)) {
            avatar = userDao.selectAvatar(id);
            if (avatar != null) {
                redisDao.set("avatar_" + id, JSONObject.toJSONString(avatar));
            }
        }
        else {
            avatar = JSONObject.parseObject(avatarJson, Avatar.class);
        }
        return avatar;
    }

    public void insertAvatar(Avatar avatar) throws Exception{
        userDao.insertAvatar(avatar);
        redisDao.set("avatar_" + avatar.getUser_id(), JSONObject.toJSONString(avatar));
    }

    public void updateAvatar(Avatar avatar) {
        userDao.updateAvatar(avatar);
        redisDao.set("avatar_" + avatar.getUser_id(), JSONObject.toJSONString(avatar));
    }
}
