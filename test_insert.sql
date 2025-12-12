-- 测试脚本：只插入医生数据
USE his_system;

-- 先删除医生表（如果存在）
DROP TABLE IF EXISTS doctors;

-- 创建医生表
CREATE TABLE doctors (
    doctor_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    title VARCHAR(50),
    department VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 插入医生数据
INSERT INTO doctors (name, title, department, phone) VALUES
('张医生', '主任医师', '内科', '13800138001');

-- 显示插入结果
SELECT * FROM doctors;

-- 清理测试表
DROP TABLE doctors;