package com.his.mapper;

import com.his.entity.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PatientMapperTest {

    @Autowired
    private PatientMapper patientMapper;

    @Test
    public void testInsert() {
        Patient patient = new Patient();
        patient.setName("测试患者");
        patient.setGender("男");
        patient.setAge(25);
        patient.setPhone("13800138001");
        patient.setAddress("测试地址");
        patient.setMedicalHistory("无");
        patient.setAllergyHistory("无");

        int result = patientMapper.insert(patient);
        assertTrue(result > 0);
        assertNotNull(patient.getPatientId());

        // 清理测试数据
        patientMapper.deleteById(patient.getPatientId());
    }

    @Test
    public void testSelectById() {
        // 先插入一条测试数据
        Patient patient = new Patient();
        patient.setName("测试患者");
        patient.setGender("女");
        patient.setAge(30);
        patient.setPhone("13800138002");
        patient.setAddress("测试地址");
        patient.setMedicalHistory("无");
        patient.setAllergyHistory("无");
        patientMapper.insert(patient);

        // 测试查询
        Patient result = patientMapper.selectById(patient.getPatientId());
        assertNotNull(result);
        assertEquals("测试患者", result.getName());
        assertEquals(30, result.getAge());

        // 清理测试数据
        patientMapper.deleteById(patient.getPatientId());
    }

    @Test
    public void testSelectAll() {
        List<Patient> patients = patientMapper.selectAll();
        assertNotNull(patients);
        // 至少应该有DataInitializer插入的测试数据
        assertTrue(patients.size() >= 5);
    }

    @Test
    public void testSelectByKeyword() {
        List<Patient> patients = patientMapper.selectByKeyword("张");
        assertNotNull(patients);
        // 检查是否有名字包含"张"的患者
        boolean hasZhang = patients.stream().anyMatch(p -> p.getName().contains("张"));
        assertTrue(hasZhang, "应该找到名字包含'张'的患者");
    }

    @Test
    public void testUpdate() {
        // 先插入一条测试数据
        Patient patient = new Patient();
        patient.setName("测试患者");
        patient.setGender("男");
        patient.setAge(25);
        patient.setPhone("13800138003");
        patient.setAddress("测试地址");
        patient.setMedicalHistory("无");
        patient.setAllergyHistory("无");
        patientMapper.insert(patient);

        // 更新数据
        patient.setName("更新后的患者");
        patient.setAge(26);
        int updateResult = patientMapper.update(patient);
        assertEquals(1, updateResult);

        // 验证更新结果
        Patient updatedPatient = patientMapper.selectById(patient.getPatientId());
        assertNotNull(updatedPatient);
        assertEquals("更新后的患者", updatedPatient.getName());
        assertEquals(26, updatedPatient.getAge());

        // 清理测试数据
        patientMapper.deleteById(patient.getPatientId());
    }

    @Test
    public void testDeleteById() {
        // 先插入一条测试数据
        Patient patient = new Patient();
        patient.setName("测试患者");
        patient.setGender("男");
        patient.setAge(25);
        patient.setPhone("13800138004");
        patient.setAddress("测试地址");
        patient.setMedicalHistory("无");
        patient.setAllergyHistory("无");
        patientMapper.insert(patient);

        // 删除数据
        int deleteResult = patientMapper.deleteById(patient.getPatientId());
        assertEquals(1, deleteResult);

        // 验证删除结果
        Patient deletedPatient = patientMapper.selectById(patient.getPatientId());
        assertNull(deletedPatient);
    }
}
