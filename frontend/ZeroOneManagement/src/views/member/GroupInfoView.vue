<script setup>
import { ref, onMounted } from 'vue'
import { getGroupInfo } from '@/api/member/member'

// 小组信息数据
const groupInfo = ref({
    name: '',
    groupSize: 0,
    seniorName: '',
    language: '',
    clockLink: '',
    members: []
})

// 小组类型转换
const languageMap = { 0: 'C语言', 1: 'C++', 2: 'Java' }
// 性别转换
const genderMap = { 1: '男', 2: '女' }
// 职位转换（对应接口文档 1组长，2副组长...）
const posMap = { 1: '组长', 2: '副组长', 3: '产品经理', 4: '信息官', 5: '技术官', 6: '监督官' }

const getGroupId = () => {
    const userDataStr = localStorage.getItem('userData')
    const userData = userDataStr ? JSON.parse(userDataStr) : null
    return userData?.userInfo?.groupId || localStorage.getItem('groupId')
}

// 获取小组信息的主函数
const fetchGroupInfo = async () => {
    const groupId = getGroupId()
    
    // 如果拿不到 groupId（比如未登录），直接返回，避免报错
    if (!groupId) return

    const result = await getGroupInfo(groupId)
    if (result.code === 1) {
        groupInfo.value = result.data
    }
}

onMounted(() => {
    fetchGroupInfo()
})
</script>

<template>
    <div class="group-info-page">
        <h2 class="page-title">小组信息</h2>

        <!-- 上方：小组概览卡片 -->
        <div class="group-card">
            <div class="card-item">
                <span class="label">组名：</span>
                <span class="value">{{ groupInfo.name }}</span>
            </div>
            <div class="card-item">
                <span class="label">小组人数：</span>
                <span class="value">{{ groupInfo.groupSize }}</span>
            </div>
            <div class="card-item">
                <span class="label">带班小学长：</span>
                <span class="value">{{ groupInfo.seniorName }}</span>
            </div>
            <div class="card-item">
                <span class="label">小组类型：</span>
                <span class="value">{{ languageMap[groupInfo.language] || groupInfo.language }}</span>
            </div>
            <div class="card-item">
                <span class="label">打卡表链接：</span>
                <a :href="groupInfo.clockLink" target="_blank" class="value link">{{ groupInfo.clockLink || '暂无' }}</a>
            </div>
        </div>

        <!-- 下方：小组成员列表 -->
        <div class="member-table">
            <el-table :data="groupInfo.members" stripe border style="width: 100%">
                <el-table-column prop="name" label="昵称" align="center" min-width="120" />
                
                <el-table-column label="职位" align="center" min-width="120">
                    <template #default="scope">
                        {{ posMap[scope.row.pos] || scope.row.pos }}
                    </template>
                </el-table-column>

                <el-table-column label="性别" align="center" min-width="100">
                    <template #default="scope">
                        {{ genderMap[scope.row.gender] || scope.row.gender }}
                    </template>
                </el-table-column>

                <el-table-column prop="age" label="年龄" align="center" min-width="100" />

                <el-table-column label="头像" align="center" min-width="100">
                    <template #default="scope">
                        <el-avatar :size="40" :src="scope.row.image" style="background-color: #f0f2f5;">
                            <span v-if="!scope.row.image">无</span>
                        </el-avatar>
                    </template>
                </el-table-column>

                <el-table-column prop="number" label="联系方式" align="center" min-width="150" />
            </el-table>
        </div>
    </div>
</template>

<style scoped>
/* 撑满父级，还原淡黄色背景 */
.group-info-page {
    background-color: #fdfcf0;
    padding: 40px 60px;
    height: 100%; 
    box-sizing: border-box; 
    position: relative;
    font-family: 'Microsoft YaHei', sans-serif;
    overflow-y: auto; 
}

.page-title {
    font-size: 32px;
    font-weight: bold;
    margin-bottom: 30px;
}

/* 上方概览卡片样式 */
.group-card {
    display: flex;
    flex-wrap: wrap;
    gap: 20px 60px;
    background: white;
    border: 1px solid #ccc;
    border-radius: 8px;
    padding: 30px 40px;
    margin-bottom: 30px;
}

.card-item {
    display: flex;
    align-items: center;
    font-size: 18px;
}

.card-item .label {
    font-weight: bold;
    margin-right: 10px;
    color: #333;
}

.card-item .value {
    color: #555;
}

.card-item .link {
    color: #0f7b3a;
    text-decoration: none;
}

.card-item .link:hover {
    text-decoration: underline;
}

/* 表格样式优化 */
.member-table {
    background: white;
    border-radius: 8px;
    padding: 20px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

:deep(.el-table th) {
    background-color: #f8fafc !important;
    font-weight: 600;
    color: #333;
}
</style>