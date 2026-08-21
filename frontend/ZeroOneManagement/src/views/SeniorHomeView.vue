<template>
  <div class="placeholder">
    <h1>学长操作界面</h1>
    <p>这里是学长模块，登录跳转验证用</p>
    <button class="btn btn-plain" @click="handleLogout">退出登录</button>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { clearAuth } from '@/utils/auth'

const router = useRouter()

function handleLogout() {
  clearAuth()
  router.replace('/login')
}
</script>
