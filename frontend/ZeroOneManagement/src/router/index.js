import { createRouter, createWebHistory } from 'vue-router'
<<<<<<< HEAD
=======

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


>>>>>>> feature/member

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/', redirect: '/login' },
    { path: '/login', name: 'login', component: LoginView }, 
    { path: '/register', name: 'register', component: RegisterView },

    {
<<<<<<< HEAD
      path: '/',
      // 产品说明:仪表盘未完成时默认进入组员管理
      redirect: '/senior/members'
=======
      path: '/member',
      name: 'member',
      component: MemberIndexView,
      children: [
        { path: '', redirect: '/member/memberSelfInfo' },
        { path: 'memberSelfInfo', component: MemberSelfInfoView },
        { path: 'groupInfo', component: GroupInfoView },
        { path: 'leaveApply', component: LeaveApplyView }      
      ]
>>>>>>> feature/member
    },
    // 学长操作界面 - 组员管理
    {
<<<<<<< HEAD
      path: '/senior/members',
      name: 'senior-member-manage',
      component: () => import('../views/senior/MemberManage.vue'),
      meta: { title: '组员管理' }
    },
    // 学长操作界面 - 小组管理
    {
      path: '/senior/groups',
      name: 'senior-group-manage',
      component: () => import('../views/senior/GroupManage.vue'),
      meta: { title: '小组管理' }
    },
    // 学长操作界面 - 请假审核
    {
      path: '/senior/leaves',
      name: 'senior-leave-audit',
      component: () => import('../views/senior/LeaveAudit.vue'),
      meta: { title: '请假审核' }
    },
    // 学长操作界面 - 个人信息
    {
      path: '/senior/profile',
      name: 'senior-profile',
      component: () => import('../views/senior/Profile.vue'),
      meta: { title: '个人信息' }
    },
    // 仪表盘
    {
      path: '/dashboard',
      name: 'dashboard',
      component: () => import('../views/Dashboard.vue'),
      meta: { title: '仪表盘' }
=======
      path: '/senior',
      name: 'senior',
      component: SeniorIndexView,
      children: [
        { path: '', redirect: '/senior/membersManage' },
        { path: 'groupManage', component: GroupManageView },
        { path: 'leavesManage', component: LeavesManageView },
        { path: 'membersManage', component: MembersManageView },
        { path: 'seniorSelfInfo', component: SeniorSelfInfoView }
      ]
>>>>>>> feature/member
    }

  ]
})

router.afterEach((to) => {
  document.title = to.meta.title ? `${to.meta.title} - 零壹星` : '零壹星'
})

export default router
