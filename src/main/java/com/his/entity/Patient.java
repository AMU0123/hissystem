package com.his.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 患者实体类
 */
@Data
public class Patient implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 患者ID，自增主键
     */
    private Integer patientId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 电话
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 既往病史记录
     */
    private String medicalHistory;

    /**
     * 过敏史
     */
    private String allergyHistory;

    /**
     * 创建时间
     */
    private Date createdAt;
}