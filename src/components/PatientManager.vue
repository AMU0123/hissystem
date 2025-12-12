<template>
  <div class="patient-manager">
    <!-- 搜索栏 -->
    <div class="search-bar">
      <input
        type="text"
        v-model="searchKeyword"
        placeholder="输入患者姓名或电话搜索"
        @input="searchPatients"
      />
      <button class="btn-primary" @click="openAddModal">新增患者</button>
    </div>

    <!-- 患者列表 -->
    <div class="table-container">
      <table class="table">
        <thead>
          <tr>
            <th>病历号</th>
            <th>姓名</th>
            <th>性别</th>
            <th>年龄</th>
            <th>电话</th>
            <th>地址</th>
            <th>创建时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="patient in patients" :key="patient.patientId">
            <td>{{ patient.patientId }}</td>
            <td>{{ patient.name }}</td>
            <td>{{ patient.gender }}</td>
            <td>{{ patient.age }}</td>
            <td>{{ patient.phone }}</td>
            <td>{{ patient.address }}</td>
            <td>{{ formatDate(patient.createdAt) }}</td>
            <td>
              <div class="action-buttons">
                <button class="btn-primary" @click="openEditModal(patient)">编辑</button>
                <button class="btn-danger" @click="deletePatient(patient.patientId)">删除</button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- 新增/编辑患者模态框 -->
    <div v-if="showModal" class="modal" @click.self="closeModal">
      <div class="modal-content">
        <div class="modal-header">
          <h3 class="modal-title">{{ isEditMode ? '编辑患者' : '新增患者' }}</h3>
          <button class="modal-close" @click="closeModal">&times;</button>
        </div>
        <form @submit.prevent="savePatient">
          <div class="form-group">
            <label class="form-label" for="name">姓名</label>
            <input
              type="text"
              id="name"
              v-model="formData.name"
              required
              placeholder="请输入患者姓名"
            />
          </div>

          <div class="form-group">
            <label class="form-label" for="gender">性别</label>
            <select id="gender" v-model="formData.gender" required>
              <option value="">请选择性别</option>
              <option value="男">男</option>
              <option value="女">女</option>
              <option value="未知">未知</option>
            </select>
          </div>

          <div class="form-group">
            <label class="form-label" for="age">年龄</label>
            <input
              type="number"
              id="age"
              v-model.number="formData.age"
              required
              placeholder="请输入患者年龄"
              min="0"
              max="120"
            />
          </div>

          <div class="form-group">
            <label class="form-label" for="phone">电话</label>
            <input
              type="tel"
              id="phone"
              v-model="formData.phone"
              required
              placeholder="请输入患者电话"
              pattern="^1[3-9]\d{9}$"
            />
          </div>

          <div class="form-group">
            <label class="form-label" for="address">地址</label>
            <input
              type="text"
              id="address"
              v-model="formData.address"
              placeholder="请输入患者地址"
            />
          </div>

          <div class="form-group">
            <label class="form-label" for="medicalHistory">既往病史</label>
            <textarea
              id="medicalHistory"
              v-model="formData.medicalHistory"
              placeholder="请输入患者既往病史"
              rows="3"
            ></textarea>
          </div>

          <div class="form-group">
            <label class="form-label" for="allergyHistory">过敏史</label>
            <textarea
              id="allergyHistory"
              v-model="formData.allergyHistory"
              placeholder="请输入患者过敏史"
              rows="3"
            ></textarea>
          </div>

          <div class="modal-footer">
            <button type="button" class="btn-info" @click="closeModal">取消</button>
            <button type="submit" class="btn-primary">{{ isEditMode ? '更新' : '保存' }}</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue'
import api from '../api/api.js'

// 响应式数据
const patients = ref([])
const showModal = ref(false)
const isEditMode = ref(false)
const searchKeyword = ref('')
const formData = reactive({
  patientId: null,
  name: '',
  gender: '',
  age: null,
  phone: '',
  address: '',
  medicalHistory: '',
  allergyHistory: ''
})

// 格式化日期
const formatDate = (date) => {
  if (!date) return ''
  const d = new Date(date)
  return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')} ${String(d.getHours()).padStart(2, '0')}:${String(d.getMinutes()).padStart(2, '0')}:${String(d.getSeconds()).padStart(2, '0')}`
}

// 获取所有患者
const getAllPatients = async () => {
  try {
    const response = await api.patient.getAllPatients()
    if (response.data.success) {
      patients.value = response.data.data
    } else {
      alert(response.data.message)
    }
  } catch (error) {
    console.error('获取患者列表失败:', error)
    alert('获取患者列表失败，请稍后重试')
  }
}

// 搜索患者
const searchPatients = async () => {
  if (!searchKeyword.value.trim()) {
    await getAllPatients()
    return
  }
  try {
    const response = await api.patient.searchPatients(searchKeyword.value)
    if (response.data.success) {
      patients.value = response.data.data
    } else {
      alert(response.data.message)
    }
  } catch (error) {
    console.error('搜索患者失败:', error)
    alert('搜索患者失败，请稍后重试')
  }
}

// 打开新增患者模态框
const openAddModal = () => {
  isEditMode.value = false
  resetForm()
  showModal.value = true
}

// 打开编辑患者模态框
const openEditModal = (patient) => {
  isEditMode.value = true
  Object.assign(formData, patient)
  showModal.value = true
}

// 关闭模态框
const closeModal = () => {
  showModal.value = false
  resetForm()
}

// 重置表单
const resetForm = () => {
  formData.patientId = null
  formData.name = ''
  formData.gender = ''
  formData.age = null
  formData.phone = ''
  formData.address = ''
  formData.medicalHistory = ''
  formData.allergyHistory = ''
}

// 保存患者
const savePatient = async () => {
  try {
    let response
    if (isEditMode.value) {
      // 更新患者
      response = await api.patient.updatePatient(formData.patientId, formData)
    } else {
      // 新增患者
      response = await api.patient.addPatient(formData)
    }
    
    if (response.data.success) {
      alert(response.data.message)
      closeModal()
      await getAllPatients()
    } else {
      alert(response.data.message)
    }
  } catch (error) {
    console.error('保存患者失败:', error)
    alert('保存患者失败，请稍后重试')
  }
}

// 删除患者
const deletePatient = async (patientId) => {
  if (!confirm('确定要删除该患者吗？')) {
    return
  }
  
  try {
    const response = await api.patient.deletePatient(patientId)
    if (response.data.success) {
      alert(response.data.message)
      await getAllPatients()
    } else {
      alert(response.data.message)
    }
  } catch (error) {
    console.error('删除患者失败:', error)
    alert('删除患者失败，请稍后重试')
  }
}

// 初始化
onMounted(() => {
  getAllPatients()
})
</script>

<style scoped>
.patient-manager {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.table-container {
  overflow-x: auto;
}

.action-buttons {
  display: flex;
  gap: 5px;
}

.modal-content {
  max-width: 600px;
}
</style>