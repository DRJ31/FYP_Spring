package com.suzumiya.controller;

import com.suzumiya.model.School;
import com.suzumiya.service.SchoolService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;
import java.util.Map;

@Controller
public class SchoolController {
    @RequestMapping(value = "/api/schools", method = {RequestMethod.GET})
    @ResponseBody
    public ModelAndView getAllSchools() {
        SchoolService service = new SchoolService();
        Map<String, List<School>> map = service.getSchoolsMap();
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/school", method = {RequestMethod.GET})
    @ResponseBody
    public ModelAndView getSchoolBySid(@RequestParam(value = "id") int id) {
        System.out.println("School ID: " + id);
        SchoolService service = new SchoolService();
        Map<String, School> map = service.getSchoolMap(id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/favorite/school", method = {RequestMethod.GET})
    @ResponseBody
    public ModelAndView getFavoriteSchools(@RequestParam(value = "user_id") int id) {
        SchoolService service = new SchoolService();
        Map<String, List<School>> map = service.getFavoriteSchools(id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }
}
