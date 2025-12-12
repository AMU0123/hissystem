package com.his.service;

import com.his.entity.Patient;
import com.his.mapper.PatientMapper;
import com.his.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientMapper patientMapper;

    @InjectMocks
    private PatientServiceImpl patientService;

    private Patient patient;

    @BeforeEach//测试用例
    public void setUp() {
        patient = new Patient();
        patient.setPatientId(1);
        patient.setName("张三");
        patient.setGender("男");
        patient.setAge(30);
        patient.setPhone("13800138000");
        patient.setAddress("北京市朝阳区");
        patient.setMedicalHistory("无");
        patient.setAllergyHistory("无");
    }

    @Test
    public void testAddPatient() {
        when(patientMapper.insert(any(Patient.class))).thenReturn(1);

        int result = patientService.addPatient(patient);

        assertEquals(1, result);
        verify(patientMapper, times(1)).insert(patient);
    }

    @Test
    public void testDeletePatientById() {
        when(patientMapper.deleteById(1)).thenReturn(1);

        int result = patientService.deletePatientById(1);

        assertEquals(1, result);
        verify(patientMapper, times(1)).deleteById(1);
    }

    @Test
    public void testUpdatePatient() {
        when(patientMapper.update(any(Patient.class))).thenReturn(1);

        int result = patientService.updatePatient(patient);

        assertEquals(1, result);
        verify(patientMapper, times(1)).update(patient);
    }

    @Test
    public void testGetPatientById() {
        when(patientMapper.selectById(1)).thenReturn(patient);

        Patient result = patientService.getPatientById(1);

        assertNotNull(result);
        assertEquals("张三", result.getName());
        assertEquals(30, result.getAge());
        verify(patientMapper, times(1)).selectById(1);
    }

    @Test
    public void testGetPatientById_NotFound() {
        when(patientMapper.selectById(999)).thenReturn(null);

        Patient result = patientService.getPatientById(999);

        assertNull(result);
        verify(patientMapper, times(1)).selectById(999);
    }

    @Test
    public void testGetAllPatients() {
        Patient patient2 = new Patient();
        patient2.setPatientId(2);
        patient2.setName("李四");
        patient2.setGender("女");
        patient2.setAge(25);

        List<Patient> patients = Arrays.asList(patient, patient2);
        when(patientMapper.selectAll()).thenReturn(patients);

        List<Patient> result = patientService.getAllPatients();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("张三", result.get(0).getName());
        assertEquals("李四", result.get(1).getName());
        verify(patientMapper, times(1)).selectAll();
    }

    @Test
    public void testSearchPatients() {
        List<Patient> patients = Arrays.asList(patient);
        when(patientMapper.selectByKeyword("张")).thenReturn(patients);

        List<Patient> result = patientService.searchPatients("张");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("张三", result.get(0).getName());
        verify(patientMapper, times(1)).selectByKeyword("张");
    }

    @Test
    public void testSearchPatients_NoMatch() {
        when(patientMapper.selectByKeyword("未知关键词")).thenReturn(Arrays.asList());

        List<Patient> result = patientService.searchPatients("未知关键词");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(patientMapper, times(1)).selectByKeyword("未知关键词");
    }
}
