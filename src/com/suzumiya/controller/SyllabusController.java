package com.suzumiya.controller;

import com.suzumiya.model.Syllabus;
import com.suzumiya.service.SyllabusService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;


import java.util.List;
import java.util.Map;

@Controller
public class SyllabusController {
    @RequestMapping(value = "/api/syllabuses", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getAllSyllabuses() {
        SyllabusService service = new SyllabusService();
        Map<String, List<Syllabus>> map = service.getSyllabusesMap();
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/syllabus", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getSyllabusBySid(@RequestParam(value = "id") int id) {
        System.out.println("Syllabus ID: " + id);
        SyllabusService service = new SyllabusService();
        Map<String, Syllabus> map = service.getSyllabusMap(id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/favorite/syllabus", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getFavoriteSyllabuses(@RequestParam(value = "user_id") int id) {
        SyllabusService service = new SyllabusService();
        Map<String, List<Syllabus>> map = service.getFavoriteSyllabuses(id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }
}
