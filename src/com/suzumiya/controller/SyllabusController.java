package com.suzumiya.controller;

import com.alibaba.fastjson.JSONObject;
import com.suzumiya.dao.RedisDao;
import com.suzumiya.model.*;
import com.suzumiya.model.list.CiloList;
import com.suzumiya.model.list.ContentList;
import com.suzumiya.model.list.PiloList;
import com.suzumiya.model.list.TlaList;
import com.suzumiya.model.relationship.PiloCilo;
import com.suzumiya.model.relationship.list.PiloCiloList;
import com.suzumiya.model.user.School;
import com.suzumiya.model.user.Token;
import com.suzumiya.model.user.User;
import com.suzumiya.service.SchoolService;
import com.suzumiya.service.SyllabusService;
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
public class SyllabusController {
    private SyllabusService service = new SyllabusService();
    private SchoolService schoolService = new SchoolService();
    private ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
    private RedisDao redisDao = (RedisDao) ac.getBean("redisDao");

    private User getUser(String token) {
        redisDao.expire("token_" + token, 1800);
        return JSONObject.parseObject(redisDao.get("token_" + token), User.class);
    }

    @RequestMapping(value = "/api/syllabuses", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getAllSyllabuses() {
        Map<String, List<Syllabus>> map = service.getSyllabusesMap();
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/syllabus", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getSyllabusBySid(@RequestParam(value = "id") int id) {
        Map<String, Syllabus> map = service.getSyllabusMap(id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/favorite/syllabus", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getFavoriteSyllabuses(@RequestParam(value = "user_id") int id) {
        Map<String, List<Syllabus>> map = service.getFavoriteSyllabuses(id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }


    @RequestMapping(value = "/api/syllabuses", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getAllSyllabuses(@RequestBody Token token) {
        SyllabusService service = new SyllabusService();
        User user = getUser(token.getToken());
        Map<String, List<Syllabus>> map = new HashMap<>();
        int role = user.getRole().getId();
        switch (role){
            case 1:
                map = service.getSyllabusesMap();
                break;
            case 2:
                map = service.selectSyllabuses_S(user.getSchool_id());
                break;
            case 3:
                map = service.selectSyllabuses_T(user.getId());
                break;
            default:
                map.put("syllabuses", null);
                return null;
        }
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/syllabus",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView insertSyllabus(@RequestBody Syllabus syllabus){
        service.insertSyllabus(syllabus);
        Map<String, Syllabus> map = new HashMap<>();
        map.put("syllabus", service.getInsertedSyllabus(syllabus));
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/syllabus",method = RequestMethod.DELETE)
    @ResponseBody
    @CrossOrigin
    public ModelAndView deleteSyllabus(@RequestParam("id") int id){
        service.deleteSyllabus(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }


    @RequestMapping(value = "/api/favorite",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView insertFavorite(@RequestBody Favorite favorite){
        int uid = getUser(favorite.getToken()).getId();
        favorite.setUser_id(uid);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", false);
        if (favorite.getSyllabus_id() != 0){
            if (service.checkFavoriteDuplicate(favorite) != null)
                return new ModelAndView(new MappingJackson2JsonView(), map);
            service.insertFavoriteSyllabus(favorite);
        }
        if (favorite.getSchool_id() != 0){
            if (schoolService.checkFavoriteDuplicate(favorite) != null)
                return new ModelAndView(new MappingJackson2JsonView(), map);
            schoolService.insertFavoriteSchool(favorite);
        }
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/favorite",method = RequestMethod.DELETE)
    @ResponseBody
    @CrossOrigin
    public ModelAndView deleteFavorite(@RequestBody Favorite favorite){
        int uid = getUser(favorite.getToken()).getId();
        favorite.setUser_id(uid);
        if (favorite.getSyllabus_id() != 0){
            service.deleteFavoriteSyllabus(favorite);
        }
        if (favorite.getSchool_id() != 0){
            schoolService.deleteFavoriteSchool(favorite);
        }
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/cilos", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView selectAllCilo() {
        Map<String, List<Cilo>> map = service.selectAllCilo();
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/cilo", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView selectCiloById(@RequestParam(value = "id") int id) {
        Map<String, Cilo> map = service.selectCiloById(id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/cilo",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView insertCilo(@RequestBody CiloList cilos){
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
        service.deleteCilo(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/pilo",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView insertPilo(@RequestBody PiloList pilos){
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
        service.deletePilo(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/pc",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView insertPC(@RequestBody PiloCiloList piloCilos){
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
        service.deletePC(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/tla",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView insertTla(@RequestBody TlaList tlas){
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
        service.deleteTla(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/content",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView insertContent(@RequestBody ContentList contents){
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
        service.deleteContent(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/syllabus/update", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public ModelAndView updateSyllabus(@RequestBody Syllabus syllabus){
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", false);
        service.updateSyllabus(syllabus);
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/content/update", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public ModelAndView updateContent(@RequestBody Content content){
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", false);
        service.updateContent(content);
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/cilo/update", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public ModelAndView updateCilo(@RequestBody Cilo cilo){
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", false);
        service.updateCilo(cilo);
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/pilo/update", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public ModelAndView updatePilo(@RequestBody Pilo pilo){
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", false);
        service.updatePilo(pilo);
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/pc/update", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public ModelAndView updatePC(@RequestBody PiloCilo piloCilo){
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", false);
        service.updatePC(piloCilo);
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/tla/update", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public ModelAndView updateTla(@RequestBody Tla tla){
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", false);
        service.updateTla(tla);
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }
}
