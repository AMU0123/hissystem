-- 插入测试患者数据
USE his_system;

INSERT INTO patients (name, gender, age, phone, address, medical_history, allergy_history, created_at) VALUES
('张三', '男', 35, '13800138001', '北京市朝阳区', '高血压', '青霉素', NOW()),
('李四', '女', 28, '13800138002', '上海市浦东新区', '糖尿病', '无', NOW()),
('王五', '男', 45, '13800138003', '广州市天河区', '冠心病', '阿司匹林', NOW()),
('赵六', '女', 32, '13800138004', '深圳市南山区', '哮喘', '花粉', NOW()),
('孙七', '男', 50, '13800138005', '成都市锦江区', '关节炎', '无', NOW());

-- 查询插入的患者数据
SELECT * FROM patients;