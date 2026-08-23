import { createRouter, createWebHistory } from 'vue-router'

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

export default router
