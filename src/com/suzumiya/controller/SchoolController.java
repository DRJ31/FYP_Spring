package com.suzumiya.controller;

import com.alibaba.fastjson.JSONObject;
import com.suzumiya.dao.RedisDao;
import com.suzumiya.model.audit.AuditSchool;
import com.suzumiya.model.audit.AuditTeacher;
import com.suzumiya.model.user.School;
import com.suzumiya.model.user.Token;
import com.suzumiya.model.user.User;
import com.suzumiya.service.SchoolService;
import com.suzumiya.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SchoolController {
    private SchoolService service = new SchoolService();
    private UserService userService = new UserService();
    private ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
    private RedisDao redisDao = (RedisDao) ac.getBean("redisDao");

    private User getUser(String token) {
        redisDao.expire("token_" + token, 1800);
        return JSONObject.parseObject(redisDao.get("token_" + token), User.class);
    }

    @RequestMapping(value = "/api/schools", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getAllSchools() {
        Map<String, List<School>> map = service.getSchoolsMap();
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/school", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getSchoolBySid(@RequestParam(value = "id") int id) {
        Map<String, School> map = service.getSchoolMap(id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/favorite/school", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getFavoriteSchools(@RequestParam(value = "user_id") int id) {
        Map<String, List<School>> map = service.getFavoriteSchools(id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/school",method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView insertSchool(@RequestBody School school){
        service.insertSchool(school);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/school",method = RequestMethod.DELETE)
    @ResponseBody
    @CrossOrigin
    public ModelAndView deleteSchool(@RequestParam("id") int id){
        service.deleteSchool(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/audits", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView selectAllAudit() {
        Map<String, List<AuditSchool>> map = service.getAuditSchoolsMap();
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/audit", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView selectAuditById(@RequestParam(value = "id") int id) {
        Map<String, AuditSchool> map = service.getAuditSchoolMap(id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/audit",method = RequestMethod.PUT)
    @ResponseBody
    @CrossOrigin
    public ModelAndView updateRole(@RequestParam("id") int id){
        AuditSchool auditSchool = service.getAuditSchoolMap(id).get("audit");
        int uid = auditSchool.getUser_id();
        userService.updateSchoolRole(uid);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", false);
        try {
            service.insertAuditSchool(auditSchool);
        } catch (Exception e) {
            return new ModelAndView(new MappingJackson2JsonView(), map);
        }
        service.deleteAudit(id);
        int school_id = service.getSchoolByName(auditSchool.getSchool_name()).getId();
        userService.updateSchoolId(new User(uid, school_id));
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/audit/teacher",method = RequestMethod.PUT)
    @ResponseBody
    @CrossOrigin
    public ModelAndView updateTeacherRole(@RequestParam("id") int id){
        AuditTeacher auditTeacher = service.getAuditTeacherMap(id).get("audit");
        int uid = auditTeacher.getUser_id();
        userService.updateTeacherRole(uid);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", false);
        service.deleteAuditTeacher(id);
        int school_id = service.getSchoolByName(auditTeacher.getSchool_name()).getId();
        userService.updateSchoolId(new User(uid, school_id));
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/auditTeachers", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView selectAllAuditTeacher() {
        Map<String, List<AuditTeacher>> map = service.getAuditTeachersMap();
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/auditTeacher", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView selectAuditTeacherById(@RequestParam(value = "id") int id) {
        Map<String, AuditTeacher> map = service.getAuditTeacherMap(id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/school/update", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public ModelAndView updateSchool(@RequestBody School school){
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", false);
        service.updateSchool(school);
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/audit/teachers", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public ModelAndView selectAllAuditSchoolTeacher(@RequestBody Token token) {
        SchoolService service = new SchoolService();
        User user = getUser(token.getToken());
        Map<String, List<AuditTeacher>> map = service.getAuditSchoolTeachersMap(user.getSchool_id());
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/audit",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView insertAudit(@RequestBody AuditSchool auditSchool){
        int uid = getUser(auditSchool.getToken()).getId();
        auditSchool.setUser_id(uid);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", false);
        try {
            service.insertAudit(auditSchool);
        } catch (Exception e) {
            return new ModelAndView(new MappingJackson2JsonView(), map);
        }
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/audit/teacher",method = RequestMethod.DELETE)
    @ResponseBody
    @CrossOrigin
    public ModelAndView deleteAuditTeacher(@RequestParam("id") int id){
        service.deleteAuditTeacher(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }


    @RequestMapping(value = "/api/audit/teacher",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView insertAuditTeacher(@RequestBody AuditTeacher auditTeacher){
        int uid = getUser(auditTeacher.getToken()).getId();
        auditTeacher.setUser_id(uid);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", false);
        try {
            service.insertAuditTeacher(auditTeacher);
        } catch (Exception e) {
            return new ModelAndView(new MappingJackson2JsonView(), map);
        }
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }


    @RequestMapping(value = "/api/audit",method = RequestMethod.DELETE)
    @ResponseBody
    @CrossOrigin
    public ModelAndView deleteAudit(@RequestParam("id") int id){
        service.deleteAudit(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }
}
