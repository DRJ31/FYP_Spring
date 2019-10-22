package com.suzumiya.dao;

import com.suzumiya.model.TextBook;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

public class TextBookDao extends SqlSessionDaoSupport implements Dao<TextBook> {
    @Override
    public List<TextBook> selectAll() {
        return getSqlSession().selectList("com.suzumiya.mapper.TextBookMapper.selectAll");
    }

    @Override
    public TextBook selectById(@Param("id") int id){
        return getSqlSession().selectOne("com.suzumiya.mapper.TextBookMapper.selectByTextBookId", id);
    }

    public void insertTextBook(TextBook textBook){
        getSqlSession().insert("com.suzumiya.mapper.TextBookMapper.insertTextBook", textBook);
    }

    public void deleteTextBook(@Param("id") int id){
        getSqlSession().delete("com.suzumiya.mapper.TextBookMapper.deleteTextBook", id);
    }

    public void updateTextBook(TextBook textBook){
        getSqlSession().update("com.suzumiya.mapper.TextBookMapper.updateTextBook", textBook);
    }
}
