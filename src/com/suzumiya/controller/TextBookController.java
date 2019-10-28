package com.suzumiya.controller;

import com.suzumiya.model.TextBook;
import com.suzumiya.model.list.TextBookList;
import com.suzumiya.service.TextBookService;
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
public class TextBookController {
    private ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
    private TextBookService service = (TextBookService) ac.getBean("textBookService");

    @RequestMapping(value = "/api/textBooks", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getAllTextBooks() {
        Map<String, List<TextBook>> map = service.getTextBooksMap();
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/textBook", method = {RequestMethod.GET})
    @ResponseBody
    @CrossOrigin
    public ModelAndView getTextBookByTid(@RequestParam(value = "id") int id) {
        Map<String, TextBook> map = service.getTextBookMap(id);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/textBook",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView insertTextBook(@RequestBody TextBookList textBooks){
        for (TextBook textBook : textBooks.getTextBooks()) {
            service.insertTextBook(textBook);
        }
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/textBook",method = RequestMethod.DELETE)
    @ResponseBody
    @CrossOrigin
    public ModelAndView deleteTextBook(@RequestParam("id") int id){
        service.deleteTextBook(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }

    @RequestMapping(value = "/api/textBook/update",method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public ModelAndView deleteTextBook(@RequestBody TextBook textBook){
        service.updateTextBook(textBook);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", true);
        return new ModelAndView(new MappingJackson2JsonView(), map);
    }
}
