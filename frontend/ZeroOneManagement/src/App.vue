<script setup>
import { RouterView, useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()

// 学长操作界面菜单(原型图布局)
const menus = [
  { path: '/senior/members', title: '组员管理', ready: true },
  { path: '/senior/groups', title: '小组管理', ready: true },
  { path: '/senior/leaves', title: '请假审核', ready: true },
  { path: '/senior/profile', title: '个人信息', ready: true },
  { path: '/dashboard', title: '仪表盘', ready: true }
]

function handleMenuSelect(path) {
  const menu = menus.find((m) => m.path === path)
  if (!menu?.ready) {
    ElMessage.info('该功能开发中')
    return
  }
  router.push(path)
}

// TODO: 登录模块开发后替换为真实用户名
const username = '学长'
</script>

<template>
  <el-container class="layout">
    <!-- 顶部栏 -->
    <el-header class="layout-header">
      <div class="layout-brand">零壹星 · 赋能驾驶舱</div>
      <div class="layout-user">
        <span>{{ username }}</span>
        <el-button link type="primary" @click="handleUnready">退出登录</el-button>
      </div>
    </el-header>

    <el-container class="layout-body">
      <!-- 左侧导航 -->
      <el-aside width="200px" class="layout-aside">
        <el-menu
          :default-active="route.path"
          class="layout-menu"
          @select="handleMenuSelect"
        >
          <el-menu-item v-for="menu in menus" :key="menu.path" :index="menu.path">
            {{ menu.title }}
          </el-menu-item>
        </el-menu>
      </el-aside>

      <!-- 主内容区 -->
      <el-main class="layout-main">
        <RouterView />
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
.layout {
  height: 100vh;
}

.layout-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid var(--el-border-color-light);
}

.layout-brand {
  font-size: 18px;
  font-weight: 600;
}

.layout-user {
  display: flex;
  align-items: center;
  gap: 8px;
}

.layout-aside {
  border-right: 1px solid var(--el-border-color-light);
}

.layout-menu {
  border-right: none;
}

.layout-main {
  background-color: var(--el-fill-color-light);
}
</style>
