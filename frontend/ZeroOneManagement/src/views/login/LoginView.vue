<template>
  <div class="login-page">
    <div class="login-card">
      <!-- 标题 -->
      <div class="brand">
        <h1>零壹星 · 赋能驾驶舱</h1>
        <p>01 星球学员管理系统</p>
      </div>

      <!-- 表单 -->
      <form class="login-form" @submit.prevent="handleLogin">
        <div class="form-item">
          <label for="username">用户名</label>
          <input id="username" v-model.trim="form.username" type="text" placeholder="请输入账号" autocomplete="username" />
        </div>
        <div class="form-item">
          <label for="password">密码</label>
          <input id="password" v-model="form.password" type="password" placeholder="请输入密码"
            autocomplete="current-password" />
        </div>

        <!-- 学长登录勾选 -->
        <div class="form-options">
          <label class="checkbox">
            <input v-model="form.isSenior" type="checkbox" />
            <span>学长登录</span>
          </label>
        </div>

        <!-- 登录 / 重置 -->
        <div class="form-actions">
          <button class="btn btn-primary" type="submit" :disabled="loading">
            {{ loading ? '登录中...' : '登 录' }}
          </button>
          <button class="btn btn-plain" type="button" @click="handleReset">重 置</button>
        </div>

        <!-- 错误提示 -->
        <p v-if="errorMsg" class="error-msg">{{ errorMsg }}</p>
      </form>

      <!-- 没有账号 → 注册 -->
      <div class="form-footer">
        <span>没有账号？</span>
        <RouterLink to="/register">立即注册</RouterLink>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { apiLogin } from '@/api/utils/auth'
import { setAuth } from '@/utils/auth'

const router = useRouter()

const form = reactive({
  username: '',
  password: '',
  isSenior: false // 勾选 = 学长登录
})

const loading = ref(false)
const errorMsg = ref('')

async function handleLogin() {
  errorMsg.value = ''
  // 简单校验
  if (!form.username || !form.password) {
    errorMsg.value = '请输入账号和密码'
    return
  }

  loading.value = true
  try {
    const res = await apiLogin({
      username: form.username,
      password: form.password,
      role: form.isSenior ? 'SENIOR' : 'MEMBER'
    })
    if (res.code === 1 && res.data) {
      // 保存登录态，按角色跳转
      setAuth(res.data.token, res.data.role)
      router.replace(res.data.role === 'SENIOR' ? '/senior' : '/member')
    }
  } catch (err) {
    errorMsg.value = err instanceof Error ? err.message : '登录失败，请重试'
  } finally {
    loading.value = false
  }
}

function handleReset() {
  form.username = ''
  form.password = ''
  form.isSenior = false
  errorMsg.value = ''
}
</script>
