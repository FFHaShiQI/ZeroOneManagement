<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { clearAuth } from '@/utils/auth'

const router = useRouter()
// 用户名
const name = ref('土豆雷')
// 小组信息弹窗是否显示
const dialogTableVisible = ref(false)
// 小组成员信息
const groupMembers = ref([
  { name: '土豆雷', position: '组长' },
  { name: '天坑', position: '信息官' },
  { name: 'Cat', position: '副组长' },
  { name: '蓝宝', position: '产品经理' },
  { name: '满末西', position: '技术官' },
  { name: 'serendipity', position: '技术官' },
  { name: '与或非', position: '技术官' },
  { name: 'W', position: '监督管' }
])


// 显示小组信息弹窗
const showGroupInfo = () => {
  dialogTableVisible.value = true
}

// 退出登录
const logout = () => {
  clearAuth()
  router.replace('/login')
}

</script>

<template>
  <div class="common-layout">
    <el-container>
      <!-- 顶部区域 -->
      <el-header>
        <span class="header-title">零壹星 · 赋能驾驶舱</span>
       <span class="header-user">
          <a href="javascript:void(0)"  @click="logout">
            【欢迎你,{{name}}】
            <el-icon>
              <SwitchButton />
            </el-icon> 退出登录  
          </a>
       </span>
      </el-header>

      <el-container>
        <!-- 侧边栏菜单 -->
        <el-aside width="200px">
          <el-scrollbar>
            <el-menu router>
              <el-menu-item index="/member/memberSelfInfo">
                <template #title>
                  <el-icon><User /></el-icon>个人信息
                </template>           
              </el-menu-item>

              <el-menu-item index="/member/groupInfo">
                <template #title>
                 <el-icon><HomeFilled /></el-icon>我的小组
                </template>                               
              </el-menu-item>

              <el-menu-item index="/member/leaveApply">
                <template #title>
                  <el-icon><Message /></el-icon>申请请假
                </template>                                       
              </el-menu-item>
            </el-menu>
          </el-scrollbar>
        </el-aside>

        <el-container>
          <!-- 主体区域 -->
          <el-main>
            <router-view></router-view>
          </el-main>
          <!-- 底部区域 -->
          <el-footer>
            <span class="footer-text">
              Powered by 
              <span class="footer-text-bold" @click="showGroupInfo">
                2026 零壹星球·哈基米小组
              </span>
            </span>
          </el-footer>
        </el-container>

      </el-container>

    </el-container>
  </div>

  <el-dialog v-model="dialogTableVisible" title="哈基米小组" width="600px" align-center class="group-dialog">
    <el-table :data="groupMembers"  stripe border size="default" style="width: 100%; border-radius: 12px; overflow: hidden;">
      <el-table-column property="name" label="昵称" align="center" min-width="120"/>
      <el-table-column property="position" label="职位" align="center" min-width="120" />
    </el-table>
  </el-dialog>
</template>

<style scoped>
/* 全局样式重置 */
.common-layout {
  height: 100vh;
}

.el-container {
  height: 100%;
}

/* ===== 顶部样式（绿色渐变，加大版） ===== */
.el-header {
  background: linear-gradient(135deg, #0f7b3a 0%, #2ecc71 100%);
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 32px; /* 水平内边距稍增，更舒展 */
  height: 72px; /* ===== 修改点1：高度从60px增至72px ===== */
  border-bottom: 1px solid rgba(255, 255, 255, 0.15);
  box-shadow: 0 2px 12px rgba(46, 204, 113, 0.25);
  position: relative;
  z-index: 10;
}

.header-title {
  font-size: 24px; /* ===== 修改点2：字号从20px增至24px ===== */
  font-weight: 600;
  letter-spacing: 0.5px;
  color: #fff;
}

.header-title::before {
  content: "✦ ";
  font-weight: 300;
  opacity: 0.8;
}

.header-user a {
  color: rgba(255, 255, 255, 0.85);
  text-decoration: none;
  font-size: 15px; /* 用户名字号略微调大 */
  display: flex;
  align-items: center;
  gap: 6px;
  transition: all 0.3s;
  padding: 8px 18px; /* 内边距稍增，视觉平衡 */
  border-radius: 24px;
  background: rgba(255, 255, 255, 0.08);
}

.header-user a:hover {
  color: #fff;
  background: rgba(255, 255, 255, 0.18);
}

.header-user .el-icon {
  font-size: 18px; /* 图标稍大 */
}

/* ===== 侧边栏样式（浅色） ===== */
.el-aside {
  background: #f8fafc;
  border-right: 1px solid #e4e7ed;
}

.el-menu {
  background: transparent;
  border-right: none;
}

/* 菜单项样式 */
.el-menu-item {
  color: #4a5a6e;
  font-size: 14px;
  height: 48px;
  line-height: 48px;
  padding: 0 20px;
  margin: 4px 8px;
  border-radius: 8px;
  transition: all 0.2s;
}

.el-menu-item:hover {
  color: #1f2937;
  background: #edf2f7;
}

.el-menu-item.is-active {
  color: #0f7b3a;
  background: #e8f5e9;
  font-weight: 500;
}

.el-menu-item .el-icon {
  color: #8896a8;
  margin-right: 10px;
  font-size: 18px;
}

.el-menu-item:hover .el-icon {
  color: #0f7b3a;
}

.el-menu-item.is-active .el-icon {
  color: #0f7b3a;
}

/* 主体样式 */
.el-main {
  background: #f0f2f5;
  padding: 20px;
  flex: 1;
  overflow-y: auto;
  min-width: 0;
}

/* 底部样式 */
.el-footer {
  background: #ffffff;
  border-top: 1px solid #e4e7ed;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50px;
  color: #909399;
  font-size: 13px;
  flex-shrink: 0;
}

.footer-text-bold {
  color: #0f7b3a;
  cursor: pointer;
  font-weight: 500;
}

.footer-text-bold:hover {
  color: #2ecc71;
}

/* 小组弹窗样式 */
.group-dialog .el-dialog {
  border-radius: 16px;
}

.group-dialog .el-dialog__header {
  padding: 20px 24px 12px 24px;
  border-bottom: 1px solid #f0f2f5;
}

.group-dialog .el-dialog__title {
  font-size: 18px;
  font-weight: 600;
  color: #1f2937;
}

.group-dialog .el-dialog__body {
  padding: 20px 24px 28px 24px;
}

.group-dialog .el-table thead th {
  background: #f8fafc !important;
  color: #1f2937;
  font-weight: 600;
  font-size: 14px;
  border-bottom: 2px solid #e4e7ed;
}

.group-dialog .el-table td {
  font-size: 14px;
  color: #2c3e50;
  padding: 12px 0;
}

.group-dialog .el-table--striped .el-table__body tr.el-table__row--striped td {
  background: #fafcff;
}

.group-dialog .el-table__body tr:hover > td {
  background: #eef3ff !important;
  transition: background 0.15s;
}

.group-dialog .el-table {
  border-radius: 12px;
  border: 1px solid #e9edf4;
}
</style>
