package com.suzumiya.controller;

import com.suzumiya.model.User;
import com.suzumiya.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;


import java.util.List;
import java.util.Map;

@Controller
public class UserController {

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
        System.out.println("User ID: " + id);
        UserService service = new UserService();
        Map<String, User> map = service.getUserMap(id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/insertUser",method = RequestMethod.POST)
    @ResponseBody
    public void insertUser(@RequestBody User user){
        UserService service = new UserService();
        service.insertUser(user);
    }

    @RequestMapping(value = "/api/testUser",method = RequestMethod.GET)
    @ResponseBody
    public String  testUser(){
        User user = new User("name","email","password",4);
        String s = user.toString();
        return s;
    }

//    @RequestMapping(value = "/api/insertUser",method = RequestMethod.POST)
//    @ResponseBody
//    public void insertUser(@RequestParam("name")String name, @RequestParam("email")String email, @RequestParam("role_id")int role_id, @RequestParam("password")String password){
//        User user = new User(name, password, email, role_id);
//        UserService service = new UserService();
//        service.insertUser(user);
//    }
}
