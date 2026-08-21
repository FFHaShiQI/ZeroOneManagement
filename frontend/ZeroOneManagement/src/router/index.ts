import { createRouter, createWebHistory } from 'vue-router'
import { getToken, getRole } from '@/utils/auth'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', redirect: '/login' },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/Login.vue'),
      meta: { title: '登录' }
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('@/views/Register.vue'),
      meta: { title: '注册' }
    },
    {
      path: '/member',
      name: 'member',
      component: () => import('@/views/MemberHomeView.vue'),
      meta: { title: '组员操作界面', requiresAuth: true }
    },
    {
      path: '/senior',
      name: 'senior',
      component: () => import('@/views/SeniorHomeView.vue'),
      meta: { title: '学长操作界面', requiresAuth: true }
    },
    // 兜底：未匹配的路由回登录页
    { path: '/:pathMatch(.*)*', redirect: '/login' }
  ]
})

// 全局前置守卫：登录控制
router.beforeEach((to) => {
  document.title = to.meta.title ? `${to.meta.title} - 零壹星 · 赋能驾驶舱` : '零壹星 · 赋能驾驶舱'

  const token = getToken()

  // 需要登录的页面，未登录 → 回登录页
  if (to.meta.requiresAuth && !token) {
    return { name: 'login', query: { redirect: to.fullPath } }
  }

  // 已登录访问登录/注册页 → 按角色跳走
  if ((to.name === 'login' || to.name === 'register') && token) {
    return getRole() === 'SENIOR' ? { name: 'senior' } : { name: 'member' }
  }

  return true
})

export default router
