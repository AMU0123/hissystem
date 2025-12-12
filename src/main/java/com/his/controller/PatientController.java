package com.his.controller;

import com.his.entity.Patient;
import com.his.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 患者控制器
 */
@RestController
@RequestMapping("/api/patient")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class PatientController {

    @Autowired
    private PatientService patientService;

    /**
     * 获取所有患者列表
     * @return 患者列表
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPatients() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Patient> patients = patientService.getAllPatients();
            result.put("success", true);
            result.put("data", patients);
            result.put("message", "获取患者列表成功");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取患者列表失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    /**
     * 根据ID获取患者信息
     * @param id 患者ID
     * @return 患者信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPatientById(@PathVariable("id") Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            Patient patient = patientService.getPatientById(id);
            if (patient != null) {
                result.put("success", true);
                result.put("data", patient);
                result.put("message", "获取患者信息成功");
                return ResponseEntity.ok(result);
            } else {
                result.put("success", false);
                result.put("message", "患者不存在");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "获取患者信息失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    /**
     * 新增患者
     * @param patient 患者信息
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> addPatient(@RequestBody Patient patient) {
        Map<String, Object> result = new HashMap<>();
        try {
            int rows = patientService.addPatient(patient);
            if (rows > 0) {
                result.put("success", true);
                result.put("data", patient);
                result.put("message", "新增患者成功");
                return ResponseEntity.status(HttpStatus.CREATED).body(result);
            } else {
                result.put("success", false);
                result.put("message", "新增患者失败");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "新增患者失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    /**
     * 更新患者信息
     * @param id 患者ID
     * @param patient 患者信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updatePatient(@PathVariable("id") Integer id, @RequestBody Patient patient) {
        Map<String, Object> result = new HashMap<>();
        try {
            patient.setPatientId(id);
            int rows = patientService.updatePatient(patient);
            if (rows > 0) {
                result.put("success", true);
                result.put("data", patient);
                result.put("message", "更新患者信息成功");
                return ResponseEntity.ok(result);
            } else {
                result.put("success", false);
                result.put("message", "患者不存在或更新失败");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "更新患者信息失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    /**
     * 删除患者
     * @param id 患者ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletePatient(@PathVariable("id") Integer id) {
        Map<String, Object> result = new HashMap<>();
        try {
            int rows = patientService.deletePatientById(id);
            if (rows > 0) {
                result.put("success", true);
                result.put("message", "删除患者成功");
                return ResponseEntity.ok(result);
            } else {
                result.put("success", false);
                result.put("message", "患者不存在或删除失败");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "删除患者失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    /**
     * 搜索患者
     * @param keyword 关键词（姓名或电话）
     * @return 患者列表
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchPatients(@RequestParam String keyword) {
        Map<String, Object> result = new HashMap<>();
        try {
            List<Patient> patients = patientService.searchPatients(keyword);
            result.put("success", true);
            result.put("data", patients);
            result.put("message", "搜索患者成功");
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "搜索患者失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
}