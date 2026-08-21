<template>
  <div class="placeholder">
    <h1>组员操作界面</h1>
    <p>这里是土豆雷负责的学员模块，登录跳转验证用</p>
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
