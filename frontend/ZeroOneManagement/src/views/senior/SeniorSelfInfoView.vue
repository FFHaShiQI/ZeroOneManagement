<script setup>
import { reactive, ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getSeniorProfile, updateSeniorProfile } from '@/api/senior/senior'
import { uploadFile } from '@/api/upload/upload'
import { SENIOR_TYPE_OPTIONS, optionLabel } from '@/constants/senior'

const router = useRouter()

// 登录成功后 LoginView 已将当前用户 id 存入 localStorage(id)
function getCurrentSeniorId() {
  return Number(localStorage.getItem('id'))
}

const profile = ref(null)
const loading = ref(false)

async function loadProfile() {
  const currentId = getCurrentSeniorId()
  if (!currentId) {
    ElMessage.warning('未获取到登录信息,请重新登录')
    router.push('/login')
    return
  }
  loading.value = true
  try {
    const res = await getSeniorProfile(currentId)
    if (res.code === 1) {
      profile.value = res.data
    } else {
      ElMessage.error(res.msg || '查询失败')
    }
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
    // 密码不回显,留空表示不修改
    password: '',
    name: profile.value.name ?? '',
    number: profile.value.number ?? '',
    image: profile.value.image ?? '',
    type: profile.value.type ?? null
  })
  dialogVisible.value = true
}

// 头像选择本地文件后直接上传,成功后把返回的 url 写入 form.image
async function handleAvatarChange(uploadFileObj) {
  const file = uploadFileObj.raw
  if (!file) return
  const formData = new FormData()
  formData.append('file', file)
  const uploadRes = await uploadFile(formData)
  if (uploadRes.code === 1) {
    form.image = uploadRes.data
    ElMessage.success('头像上传成功')
  } else {
    ElMessage.error(uploadRes.msg || '头像上传失败')
  }
}

async function handleSubmit() {
  try {
    await formRef.value.validate()
  } catch {
    return
  }
  submitting.value = true
  try {
    const payload = { ...form }
    // 后端 update 在 password != null 时即更新,空字符串必须剔除,否则会把密码置空
    if (!payload.password) {
      delete payload.password
    }
    const res = await updateSeniorProfile(payload)
    if (res.code === 1) {
      ElMessage.success('修改成功')
      dialogVisible.value = false
      loadProfile()
    } else {
      ElMessage.error(res.msg || '保存失败')
    }
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

    <!-- 原型: 此页面展示个人所有信息(密码为敏感信息,不对外展示) -->
    <div v-if="profile" class="profile-body">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="用户名">{{ profile.username ?? '-' }}</el-descriptions-item>
        <el-descriptions-item label="昵称">{{ profile.name ?? '-' }}</el-descriptions-item>
        <el-descriptions-item label="类型">
          {{ optionLabel(SENIOR_TYPE_OPTIONS, profile.type) }}
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
        <!-- 用户名/类型在注册时确定,不在此修改 -->
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" disabled placeholder="用户名不可修改" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" show-password placeholder="留空则不修改" />
        </el-form-item>
        <el-form-item label="昵称" prop="name">
          <el-input v-model="form.name" placeholder="请输入昵称" />
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择类型" style="width: 100%">
            <el-option v-for="opt in SENIOR_TYPE_OPTIONS" :key="opt.value" :label="opt.label" :value="opt.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="联系方式" prop="number">
          <el-input v-model="form.number" placeholder="请输入联系方式" />
        </el-form-item>
        <el-form-item label="头像" prop="image">
          <el-upload
            class="avatar-uploader"
            action="#"
            :show-file-list="false"
            :auto-upload="false"
            accept="image/*"
            :on-change="handleAvatarChange"
          >
            <div class="avatar-upload-trigger">
              <el-avatar v-if="form.image" :src="form.image" :size="60" />
              <span v-else class="avatar-upload-tip">点击上传头像</span>
            </div>
          </el-upload>
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

.avatar-uploader :deep(.el-upload) {
  border: 1px dashed #dcdfe6;
  border-radius: 6px;
  cursor: pointer;
  padding: 4px;
  overflow: hidden;
  transition: border-color 0.3s;
}

.avatar-uploader :deep(.el-upload:hover) {
  border-color: #409eff;
}

.avatar-uploader :deep(.el-upload:focus-visible) {
  outline: none;
}

.avatar-upload-trigger {
  display: flex;
  align-items: center;
  justify-content: center;
  min-width: 80px;
  min-height: 68px;
}

.avatar-upload-tip {
  color: #909399;
  font-size: 12px;
}
</style>
