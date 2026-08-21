import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      // 产品说明:仪表盘未完成时默认进入组员管理
      redirect: '/senior/members'
    },
    // 学长操作界面 - 组员管理
    {
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
    }
  ]
})

router.afterEach((to) => {
  document.title = to.meta.title ? `${to.meta.title} - 零壹星` : '零壹星'
})

export default router
