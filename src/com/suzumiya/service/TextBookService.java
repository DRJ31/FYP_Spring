package com.suzumiya.service;

import com.suzumiya.dao.TextBookDao;
import com.suzumiya.model.TextBook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextBookService {
    private List<TextBook> textBooks;
    private TextBookDao textBookDao;
    private TextBook textBook;

    public TextBookService() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
        this.textBookDao = (TextBookDao) ac.getBean("textBookDao");
        this.setTextBooks();
    }

    public List<TextBook> getTextBooks() {
        return textBooks;
    }

    public void setTextBooks() {
        this.textBooks = textBookDao.selectAll();
    }

    public TextBookDao getTextBookDao() {
        return textBookDao;
    }

    public void setTextBookDao(TextBookDao textBookDao) {
        this.textBookDao = textBookDao;
    }

    public TextBook getTextBook() {
        return textBook;
    }

    public void setTextBook(int tid) {
        this.textBook = textBookDao.selectById(tid);
    }

    public Map<String, List<TextBook>> getTextBooksMap() {
        Map<String, List<TextBook>> result = new HashMap<>();
        result.put("textBooks", textBooks);
        return result;
    }

    public Map<String, TextBook> getTextBookMap(int tid) {
        Map<String, TextBook> result = new HashMap<>();
        setTextBook(tid);
        result.put("textBook", textBook);
        return result;
    }

    public void insertTextBook(TextBook textBook){
        textBookDao.insertTextBook(textBook);
    }

    public void deleteTextBook(int id){
        textBookDao.deleteTextBook(id);
    }

    public void updateTextBook(TextBook textBook){
        textBookDao.updateTextBook(textBook);
    }

}
