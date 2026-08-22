import { createRouter, createWebHistory } from 'vue-router'
import { getToken, getRole } from '@/utils/auth'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', redirect: '/login' },
    // ===== 登录 / 注册（登录模块）=====
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/login/Login.vue'),
      meta: { title: '登录' }
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('@/views/login/Register.vue'),
      meta: { title: '注册' }
    },
    // ===== 组员操作界面（feature/member）=====
    {
      path: '/member',
      name: 'member',
      component: () => import('@/views/layout/MemberIndex.vue'),
      meta: { title: '组员操作界面', requiresAuth: true },
      children: [
        { path: '', redirect: '/member/memberSelfInfo' },
        { path: 'memberSelfInfo', name: 'memberSelfInfo', component: () => import('@/views/member/MemberSelfInfoView.vue') }
        // TODO: groupInfo / leaveApply 视图待 feature/member 分支合并后补充
      ]
    },
    // ===== 学长操作界面（feature/senior/*）=====
    {
      path: '/senior',
      name: 'senior',
      component: () => import('@/views/layout/SeniorIndex.vue'),
      meta: { title: '学长操作界面', requiresAuth: true },
      children: [
        { path: '', redirect: '/senior/groupManage' },
        { path: 'groupManage', name: 'senior-group-manage', component: () => import('@/views/senior/GroupManage.vue') },
        { path: 'leavesManage', name: 'senior-leaves-manage', component: () => import('@/views/senior/LeaveAudit.vue') },
        { path: 'seniorSelfInfo', name: 'senior-self-info', component: () => import('@/views/senior/Profile.vue') }
        // TODO: membersManage 视图待 feature/senior/member-manage 分支合并后补充
      ]
    },
    // ===== 仪表盘（附加功能）=====
    {
      path: '/dashboard',
      name: 'dashboard',
      component: () => import('@/views/Dashboard.vue'),
      meta: { title: '仪表盘', requiresAuth: true }
    },
    // 兜底：未匹配的路由回登录页
    { path: '/:pathMatch(.*)*', redirect: '/login' }
  ]
})

// 全局前置守卫：登录控制（临时关闭：注释了登录拦截，便于单独查看各页面样式）
router.beforeEach((to) => {
  document.title = to.meta.title ? `${to.meta.title} - 零壹星 · 赋能驾驶舱` : '零壹星 · 赋能驾驶舱'

  const token = getToken()

  //  需要登录的页面，未登录 → 回登录页
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
