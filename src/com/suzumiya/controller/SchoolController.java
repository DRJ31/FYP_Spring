package com.suzumiya.controller;

import com.suzumiya.model.School;
import com.suzumiya.service.SchoolService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SchoolController {
    @RequestMapping(value = "/api/schools", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getAllSchools() {
        SchoolService service = new SchoolService();
        Map<String, List<School>> map = service.getSchoolsMap();
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/school", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getSchoolBySid(@RequestParam(value = "id") int id) {
        System.out.println("School ID: " + id);
        SchoolService service = new SchoolService();
        Map<String, School> map = service.getSchoolMap(id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/favorite/school", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getFavoriteSchools(@RequestParam(value = "user_id") int id) {
        SchoolService service = new SchoolService();
        Map<String, List<School>> map = service.getFavoriteSchools(id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/insertSchool",method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView insertSchool(@RequestBody School school){
        SchoolService service = new SchoolService();
        service.insertSchool(school);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/deleteSchool",method = RequestMethod.DELETE)
    @ResponseBody
    @CrossOrigin
    public ModelAndView deleteSchool(@RequestParam("id") int id){
        SchoolService service = new SchoolService();
        service.deleteSchool(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }
}
