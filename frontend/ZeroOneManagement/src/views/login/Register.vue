<template>
  <div class="login-page">
    <div class="login-card">
      <div class="brand">
        <h1>注册账号</h1>
        <p>加入零壹星 · 赋能驾驶舱</p>
      </div>

      <form class="login-form" @submit.prevent="handleRegister">
        <div class="form-item">
          <label for="username">账号</label>
          <input id="username" v-model.trim="form.username" type="text" placeholder="请输入账号（唯一）" />
        </div>

        <div class="form-item">
          <label for="password">密码</label>
          <input id="password" v-model="form.password" type="password" placeholder="请输入密码" />
        </div>

        <div class="form-item">
          <label for="confirm">确认密码</label>
          <input id="confirm" v-model="form.confirmPassword" type="password" placeholder="请再次输入密码" />
        </div>

        <!-- 勾选 = 学长（填邀请码）；不勾选 = 学员（填小组 ID） -->
        <div class="form-options">
          <label class="checkbox">
            <input v-model="form.isSenior" type="checkbox" />
            <span>注册为学长（需要邀请码）</span>
          </label>
        </div>

        <div v-if="form.isSenior" class="form-item">
          <label for="invite">学长邀请码</label>
          <input id="invite" v-model.trim="form.inviteCode" type="text" placeholder="请输入邀请码" />
        </div>

        <div v-else class="form-item">
          <label for="groupId">小组 ID</label>
          <input id="groupId" v-model.number="form.groupId" type="number" min="1" placeholder="请输入所属小组 ID" />
        </div>

        <div class="form-actions">
          <button class="btn btn-primary" type="submit" :disabled="loading">
            {{ loading ? '注册中...' : '创 建 账 号' }}
          </button>
        </div>

        <p v-if="errorMsg" class="error-msg">{{ errorMsg }}</p>
      </form>

      <div class="form-footer">
        <span>已有账号？</span>
        <RouterLink to="/login">返回登录</RouterLink>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { apiRegister } from '@/api/auth'

const router = useRouter()

const form = reactive({
  username: '',
  password: '',
  confirmPassword: '',
  isSenior: false,
  inviteCode: '',
  groupId: undefined as number | undefined
})

const loading = ref(false)
const errorMsg = ref('')

async function handleRegister() {
  errorMsg.value = ''
  const { username, password, confirmPassword, isSenior, inviteCode, groupId } = form

  // 简单校验
  if (!username || !password || !confirmPassword) {
    errorMsg.value = '请填写完整信息'
    return
  }
  if (password !== confirmPassword) {
    errorMsg.value = '两次输入的密码不一致'
    return
  }
  if (!isSenior && (groupId === undefined || groupId <= 0)) {
    errorMsg.value = '学员注册请填写小组 ID'
    return
  }

  loading.value = true
  try {
    const res = await apiRegister({
      username,
      password,
      option: isSenior,
      inviteCode: isSenior ? inviteCode : undefined,
      groupId: isSenior ? undefined : groupId
    })
    if (res.code === 1) {
      alert('注册成功，请登录')
      router.replace('/login')
    }
  } catch (err) {
    errorMsg.value = err instanceof Error ? err.message : '注册失败，请重试'
  } finally {
    loading.value = false
  }
}
</script>
