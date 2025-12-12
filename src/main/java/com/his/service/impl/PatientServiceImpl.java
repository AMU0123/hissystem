package com.his.service.impl;

import com.his.entity.Patient;
import com.his.mapper.PatientMapper;
import com.his.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 患者服务实现类
 */
@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientMapper patientMapper;

    @Override
    public int addPatient(Patient patient) {
        return patientMapper.insert(patient);
    }

    @Override
    public int deletePatientById(Integer patientId) {
        return patientMapper.deleteById(patientId);
    }

    @Override
    public int updatePatient(Patient patient) {
        return patientMapper.update(patient);
    }

    @Override
    public Patient getPatientById(Integer patientId) {
        return patientMapper.selectById(patientId);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientMapper.selectAll();
    }

    @Override
    public List<Patient> searchPatients(String keyword) {
        return patientMapper.selectByKeyword(keyword);
    }
}