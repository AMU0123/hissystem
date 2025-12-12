package com.his;

import com.his.entity.Patient;
import com.his.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 数据初始化类，在应用启动时执行，自动插入测试数据
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private PatientService patientService;

    @Override
    public void run(String... args) throws Exception {
        // 检查是否已有患者数据
        if (patientService.getAllPatients().isEmpty()) {
            // 插入测试患者数据
            insertTestPatients();
            System.out.println("测试患者数据初始化完成！");
        } else {
            System.out.println("已存在患者数据，跳过初始化");
        }
    }

    /**
     * 插入测试患者数据
     */
    private void insertTestPatients() {
        // 患者1
        Patient patient1 = new Patient();
        patient1.setName("张三");
        patient1.setGender("男");
        patient1.setAge(35);
        patient1.setPhone("13800138001");
        patient1.setAddress("北京市朝阳区");
        patient1.setMedicalHistory("高血压");
        patient1.setAllergyHistory("青霉素");
        patientService.addPatient(patient1);

        // 患者2
        Patient patient2 = new Patient();
        patient2.setName("李四");
        patient2.setGender("女");
        patient2.setAge(28);
        patient2.setPhone("13800138002");
        patient2.setAddress("上海市浦东新区");
        patient2.setMedicalHistory("糖尿病");
        patient2.setAllergyHistory("无");
        patientService.addPatient(patient2);

        // 患者3
        Patient patient3 = new Patient();
        patient3.setName("王五");
        patient3.setGender("男");
        patient3.setAge(45);
        patient3.setPhone("13800138003");
        patient3.setAddress("广州市天河区");
        patient3.setMedicalHistory("冠心病");
        patient3.setAllergyHistory("阿司匹林");
        patientService.addPatient(patient3);

        // 患者4
        Patient patient4 = new Patient();
        patient4.setName("赵六");
        patient4.setGender("女");
        patient4.setAge(32);
        patient4.setPhone("13800138004");
        patient4.setAddress("深圳市南山区");
        patient4.setMedicalHistory("哮喘");
        patient4.setAllergyHistory("花粉");
        patientService.addPatient(patient4);

        // 患者5
        Patient patient5 = new Patient();
        patient5.setName("孙七");
        patient5.setGender("男");
        patient5.setAge(50);
        patient5.setPhone("13800138005");
        patient5.setAddress("成都市锦江区");
        patient5.setMedicalHistory("关节炎");
        patient5.setAllergyHistory("无");
        patientService.addPatient(patient5);
    }
}