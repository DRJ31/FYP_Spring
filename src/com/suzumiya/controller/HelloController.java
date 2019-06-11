package com.suzumiya.controller;

import com.suzumiya.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HelloController {
    @RequestMapping(value = "/hello", method = {RequestMethod.GET})
    @ResponseBody
    public ModelAndView hello() {
        UserService service = new UserService();
        String name = service.getFirstName();
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }
}
