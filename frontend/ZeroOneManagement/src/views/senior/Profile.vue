<script setup>
import { reactive, ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getSeniorProfile, updateSeniorProfile } from '../../api/senior'

// 类型: 大小学长(TODO: 与后端确认枚举,暂按 1大学长 2小学长)
const TYPE_OPTIONS = [
  { label: '大学长', value: 1 },
  { label: '小学长', value: 2 }
]

// TODO: 登录模块完成后改为当前登录用户 id
const CURRENT_SENIOR_ID = 1

const profile = ref(null)
const loading = ref(false)

async function loadProfile() {
  loading.value = true
  try {
    profile.value = await getSeniorProfile(CURRENT_SENIOR_ID)
  } finally {
    loading.value = false
  }
}

// ---------- 修改弹窗 ----------
const dialogVisible = ref(false)
const submitting = ref(false)
const formRef = ref(null)
const form = reactive({
  id: null,
  username: '',
  password: '',
  name: '',
  number: '',
  image: '',
  type: null
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  name: [{ required: true, message: '请输入昵称', trigger: 'blur' }]
}

function openEditDialog() {
  Object.assign(form, {
    id: profile.value.id,
    username: profile.value.username ?? '',
    password: profile.value.password ?? '',
    name: profile.value.name ?? '',
    number: profile.value.number ?? '',
    image: profile.value.image ?? '',
    type: profile.value.type ?? null
  })
  dialogVisible.value = true
}

async function handleSubmit() {
  try {
    await formRef.value.validate()
  } catch {
    return
  }
  submitting.value = true
  try {
    await updateSeniorProfile({ ...form })
    ElMessage.success('修改成功')
    dialogVisible.value = false
    loadProfile()
  } finally {
    submitting.value = false
  }
}

onMounted(loadProfile)
</script>

<template>
  <el-card v-loading="loading" shadow="never" class="profile-card">
    <template #header>
      <div class="profile-header">
        <span class="profile-title">个人信息</span>
        <el-button type="primary" @click="openEditDialog">修改个人信息</el-button>
      </div>
    </template>

    <!-- 原型: 此页面展示个人所有信息 -->
    <div v-if="profile" class="profile-body">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="用户名">{{ profile.username ?? '-' }}</el-descriptions-item>
        <el-descriptions-item label="密码">{{ profile.password ?? '-' }}</el-descriptions-item>
        <el-descriptions-item label="昵称">{{ profile.name ?? '-' }}</el-descriptions-item>
        <el-descriptions-item label="类型">
          {{ TYPE_OPTIONS.find((opt) => opt.value === profile.type)?.label ?? '-' }}
        </el-descriptions-item>
        <el-descriptions-item label="联系方式">{{ profile.number ?? '-' }}</el-descriptions-item>
        <el-descriptions-item label="头像">
          <el-avatar v-if="profile.image" :src="profile.image" :size="56" />
          <span v-else>-</span>
        </el-descriptions-item>
      </el-descriptions>
    </div>

    <!-- 修改弹窗 -->
    <el-dialog v-model="dialogVisible" title="修改个人信息" width="500px" destroy-on-close>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" show-password placeholder="请输入密码" />
        </el-form-item>
        <el-form-item label="昵称" prop="name">
          <el-input v-model="form.name" placeholder="请输入昵称" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择" style="width: 100%">
            <el-option v-for="opt in TYPE_OPTIONS" :key="opt.value" :label="opt.label" :value="opt.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="联系方式" prop="number">
          <el-input v-model="form.number" placeholder="请输入联系方式" />
        </el-form-item>
        <el-form-item label="头像链接" prop="image">
          <el-input v-model="form.image" placeholder="https://..." />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </el-card>
</template>

<style scoped>
.profile-card {
  max-width: 800px;
}

.profile-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.profile-title {
  font-size: 16px;
  font-weight: 600;
}
</style>
