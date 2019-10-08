package com.suzumiya.dao;

import java.util.List;

public interface Dao<T> {
    List<T> selectAll();
//
    T selectById(int id);

//    void updateSQL(String SQL);
//
//    int deleteById(int id);
}
