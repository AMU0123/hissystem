import axios from 'axios'

// 创建Axios实例
const api = axios.create({
  baseURL: '/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// 患者API
const patientApi = {
  // 获取所有患者
  getAllPatients() {
    return api.get('/patient')
  },
  // 根据ID获取患者
  getPatientById(id) {
    return api.get(`/patient/${id}`)
  },
  // 新增患者
  addPatient(patient) {
    return api.post('/patient', patient)
  },
  // 更新患者
  updatePatient(id, patient) {
    return api.put(`/patient/${id}`, patient)
  },
  // 删除患者
  deletePatient(id) {
    return api.delete(`/patient/${id}`)
  },
  // 搜索患者
  searchPatients(keyword) {
    return api.get(`/patient/search?keyword=${keyword}`)
  }
}

export default {
  patient: patientApi
}