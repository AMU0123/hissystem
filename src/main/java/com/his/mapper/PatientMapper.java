package com.his.mapper;

import com.his.entity.Patient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 患者Mapper接口
 */
@Mapper
public interface PatientMapper {

    /**
     * 新增患者
     * @param patient 患者信息
     * @return 影响行数
     */
    int insert(Patient patient);

    /**
     * 根据ID删除患者
     * @param patientId 患者ID
     * @return 影响行数
     */
    int deleteById(@Param("patientId") Integer patientId);

    /**
     * 更新患者信息
     * @param patient 患者信息
     * @return 影响行数
     */
    int update(Patient patient);

    /**
     * 根据ID查询患者
     * @param patientId 患者ID
     * @return 患者信息
     */
    Patient selectById(@Param("patientId") Integer patientId);

    /**
     * 查询所有患者
     * @return 患者列表
     */
    List<Patient> selectAll();

    /**
     * 根据姓名或电话模糊查询患者
     * @param keyword 关键词
     * @return 患者列表
     */
    List<Patient> selectByKeyword(@Param("keyword") String keyword);
}