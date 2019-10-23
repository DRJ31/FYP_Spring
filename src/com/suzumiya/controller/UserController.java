package com.suzumiya.controller;

import com.suzumiya.model.audit.AuditSchool;
import com.suzumiya.model.audit.AuditTeacher;
import com.suzumiya.model.Favorite;
import com.suzumiya.model.Syllabus;
import com.suzumiya.model.user.Token;
import com.suzumiya.model.user.User;
import com.suzumiya.service.SchoolService;
import com.suzumiya.service.SyllabusService;
import com.suzumiya.service.UserService;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    private Map<String, User> loginMap = new HashMap<>();
    private String rootPath = "/var/www/syllabus/static/";
    String filePath = rootPath + "application/";

    public Map<String, User> getLoginMap() {
        return loginMap;
    }

    public void setLoginMap(Map<String, User> loginMap) {
        this.loginMap = loginMap;
    }

    @RequestMapping(value = "/api/syllabuses", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getAllSyllabuses(@RequestBody Token token) {
        SyllabusService service = new SyllabusService();
        User user = loginMap.get(token.getToken());
        Map<String, List<Syllabus>> map;
        if (user.getRole().getId() == 1){
            map = service.getSyllabusesMap();
        }
        else {
            map = service.selectSyllabuses_S(user.getSchool_id());
        }
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }


    @RequestMapping(value = "/api/users", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getAllUsers(@RequestBody Token token) {
        UserService service = new UserService();
        User user = loginMap.get(token.getToken());
        Map<String, List<User>> map;
        if (user.getRole().getId() == 1) {
            map = service.getUsersMap();
        }
        else {
            map = service.selectUser_S(user.getSchool_id());
        }
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
        AuditSchool auditSchool = schoolService.getAuditSchoolMap(id).get("audit");
        int uid = auditSchool.getUser_id();
        userService.updateSchoolRole(uid);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", false);
        try {
            schoolService.insertAuditSchool(auditSchool);
        } catch (Exception e) {
            return new ModelAndView(new MappingJackson2JsonView(), map);
        }
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

    @RequestMapping(value = "/api/email", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public ModelAndView updateEmail(@RequestBody Token token){
        UserService userService = new UserService();
        Map<String, Boolean> map = new HashMap<>();
        User user = loginMap.get(token.getToken());
        map.put("status", false);
        user.setEmail(token.getEmail());
        try {
            userService.updateEmail(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loginMap.put(token.getToken(), user);
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/audit/teacher",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView insertAuditTeacher(@RequestBody AuditTeacher auditTeacher){
        SchoolService schoolService = new SchoolService();
        int uid = loginMap.get(auditTeacher.getToken()).getId();
        auditTeacher.setUser_id(uid);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", false);
        try {
            schoolService.insertAuditTeacher(auditTeacher);
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
        SchoolService service = new SchoolService();
        service.deleteAuditTeacher(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/audit/teacher",method = RequestMethod.PUT)
    @ResponseBody
    @CrossOrigin
    public ModelAndView updateTeacherRole(@RequestParam("id") int id){
        SchoolService schoolService = new SchoolService();
        UserService userService = new UserService();
        AuditTeacher auditTeacher = schoolService.getAuditTeacherMap(id).get("audit");
        int uid = auditTeacher.getUser_id();
        userService.updateTeacherRole(uid);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", false);
        schoolService.deleteAuditTeacher(id);
        int school_id = schoolService.getSchoolByName(auditTeacher.getSchool_name()).getId();
        userService.updateSchoolId(new User(uid, school_id));
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/audit/teachers", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public ModelAndView selectAllAuditSchoolTeacher(@RequestBody Token token) {
        SchoolService service = new SchoolService();
        User user = loginMap.get(token.getToken());
        Map<String, List<AuditTeacher>> map = service.getAuditSchoolTeachersMap(user.getSchool_id());
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/school/upload", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile partFile, @RequestParam("user") String user, @RequestParam("category") String category) throws IllegalStateException, IOException {
        if(partFile != null && partFile.getOriginalFilename() != null && partFile.getOriginalFilename().length()>0){
            File dir=new File(filePath);
            if(!dir.isDirectory()) {
                dir.mkdir();
            }
            String fileName = partFile.getOriginalFilename();
            File file = new File(filePath + user + category + fileName);
            partFile.transferTo(file);
            Map<String, String> response = new HashMap<>();
            response.put("status", "done");
            response.put("name", user + category + fileName);
            response.put("url", "https://syllabus.drjchn.com/static/application/" + user + category + fileName);
            return new ModelAndView(new MappingJackson2JsonView(), response);
        }
        else
            return null;
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadFile(@Param("filename") String filename) throws Exception {
        File file = new File(filePath + filename);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", filename);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity (FileUtils.readFileToByteArray(file),
                headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/audit",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView insertAudit(@RequestBody AuditSchool auditSchool){
        int uid = loginMap.get(auditSchool.getToken()).getId();
        auditSchool.setUser_id(uid);
        Map<String, Boolean> map = new HashMap<>();
        SchoolService schoolService = new SchoolService();
        map.put("status", false);
        try {
            schoolService.insertAudit(auditSchool);
        } catch (Exception e) {
            return new ModelAndView(new MappingJackson2JsonView(), map);
        }
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }
}
