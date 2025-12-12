package com.his.controller;

import com.his.entity.Patient;
import com.his.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
    }

    @Test
    public void testAddPatient() throws Exception {
        Patient patient = new Patient();
        patient.setPatientId(1);
        patient.setName("张三");
        patient.setGender("男");
        patient.setAge(30);
        patient.setPhone("13800138000");
        patient.setAddress("北京市朝阳区");
        patient.setMedicalHistory("无");
        patient.setAllergyHistory("无");

        when(patientService.addPatient(any(Patient.class))).thenReturn(1);

        mockMvc.perform(post("/api/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"张三\",\"gender\":\"男\",\"age\":30,\"phone\":\"13800138000\",\"address\":\"北京市朝阳区\",\"medicalHistory\":\"无\",\"allergyHistory\":\"无\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("新增患者成功"));

        verify(patientService, times(1)).addPatient(any(Patient.class));
    }

    @Test
    public void testUpdatePatient() throws Exception {
        Patient patient = new Patient();
        patient.setPatientId(1);
        patient.setName("张三");
        patient.setGender("男");
        patient.setAge(31);
        patient.setPhone("13800138000");
        patient.setAddress("北京市朝阳区");
        patient.setMedicalHistory("无");
        patient.setAllergyHistory("无");

        when(patientService.updatePatient(any(Patient.class))).thenReturn(1);

        mockMvc.perform(put("/api/patient/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"张三\",\"gender\":\"男\",\"age\":31,\"phone\":\"13800138000\",\"address\":\"北京市朝阳区\",\"medicalHistory\":\"无\",\"allergyHistory\":\"无\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("更新患者信息成功"));

        verify(patientService, times(1)).updatePatient(any(Patient.class));
    }

    @Test
    public void testDeletePatient() throws Exception {
        when(patientService.deletePatientById(1)).thenReturn(1);

        mockMvc.perform(delete("/api/patient/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("删除患者成功"));

        verify(patientService, times(1)).deletePatientById(1);
    }

    @Test
    public void testGetPatientById() throws Exception {
        Patient patient = new Patient();
        patient.setPatientId(1);
        patient.setName("张三");
        patient.setGender("男");
        patient.setAge(30);
        patient.setPhone("13800138000");
        patient.setAddress("北京市朝阳区");
        patient.setMedicalHistory("无");
        patient.setAllergyHistory("无");

        when(patientService.getPatientById(1)).thenReturn(patient);

        mockMvc.perform(get("/api/patient/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.patientId").value(1))
                .andExpect(jsonPath("$.data.name").value("张三"))
                .andExpect(jsonPath("$.data.gender").value("男"))
                .andExpect(jsonPath("$.data.age").value(30));

        verify(patientService, times(1)).getPatientById(1);
    }

    @Test
    public void testGetAllPatients() throws Exception {
        Patient patient1 = new Patient();
        patient1.setPatientId(1);
        patient1.setName("张三");
        patient1.setGender("男");
        patient1.setAge(30);

        Patient patient2 = new Patient();
        patient2.setPatientId(2);
        patient2.setName("李四");
        patient2.setGender("女");
        patient2.setAge(25);

        List<Patient> patients = Arrays.asList(patient1, patient2);
        when(patientService.getAllPatients()).thenReturn(patients);

        mockMvc.perform(get("/api/patient"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].name").value("张三"))
                .andExpect(jsonPath("$.data[1].name").value("李四"));

        verify(patientService, times(1)).getAllPatients();
    }

    @Test
    public void testSearchPatients() throws Exception {
        Patient patient = new Patient();
        patient.setPatientId(1);
        patient.setName("张三");
        patient.setGender("男");
        patient.setAge(30);

        List<Patient> patients = Arrays.asList(patient);
        when(patientService.searchPatients("张")).thenReturn(patients);

        mockMvc.perform(get("/api/patient/search?keyword=张"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()").value(1))
                .andExpect(jsonPath("$.data[0].name").value("张三"));

        verify(patientService, times(1)).searchPatients("张");
    }
}
