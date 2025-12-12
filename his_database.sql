-- 医院信息系统(HIS)数据库创建脚本
-- 生成时间: 2025-12-12

-- 创建数据库
CREATE DATABASE IF NOT EXISTS his_system CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE his_system;

-- 删除现有表（如果存在），确保表结构更新，注意删除顺序：先删除依赖表，再删除被依赖表
DROP TABLE IF EXISTS finance;
DROP TABLE IF EXISTS statistics;
DROP TABLE IF EXISTS prescriptions;
DROP TABLE IF EXISTS medical_records;
DROP TABLE IF EXISTS tests;
DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS discounts;
DROP TABLE IF EXISTS pharmacy_inventory;
DROP TABLE IF EXISTS doctors;
DROP TABLE IF EXISTS patients;

-- 1. 患者表 (patients)
CREATE TABLE patients (
    patient_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '患者ID，自增主键',
    name VARCHAR(100) NOT NULL COMMENT '姓名',
    gender ENUM('男', '女', '未知') NOT NULL COMMENT '性别',
    age INT NOT NULL COMMENT '年龄',
    phone VARCHAR(20) NOT NULL COMMENT '电话',
    address TEXT COMMENT '地址',
    medical_history TEXT COMMENT '既往病史记录',
    allergy_history TEXT COMMENT '过敏史',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2. 医生表 (doctors) - 先创建，因为其他表有外键引用
CREATE TABLE doctors (
    doctor_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '医生ID，自增主键',
    name VARCHAR(100) NOT NULL COMMENT '医生姓名',
    title VARCHAR(50) COMMENT '职称',
    department VARCHAR(50) NOT NULL COMMENT '所属科室',
    phone VARCHAR(20) NOT NULL COMMENT '联系电话',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. 挂号记录表 (appointments)
CREATE TABLE appointments (
    appointment_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '挂号ID，自增主键',
    patient_id INT NOT NULL COMMENT '患者ID，外键',
    department VARCHAR(50) NOT NULL COMMENT '科室',
    doctor_id INT NOT NULL COMMENT '医生ID，外键',
    registration_date DATE NOT NULL COMMENT '挂号日期',
    registration_fee DECIMAL(10,2) NOT NULL COMMENT '挂号费',
    status ENUM('已挂号', '已就诊', '已取消') NOT NULL DEFAULT '已挂号' COMMENT '就诊状态',
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. 病历表 (medical_records)
CREATE TABLE medical_records (
    record_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '病历ID，自增主键',
    patient_id INT NOT NULL COMMENT '患者ID，外键',
    doctor_id INT NOT NULL COMMENT '医生ID，外键',
    chief_complaint TEXT NOT NULL COMMENT '主诉',
    present_illness TEXT NOT NULL COMMENT '现病史',
    physical_examination TEXT NOT NULL COMMENT '体格检查',
    preliminary_diagnosis TEXT NOT NULL COMMENT '初步诊断',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 5. 药品表 (pharmacy_inventory)
CREATE TABLE pharmacy_inventory (
    medicine_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '药品ID，自增主键',
    name VARCHAR(100) NOT NULL COMMENT '药品名称',
    specification VARCHAR(100) NOT NULL COMMENT '规格',
    manufacturer VARCHAR(100) NOT NULL COMMENT '生产厂家',
    category VARCHAR(50) NOT NULL COMMENT '药品分类',
    price DECIMAL(10,2) NOT NULL COMMENT '单价',
    quantity INT NOT NULL DEFAULT 0 COMMENT '库存数量',
    expiration_date DATE NOT NULL COMMENT '有效期',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '入库时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 6. 处方表 (prescriptions)
CREATE TABLE prescriptions (
    prescription_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '处方ID，自增主键',
    patient_id INT NOT NULL COMMENT '患者ID，外键',
    doctor_id INT NOT NULL COMMENT '医生ID，外键',
    medicine_name VARCHAR(100) NOT NULL COMMENT '药品名称',
    dosage TEXT NOT NULL COMMENT '用法用量',
    total_cost DECIMAL(10,2) NOT NULL COMMENT '总金额',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '开具时间',
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 7. 财务表 (finance)
CREATE TABLE finance (
    finance_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '财务记录ID，自增主键',
    appointment_id INT COMMENT '挂号ID，外键',
    prescription_id INT COMMENT '处方ID，外键',
    registration_fee DECIMAL(10,2) DEFAULT 0.00 COMMENT '挂号费金额',
    medicine_fee DECIMAL(10,2) DEFAULT 0.00 COMMENT '药品费用',
    discount DECIMAL(10,2) DEFAULT 0.00 COMMENT '折扣金额',
    total_fee DECIMAL(10,2) NOT NULL COMMENT '最终总费用',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '记账时间',
    FOREIGN KEY (appointment_id) REFERENCES appointments(appointment_id) ON DELETE SET NULL,
    FOREIGN KEY (prescription_id) REFERENCES prescriptions(prescription_id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 8. 检查检验表 (tests)
CREATE TABLE tests (
    test_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '检查ID，自增主键',
    patient_id INT NOT NULL COMMENT '患者ID，外键',
    test_type VARCHAR(50) NOT NULL COMMENT '检查类型',
    test_date DATE NOT NULL COMMENT '检查日期',
    result TEXT NOT NULL COMMENT '检查结果',
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 9. 优惠折扣表 (discounts)
CREATE TABLE discounts (
    discount_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '折扣ID，自增主键',
    discount_code VARCHAR(20) NOT NULL UNIQUE COMMENT '折扣码，唯一',
    discount_value DECIMAL(10,2) NOT NULL COMMENT '折扣金额',
    valid_from DATE NOT NULL COMMENT '生效日期',
    valid_until DATE NOT NULL COMMENT '失效日期',
    description TEXT COMMENT '折扣说明'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 10. 统计报表表 (statistics)
CREATE TABLE statistics (
    statistic_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '统计ID，自增主键',
    statistic_type VARCHAR(100) NOT NULL COMMENT '统计类型',
    value DECIMAL(10,2) NOT NULL COMMENT '统计数值',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '统计时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 创建索引以提高查询性能
CREATE INDEX idx_patients_name ON patients(name);
CREATE INDEX idx_patients_phone ON patients(phone);
CREATE INDEX idx_appointments_patient_id ON appointments(patient_id);
CREATE INDEX idx_appointments_doctor_id ON appointments(doctor_id);
CREATE INDEX idx_appointments_registration_date ON appointments(registration_date);
CREATE INDEX idx_medical_records_patient_id ON medical_records(patient_id);
CREATE INDEX idx_medical_records_doctor_id ON medical_records(doctor_id);
CREATE INDEX idx_pharmacy_inventory_name ON pharmacy_inventory(name);
CREATE INDEX idx_pharmacy_inventory_category ON pharmacy_inventory(category);
CREATE INDEX idx_pharmacy_inventory_expiration_date ON pharmacy_inventory(expiration_date);
CREATE INDEX idx_prescriptions_patient_id ON prescriptions(patient_id);
CREATE INDEX idx_prescriptions_doctor_id ON prescriptions(doctor_id);
CREATE INDEX idx_finance_appointment_id ON finance(appointment_id);
CREATE INDEX idx_finance_prescription_id ON finance(prescription_id);
CREATE INDEX idx_tests_patient_id ON tests(patient_id);
CREATE INDEX idx_tests_test_type ON tests(test_type);
CREATE INDEX idx_tests_test_date ON tests(test_date);
CREATE INDEX idx_statistics_statistic_type ON statistics(statistic_type);
CREATE INDEX idx_statistics_created_at ON statistics(created_at);

-- 插入初始数据（可选）
-- 插入医生数据
INSERT INTO doctors (name, title, department, phone) VALUES
('张医生', '主任医师', '内科', '13800138001'),
('李医生', '副主任医师', '外科', '13800138002'),
('王医生', '主治医师', '儿科', '13800138003'),
('赵医生', '主任医师', '妇产科', '13800138004');

-- 插入折扣数据
INSERT INTO discounts (discount_code, discount_value, valid_from, valid_until, description) VALUES
('DISCOUNT2025', 50.00, '2025-01-01', '2025-12-31', '2025年度优惠'),
('NEWPATIENT', 30.00, '2025-01-01', '2025-12-31', '新患者优惠');

-- 插入统计数据示例
INSERT INTO statistics (statistic_type, value) VALUES
('今日挂号数', 150.00),
('今日处方数', 200.00),
('今日总收入', 50000.00);

-- 显示创建的数据库和表
SELECT '数据库创建成功！' AS message;
SHOW TABLES;