package com.suzumiya.controller;

import com.suzumiya.model.Assessment;
import com.suzumiya.model.AssessmentList;
import com.suzumiya.model.Assessment_Cilo;
import com.suzumiya.model.Assessment_CiloList;
import com.suzumiya.service.AssessmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AssessmentController {
    @RequestMapping(value = "/api/assessments", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getAllAssessments() {
        AssessmentService service = new AssessmentService();
        Map<String, List<Assessment>> map = service.getAssessmentsMap();
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/assessment", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getAssessmentById(@RequestParam(value = "id") int id) {
        AssessmentService service = new AssessmentService();
        Map<String, Assessment> map = service.getAssessmentMap(id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/assessment",method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView insertAssessment(@RequestBody AssessmentList assessmentList){
        AssessmentService service = new AssessmentService();
        List<Integer> id = new ArrayList<>();
        for (Assessment assessment : assessmentList.getAssessments()) {
            id.add(service.insertAssessment(assessment));
        }
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("ID", id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/assessment",method = RequestMethod.DELETE)
    @ResponseBody
    @CrossOrigin
    public ModelAndView deleteAssessment(@RequestParam("id") int id){
        AssessmentService service = new AssessmentService();
        service.deleteAssessment(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/acs", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getAllACs() {
        AssessmentService service = new AssessmentService();
        Map<String, List<Assessment_Cilo>> map = service.getACsMap();
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/ac", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getACById(@RequestParam(value = "id") int id) {
        AssessmentService service = new AssessmentService();
        Map<String, Assessment_Cilo> map = service.getACMap(id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/ac",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView insertAC(@RequestBody Assessment_CiloList assessment_ciloList){
        AssessmentService service = new AssessmentService();
        List<Integer> id = new ArrayList<>();
        for (Assessment_Cilo assessment_cilo : assessment_ciloList.getAssessment_cilos()) {
            id.add(service.insertAC(assessment_cilo));
        }
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("ID", id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/ac",method = RequestMethod.DELETE)
    @ResponseBody
    @CrossOrigin
    public ModelAndView deleteAC(@RequestParam("id") int id){
        AssessmentService service = new AssessmentService();
        service.deleteAC(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }
}
