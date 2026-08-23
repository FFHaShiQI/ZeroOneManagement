<script setup>
import { useRoute, useRouter } from 'vue-router'
import { clearAuth } from '@/utils/auth'

const route = useRoute()
const router = useRouter()

// 学长操作界面菜单
const menus = [
  { path: '/senior/membersManage', title: '组员管理' },
  { path: '/senior/groupManage', title: '小组管理' },
  { path: '/senior/leavesManage', title: '请假审核' },
  { path: '/senior/seniorSelfInfo', title: '个人信息' },
  { path: '/senior/dashboard', title: '仪表盘' }
]

function handleMenuSelect(path) {
  router.push(path)
}

function handleLogout() {
  clearAuth()
  router.replace('/login')
}

// TODO: 登录模块开发后替换为真实用户名
const username = '学长'
</script>

<template>
  <div class="common-layout">
    <el-container>
      <!-- Header 区域 -->
      <el-header class="header">
        <div class="brand">零壹星 · 赋能驾驶舱</div>
        <div class="user">
          <span>{{ username }}</span>
          <el-button link type="primary" @click="handleLogout">退出登录</el-button>
        </div>
      </el-header>

      <el-container>
        <!-- 左侧菜单 -->
        <el-aside width="200px" class="aside">
          <el-menu
            :default-active="route.path"
            class="menu"
            @select="handleMenuSelect"
          >
            <el-menu-item v-for="menu in menus" :key="menu.path" :index="menu.path">
              {{ menu.title }}
            </el-menu-item>
          </el-menu>
        </el-aside>
        <!-- 主体区域 -->
        <el-main class="main">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped>
.common-layout {
  height: 100vh;
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid var(--el-border-color-light);
}

.brand {
  font-size: 18px;
  font-weight: 600;
}

.user {
  display: flex;
  align-items: center;
  gap: 8px;
}

.aside {
  border-right: 1px solid var(--el-border-color-light);
}

.menu {
  border-right: none;
}

.main {
  background-color: var(--el-fill-color-light);
}
</style>
