package com.suzumiya.controller;

import com.suzumiya.model.Assessment;
import com.suzumiya.model.list.AssessmentList;
import com.suzumiya.model.relationship.AssessmentCilo;
import com.suzumiya.model.relationship.list.AssessmentCiloList;
import com.suzumiya.service.AssessmentService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AssessmentController {
    private ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
    private AssessmentService service = (AssessmentService) ac.getBean("assessmentService");

    @RequestMapping(value = "/api/assessments", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getAllAssessments() {
        Map<String, List<Assessment>> map = service.getAssessmentsMap();
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/assessment", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getAssessmentById(@RequestParam(value = "id") int id) {
        Map<String, Assessment> map = service.getAssessmentMap(id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/assessment",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView insertAssessment(@RequestBody AssessmentList assessments){
        List<Integer> id = new ArrayList<>();
        for (Assessment assessment : assessments.getAssessments()) {
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
        service.deleteAssessment(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/acs", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getAllACs() {
        Map<String, List<AssessmentCilo>> map = service.getACsMap();
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/ac", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getACById(@RequestParam(value = "id") int id) {
        Map<String, AssessmentCilo> map = service.getACMap(id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/ac",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView insertAC(@RequestBody AssessmentCiloList assessmentCilos){
        for (AssessmentCilo assessment_cilo : assessmentCilos.getAssessmentCilos()) {
            service.insertAC(assessment_cilo);
        }
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/ac",method = RequestMethod.DELETE)
    @ResponseBody
    @CrossOrigin
    public ModelAndView deleteAC(@RequestParam("id") int id){
        service.deleteAC(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/assessment/update", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public ModelAndView updateAssessment(@RequestBody Assessment assessment){
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", false);
        service.updateAssessment(assessment);
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/ac/update", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public ModelAndView updateAC(@RequestBody AssessmentCilo assessmentCilo){
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", false);
        service.updateAC(assessmentCilo);
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }
}
