package com.suzumiya.controller;

import com.suzumiya.model.Token;
import com.suzumiya.model.User;
import com.suzumiya.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    private Map<String, User> loginMap = new HashMap<>();

    @RequestMapping(value = "/api/users", method = {RequestMethod.GET})
    @ResponseBody
    public ModelAndView getAllUsers() {
        UserService service = new UserService();
        Map<String, List<User>> map = service.getUsersMap();
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/user", method = {RequestMethod.GET})
    @ResponseBody
    public ModelAndView getUserByUid(@RequestParam(value = "id") int id) {
        UserService service = new UserService();
        Map<String, User> map = service.getUserMap(id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/insertUser",method = RequestMethod.POST)
    @ResponseBody
    public void insertUser(@RequestBody User user){
        UserService service = new UserService();
        user.encryptPassword();
        service.insertUser(user);
    }

    @RequestMapping(value = "/api/deleteUser",method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteUser(@RequestParam("id") int id){
        UserService service = new UserService();
        service.deleteUser(id);
    }

    @RequestMapping(value = "/api/user", method = {RequestMethod.POST})
    @ResponseBody
    public ModelAndView userLogin(@RequestBody User user){
        String token = System.currentTimeMillis() + user.getName();
        EncryptController e = new EncryptController();
        user.encryptPassword();
        token = e.encrypt(token);
        UserService service = new UserService();
        User u = service.getUserByName(user);
        Iterator<User> iter = loginMap.values().iterator();
        while (iter.hasNext()){
            User user1 = iter.next();
            if (user1.getName().equals(user.getName())){
                return new ModelAndView(new MappingJackson2JsonView(), loginMap);
            }
            else {
                continue;
            }
        }
        loginMap.put(token, u);
        return new ModelAndView(new MappingJackson2JsonView(), loginMap);
    }

    @RequestMapping(value = "/api/token", method = {RequestMethod.POST})
    @ResponseBody
    public ModelAndView userLoginStatus(@RequestBody Token token){
        User user = loginMap.get(token.getToken());
        Map<String, User> map = new HashMap<>();
        map.put("user", user);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/logout", method = {RequestMethod.POST})
    @ResponseBody
    public String userLogout(@RequestBody Token token){
        String name = null;
        try {
            name = loginMap.get(token.getToken()).getName();
            loginMap.remove(token.getToken());
        }catch (Exception e){
            e.printStackTrace();
        }
        return name;
    }
}
