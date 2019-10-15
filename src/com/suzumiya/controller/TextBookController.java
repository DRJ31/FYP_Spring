package com.suzumiya.controller;

import com.suzumiya.model.TextBook;
import com.suzumiya.service.TextBookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TextBookController {
    @RequestMapping(value = "/api/textBooks", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getAllTextBooks() {
        TextBookService service = new TextBookService();
        Map<String, List<TextBook>> map = service.getTextBooksMap();
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/textBook", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getTextBookByTid(@RequestParam(value = "id") int id) {
        TextBookService service = new TextBookService();
        Map<String, TextBook> map = service.getTextBookMap(id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/insertTextBook",method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView insertTextBook(@RequestBody TextBook textBook){
        TextBookService service = new TextBookService();
        service.insertTextBook(textBook);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/deleteTextBook",method = RequestMethod.DELETE)
    @ResponseBody
    @CrossOrigin
    public ModelAndView deleteTextBook(@RequestParam("id") int id){
        TextBookService service = new TextBookService();
        service.deleteTextBook(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }
}
