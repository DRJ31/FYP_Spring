package com.suzumiya.controller;

import com.suzumiya.model.*;
import com.suzumiya.model.list.CiloList;
import com.suzumiya.model.list.ContentList;
import com.suzumiya.model.list.PiloList;
import com.suzumiya.model.list.TlaList;
import com.suzumiya.model.relationship.PiloCilo;
import com.suzumiya.model.relationship.list.PiloCiloList;
import com.suzumiya.service.SyllabusService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
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
    public ModelAndView insertCilo(@RequestBody CiloList cilos){
        SyllabusService service = new SyllabusService();
        List<Integer> id = new ArrayList<>();
        for (Cilo cilo : cilos.getCilos()) {
            id.add(service.insertCilo(cilo));
        }
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("ID", id);
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
    public ModelAndView insertPilo(@RequestBody PiloList pilos){
        SyllabusService service = new SyllabusService();
        List<Integer> id = new ArrayList<>();
        for (Pilo pilo : pilos.getPilos()) {
            id.add(service.insertPilo(pilo));
        }
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("ID", id);
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
    public ModelAndView insertPC(@RequestBody PiloCiloList piloCilos){
        SyllabusService service = new SyllabusService();
        for (PiloCilo pilo_cilo : piloCilos.getPiloCilos()) {
            service.insertPC(pilo_cilo);
        }
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
    public ModelAndView insertTla(@RequestBody TlaList tlas){
        SyllabusService service = new SyllabusService();
        for (Tla tla : tlas.getTlas()) {
            service.insertTla(tla);
        }
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
    public ModelAndView insertContent(@RequestBody ContentList contents){
        SyllabusService service = new SyllabusService();
        List<Integer> id = new ArrayList<>();
        for (Content content : contents.getContents()) {
            id.add(service.insertContent(content));
        }
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("ID", id);
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

    @RequestMapping(value = "/api/updateSyllabus", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public ModelAndView updateEmail(@RequestBody Syllabus syllabus){
        SyllabusService syllabusService = new SyllabusService();
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", false);
        syllabusService.updateSyllabus(syllabus);
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }
}
