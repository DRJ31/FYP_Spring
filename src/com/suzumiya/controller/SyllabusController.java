package com.suzumiya.controller;

import com.suzumiya.model.*;
import com.suzumiya.service.SyllabusService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;


import java.util.HashMap;
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

    @RequestMapping(value = "/api/syllabus",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView insertSyllabus(@RequestBody Syllabus syllabus){
        SyllabusService service = new SyllabusService();
        service.insertSyllabus(syllabus);
        Map<String, Syllabus> map = new HashMap<>();
        map.put("syllabus", service.getInsertedSyllabus(syllabus));
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/syllabus",method = RequestMethod.DELETE)
    @ResponseBody
    @CrossOrigin
    public ModelAndView deleteSyllabus(@RequestParam("id") int id){
        SyllabusService service = new SyllabusService();
        service.deleteSyllabus(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/cilos", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView selectAllCilo() {
        SyllabusService service = new SyllabusService();
        Map<String, List<Cilo>> map = service.selectAllCilo();
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/cilo", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView selectCiloById(@RequestParam(value = "id") int id) {
        System.out.println("cilo ID: " + id);
        SyllabusService service = new SyllabusService();
        Map<String, Cilo> map = service.selectCiloById(id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/cilo",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView insertCilo(@RequestBody Cilo cilo){
        SyllabusService service = new SyllabusService();
        service.insertCilo(cilo);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/cilo",method = RequestMethod.DELETE)
    @ResponseBody
    @CrossOrigin
    public ModelAndView deleteCilo(@RequestParam("id") int id){
        SyllabusService service = new SyllabusService();
        service.deleteCilo(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/pilo",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView insertPilo(@RequestBody Pilo pilo){
        SyllabusService service = new SyllabusService();
        service.insertPilo(pilo);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/pilo",method = RequestMethod.DELETE)
    @ResponseBody
    @CrossOrigin
    public ModelAndView deletePilo(@RequestParam("id") int id){
        SyllabusService service = new SyllabusService();
        service.deletePilo(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/pc",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView insertPC(@RequestBody Pilo_Cilo pilo_cilo){
        SyllabusService service = new SyllabusService();
        service.insertPC(pilo_cilo);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/pc",method = RequestMethod.DELETE)
    @ResponseBody
    @CrossOrigin
    public ModelAndView deletePC(@RequestParam("id") int id){
        SyllabusService service = new SyllabusService();
        service.deletePC(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/tla",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView insertTla(@RequestBody Tla tla){
        SyllabusService service = new SyllabusService();
        service.insertTla(tla);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/tla",method = RequestMethod.DELETE)
    @ResponseBody
    @CrossOrigin
    public ModelAndView deleteTla(@RequestParam("id") int id){
        SyllabusService service = new SyllabusService();
        service.deleteTla(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/content",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView insertContent(@RequestBody Content content){
        SyllabusService service = new SyllabusService();
        service.insertContent(content);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/content",method = RequestMethod.DELETE)
    @ResponseBody
    @CrossOrigin
    public ModelAndView deleteContent(@RequestParam("id") int id){
        SyllabusService service = new SyllabusService();
        service.deleteContent(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }
}
