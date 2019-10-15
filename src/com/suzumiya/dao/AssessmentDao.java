package com.suzumiya.dao;

import com.suzumiya.model.Assessment;
import com.suzumiya.model.Assessment_Cilo;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

public class AssessmentDao extends SqlSessionDaoSupport implements Dao<Assessment> {
    @Override
    public List<Assessment> selectAll() {
        return getSqlSession().selectList("com.suzumiya.mapper.AssessmentMapper.selectAll");
    }

    @Override
    public Assessment selectById(@Param("id") int id){
        return getSqlSession().selectOne("com.suzumiya.mapper.AssessmentMapper.selectByAssessmentId", id);
    }

    public void insertAssessment(Assessment assessment){
        getSqlSession().insert("com.suzumiya.mapper.AssessmentMapper.insertAssessment", assessment);
    }

    public void deleteAssessment(@Param("id") int id){
        getSqlSession().delete("com.suzumiya.mapper.AssessmentMapper.deleteAssessment", id);
    }

    public List<Assessment_Cilo> selectAC() {
        return getSqlSession().selectList("com.suzumiya.mapper.AssessmentMapper.selectAC");
    }

    public Assessment_Cilo selectACById(@Param("id") int id){
        return getSqlSession().selectOne("com.suzumiya.mapper.AssessmentMapper.selectACById", id);
    }

    public void insertAC(Assessment_Cilo assessment_cilo){
        getSqlSession().insert("com.suzumiya.mapper.AssessmentMapper.insertAC", assessment_cilo);
    }

    public void deleteAC(@Param("id") int id){
        getSqlSession().delete("com.suzumiya.mapper.AssessmentMapper.deleteAC", id);
    }
}
