package com.suzumiya.controller;

import com.suzumiya.model.*;
import com.suzumiya.service.SchoolService;
import com.suzumiya.service.SyllabusService;
import com.suzumiya.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    private Map<String, User> loginMap = new HashMap<>();

    @RequestMapping(value = "/api/users", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getAllUsers() {
        UserService service = new UserService();
        Map<String, List<User>> map = service.getUsersMap();
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/user", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getUserByUid(@RequestParam(value = "id") int id) {
        UserService service = new UserService();
        Map<String, User> map = service.getUserMap(id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/register",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView insertUser(@RequestBody User user){
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", false);
        UserService service = new UserService();
        if (service.checkUserDuplicate(user) != null)
            return new ModelAndView(new MappingJackson2JsonView(), map);
        user.encryptPassword();
        service.insertUser(user);
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/user",method = RequestMethod.DELETE)
    @ResponseBody
    @CrossOrigin
    public ModelAndView deleteUser(@RequestParam("id") int id){
        UserService service = new UserService();
        service.deleteUser(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/user", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public ModelAndView userLogin(@RequestBody User user){
        String token = System.currentTimeMillis() + user.getName();
        EncryptController e = new EncryptController();
        Map<String, String> currentMap = new HashMap<>();
        user.encryptPassword();
        token = e.encrypt(token);
        UserService service = new UserService();
        User u = service.getUserByName(user);
        try {
            for (Map.Entry<String, User> entry : loginMap.entrySet()) {
                if (entry.getValue().getName().equals(user.getName())) {
                    currentMap.put("token", entry.getKey());
                    return new ModelAndView(new MappingJackson2JsonView(), currentMap);
                }
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        if (u != null) {
            loginMap.put(token, u);
            currentMap.put("token", token);
        }
        else {
            currentMap.put("token", null);
        }

        return new ModelAndView(new MappingJackson2JsonView(), currentMap);
    }

    @RequestMapping(value = "/api/token", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public ModelAndView userLoginStatus(@RequestBody Token token){
        User user = loginMap.get(token.getToken());
        Map<String, User> map = new HashMap<>();
        map.put("user", user);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/logout", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public String userLogout(@RequestBody Token token){
        String name = null;
        try {
            name = loginMap.get(token.getToken()).getName();
            loginMap.remove(token.getToken());
        }catch (Exception e){
            e.printStackTrace();
        }
        return name;
    }

    @RequestMapping(value = "/api/favorite",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView insertFavorite(@RequestBody Favorite favorite){
        SyllabusService syllabusService = new SyllabusService();
        SchoolService schoolService = new SchoolService();
        int uid = loginMap.get(favorite.getToken()).getId();
        favorite.setUser_id(uid);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", false);
        if (favorite.getSyllabus_id() != 0){
            if (syllabusService.checkFavoriteDuplicate(favorite) != null)
                return new ModelAndView(new MappingJackson2JsonView(), map);
            syllabusService.insertFavoriteSyllabus(favorite);
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
        SyllabusService syllabusService = new SyllabusService();
        SchoolService schoolService = new SchoolService();
        int uid = loginMap.get(favorite.getToken()).getId();
        favorite.setUser_id(uid);
        if (favorite.getSyllabus_id() != 0){
            syllabusService.deleteFavoriteSyllabus(favorite);
        }
        if (favorite.getSchool_id() != 0){
            schoolService.deleteFavoriteSchool(favorite);
        }
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

//    @RequestMapping(value = "/api/check", method = {RequestMethod.POST})
//    @ResponseBody
//    @CrossOrigin
//    public ModelAndView checkUserDuplicate(@RequestBody User user) {
//        UserService service = new UserService();
//        User check = service.checkUserDuplicate(user);
//        Map<String, User> map = new HashMap<>();
//        map.put("user", check);
//        return new ModelAndView(new MappingJackson2JsonView(), map);
//    }

//    @RequestMapping(value = "/api/favorite/check",method = RequestMethod.POST)
//    @ResponseBody
//    @CrossOrigin
//    public ModelAndView checkFavoriteDuplicate(@RequestBody Favorite favorite){
//        SyllabusService syllabusService = new SyllabusService();
//        SchoolService schoolService = new SchoolService();
//        int uid = loginMap.get(favorite.getToken()).getId();
//        favorite.setUser_id(uid);
//        Map<String, Favorite> map = new HashMap<>();
//        if (favorite.getSyllabus_id() != 0){
//            map.put("favorite", syllabusService.checkFavoriteDuplicate(favorite));
//        }
//        if (favorite.getSchool_id() != 0){
//            map.put("favorite", schoolService.checkFavoriteDuplicate(favorite));
//        }
//        return new ModelAndView(new MappingJackson2JsonView(), map);
//    }

    @RequestMapping(value = "/api/audit",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView insertAudit(@RequestBody AuditSchool auditSchool){
        SchoolService schoolService = new SchoolService();
        int uid = loginMap.get(auditSchool.getToken()).getId();
        auditSchool.setUser_id(uid);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", false);
        schoolService.insertAudit(auditSchool);
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/audit",method = RequestMethod.DELETE)
    @ResponseBody
    @CrossOrigin
    public ModelAndView deleteAudit(@RequestParam("id") int id){
        SchoolService service = new SchoolService();
        service.deleteAudit(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/audit",method = RequestMethod.PUT)
    @ResponseBody
    @CrossOrigin
    public ModelAndView updateRole(@RequestParam("id") int id){
        SchoolService schoolService = new SchoolService();
        UserService userService = new UserService();
        AuditSchool auditSchool = schoolService.getAuditSchoolMap(id).get("audit school");
        int uid = auditSchool.getUser_id();
        schoolService.insertAuditSchool(auditSchool);
        userService.updateSchoolRole(uid);
        Map<String, Boolean> map = new HashMap<>();
        schoolService.deleteAudit(id);
        int school_id = schoolService.getSchoolByName(auditSchool.getSchool_name()).getId();
        userService.updateSchoolId(new User(uid, school_id));
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/password", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public ModelAndView updatePassword(@RequestBody Token token){
        UserService userService = new UserService();
        Map<String, Boolean> map = new HashMap<>();
        User user = loginMap.get(token.getToken());
        token.encrypt();
        System.out.println(user.getPassword());
        System.out.println(token.getOld_pass());
        if (user.getPassword().equals(token.getOld_pass())){
            user.setPassword(token.getNew_pass());
            loginMap.put(token.getToken(), user);
            userService.updatePassword(user);
            map.put("status", true);
            return new ModelAndView(new MappingJackson2JsonView(), map);
        }
        else {
            map.put("status", false);
            return new ModelAndView(new MappingJackson2JsonView(), map);
        }
    }
}
