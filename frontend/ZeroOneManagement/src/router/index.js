import { createRouter, createWebHistory } from 'vue-router'
import { ElMessage } from 'element-plus' 

import MemberIndexView from '@/views/layout/MemberIndex.vue'
import SeniorIndexView from '@/views/layout/SeniorIndex.vue'
import LoginView from '@/views/login/LoginView.vue'
import RegisterView from '@/views/login/RegisterView.vue'
import MemberSelfInfoView from '@/views/member/MemberSelfInfoView.vue'
import GroupInfoView from '@/views/member/GroupInfoView.vue'
import LeaveApplyView from '@/views/member/LeaveApplyView.vue'
import GroupManageView from '@/views/senior/GroupsManageView.vue'
import LeavesManageView from '@/views/senior/LeavesManageView.vue'
import MembersManageView from '@/views/senior/MembersManageView.vue'
import SeniorSelfInfoView from '@/views/senior/SeniorSelfInfoView.vue'
import DashboardView from '@/views/senior/DashboardView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', redirect: '/login' },
    { path: '/login', name: 'login', component: LoginView },
    { path: '/register', name: 'register', component: RegisterView },

    {
      path: '/member',
      name: 'member',
      component: MemberIndexView,
      children: [
        { path: '', redirect: '/member/memberSelfInfo' },
        { path: 'memberSelfInfo', component: MemberSelfInfoView },
        { path: 'groupInfo', component: GroupInfoView },
        { path: 'leaveApply', component: LeaveApplyView }
      ]
    },
    {
      path: '/senior',
      name: 'senior',
      component: SeniorIndexView,
      children: [
        { path: '', redirect: '/senior/membersManage' },
        { path: 'groupManage', component: GroupManageView, meta: { title: '小组管理' } },
        { path: 'leavesManage', component: LeavesManageView, meta: { title: '请假审核' } },
        { path: 'membersManage', component: MembersManageView, meta: { title: '组员管理' } },
        { path: 'seniorSelfInfo', component: SeniorSelfInfoView, meta: { title: '个人信息' } },
        { path: 'dashboard', component: DashboardView, meta: { title: '仪表盘' } }
      ]
    }
  ]
})

router.afterEach((to) => {
  document.title = to.meta.title ? `${to.meta.title} - 零壹星` : '零壹星'
})

// 全局前置守卫：未登录时禁止访问除登录和注册外的页面
router.beforeEach((to, from, next) => {
  // 直接访问登录或注册页面，放行
  if (to.path === '/login' || to.path === '/register') {
    next()
  } 
  // 其他页面，检查本地是否有 userData（登录后存的整个对象，里面有 token）
  else if (localStorage.getItem('userData')) {
    next()
  } 
  // 没存过，说明没登录，踢回登录页
  else {
    ElMessage.warning('未登录，请重新登录')
    next('/login')
  }
})

export default router
