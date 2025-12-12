package com.his.service;

import com.his.entity.Patient;

import java.util.List;

/**
 * 患者服务接口
 */
public interface PatientService {

    /**
     * 新增患者
     * @param patient 患者信息
     * @return 影响行数
     */
    int addPatient(Patient patient);

    /**
     * 根据ID删除患者
     * @param patientId 患者ID
     * @return 影响行数
     */
    int deletePatientById(Integer patientId);

    /**
     * 更新患者信息
     * @param patient 患者信息
     * @return 影响行数
     */
    int updatePatient(Patient patient);

    /**
     * 根据ID查询患者
     * @param patientId 患者ID
     * @return 患者信息
     */
    Patient getPatientById(Integer patientId);

    /**
     * 查询所有患者
     * @return 患者列表
     */
    List<Patient> getAllPatients();

    /**
     * 根据关键词搜索患者
     * @param keyword 关键词（姓名或电话）
     * @return 患者列表
     */
    List<Patient> searchPatients(String keyword);
}