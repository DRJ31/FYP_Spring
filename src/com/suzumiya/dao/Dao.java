package com.suzumiya.dao;

import java.util.List;

public interface Dao<T> {
    List<T> selectAll();
}
