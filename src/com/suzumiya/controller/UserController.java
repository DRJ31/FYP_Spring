package com.suzumiya.controller;

import com.alibaba.fastjson.JSONObject;
import com.suzumiya.dao.RedisDao;
import com.suzumiya.model.Favorite;
import com.suzumiya.model.user.Avatar;
import com.suzumiya.model.user.Token;
import com.suzumiya.model.user.User;
import com.suzumiya.service.SchoolService;
import com.suzumiya.service.SyllabusService;
import com.suzumiya.service.UserService;
import org.apache.commons.io.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
import java.util.Set;

@Controller
public class UserController {
    private ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
    private RedisDao redisDao = (RedisDao) ac.getBean("redisDao");
    private String rootPath = "/var/www/syllabus/static/";
    String filePath = rootPath + "application/";
    String avatarPath = rootPath +"avatar/";
    private UserService service = new UserService();

    private User getUser(String token) {
        redisDao.expire("token_" + token, 1800);
        return JSONObject.parseObject(redisDao.get("token_" + token), User.class);
    }

    @RequestMapping(value = "/api/users", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getAllUsers(@RequestBody Token token) {
        User user = getUser(token.getToken());
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
        Map<String, User> map = service.getUserMap(id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/register",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView insertUser(@RequestBody User user){
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", false);
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
        Set<String> tokens = redisDao.keys("token_*");
        User tmp;
        for (String tok : tokens) {
            tmp = JSONObject.parseObject(redisDao.get(tok), User.class);
            if (tmp.getName().equals(user.getName()))
                redisDao.del(tok);
        }
        User u = service.getUserByName(user);
        if (u != null) {
            redisDao.set("token_" + token, JSONObject.toJSONString(user));
            redisDao.expire("token_" + token, 1800);
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
        User user = getUser(token.getToken());
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
            name = getUser(token.getToken()).getName();
            redisDao.del("token_" + token.getToken());
        }catch (Exception e){
            e.printStackTrace();
        }
        return name;
    }

    @RequestMapping(value = "/api/password", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public ModelAndView updatePassword(@RequestBody Token token){
        Map<String, Boolean> map = new HashMap<>();
        User user = getUser(token.getToken());
        token.encrypt();
        if (user.getPassword().equals(token.getOld_pass())){
            user.setPassword(token.getNew_pass());
            redisDao.set("token_" + token.getToken(), JSONObject.toJSONString(user));
            redisDao.expire("token_" + token.getToken(), 1800);
            service.updatePassword(user);
            map.put("status", true);
            return new ModelAndView(new MappingJackson2JsonView(), map);
        }
        else {
            map.put("status", false);
            return new ModelAndView(new MappingJackson2JsonView(), map);
        }
    }

    @RequestMapping(value = "/api/user/update", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public ModelAndView updateUser(@RequestBody Token token){
        Map<String, Boolean> map = new HashMap<>();
        User user = getUser(token.getToken());
        map.put("status", false);
        user.setEmail(token.getEmail());
        user.setRole_id(token.getRole_id());
        try {
            service.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        redisDao.set("token_" + token.getToken(), JSONObject.toJSONString(user));
        redisDao.expire("token_" + token.getToken(), 1800);
        map.put("status", true);
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

    @RequestMapping(value = "/api/user/avatar", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public String getAvatar(@RequestBody Token token){
        User user = getUser(token.getToken());
        Avatar avatar = service.selectAvatar(user.getId());
        return avatar.getFilename();
    }

    @RequestMapping(value = "/api/user/avatar/upload", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public ModelAndView uploadAvatar(@RequestParam("file") MultipartFile partFile, @RequestParam("token") String token)
            throws IllegalStateException, IOException {
        if(partFile != null && partFile.getOriginalFilename() != null && partFile.getOriginalFilename().length()>0){
            File dir=new File(avatarPath);
            if(!dir.isDirectory()) {
                dir.mkdir();
            }
            User user = getUser(token);
            String originalFileName = partFile.getOriginalFilename();
            String suffix = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
            String newFileName = user.getName() + user.getEmail() + "." + suffix;
            File file = new File(avatarPath + newFileName);
            partFile.transferTo(file);
            Avatar avatar = new Avatar(user.getId(), newFileName);
            try {
                service.insertAvatar(avatar);
            } catch (Exception e){
                e.printStackTrace();
                service.updateAvatar(avatar);
            }
            Map<String, String> response = new HashMap<>();
            response.put("status", "done");
            response.put("name", newFileName);
            response.put("url", "https://syllabus.drjchn.com/static/avatar/" + newFileName);
            return new ModelAndView(new MappingJackson2JsonView(), response);
        }
        else
            return null;
    }

    @RequestMapping(value = "/api/user/update/teacher", method = {RequestMethod.POST})
    @ResponseBody
    @CrossOrigin
    public ModelAndView updateTeacher(@RequestBody User user){
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", false);
        try {
            service.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }
}
