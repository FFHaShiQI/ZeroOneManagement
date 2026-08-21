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
        { path: 'groupManage', component: GroupManageView },
        { path: 'leavesManage', component: LeavesManageView },
        { path: 'membersManage', component: MembersManageView },
        { path: 'seniorSelfInfo', component: SeniorSelfInfoView }
      ]
    }

  ]
})

export default router
