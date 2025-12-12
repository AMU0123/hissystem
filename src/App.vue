<template>
  <div class="app-container">
    <header class="app-header">
      <h1>医院信息系统</h1>
    </header>
    <main class="app-main">
      <div class="container">
        <h2>患者管理</h2>
        <PatientManager />
      </div>
    </main>
    <footer class="app-footer">
      <p>© 2025 医院信息系统 - 版权所有</p>
    </footer>
  </div>
</template>

<script setup>
import PatientManager from './components/PatientManager.vue'
</script>

<style scoped>
.app-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f5f7fa;
  font-family: Arial, sans-serif;
}

.app-header {
  background-color: #409eff;
  color: white;
  padding: 1rem;
  text-align: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.app-header h1 {
  margin: 0;
  font-size: 1.5rem;
}

.app-main {
  flex: 1;
  padding: 2rem;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  background-color: white;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.app-footer {
  background-color: #606266;
  color: white;
  text-align: center;
  padding: 1rem;
  font-size: 0.875rem;
}
</style>