<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
// 引入注册和获取小组的接口
import { register, getAllGroups } from '@/api/auth'

const router = useRouter()

// 注册模式：默认学员(false)，学长(true)
const isSenior = ref(false)

// 表单数据
const form = reactive({
    username: '',
    password: '',
    confirmPassword: '',
    groupId: '', // 学员必填
    inviteCode: '' // 学长必填
})

// 小组下拉框数据（初始为空数组）
const groupOptions = ref([])

// 页面加载时获取真实的小组列表
onMounted(async () => {
    try {
        const res = await getAllGroups()
        if (res.code === 1) {
            // 后端返回的是 [{id:1, name:"哈基米"}, ...]，需要转换成下拉框格式
            groupOptions.value = res.data.map(item => ({
                value: item.id,
                label: item.name
            }))
        } else {
            ElMessage.error(res.msg || '获取小组列表失败')
        }
    } catch (error) {
        console.error('获取小组列表出错:', error)
    }
})

// 切换注册模式
const switchMode = (type) => {
    isSenior.value = type === 'senior'
}

// 注册
const handleRegister = async () => {
    // ... (保持原有的校验逻辑不变)
    if (!form.username || !form.password || !form.confirmPassword) {
        ElMessage.warning('请填写完整的账号信息')
        return
    }
    
    if (form.password !== form.confirmPassword) {
        ElMessage.warning('两次输入的密码不一致')
        return
    }

    if (!isSenior.value && !form.groupId) {
        ElMessage.warning('请选择小组')
        return
    }
    if (isSenior.value && !form.inviteCode) {
        ElMessage.warning('请输入邀请码')
        return
    }

    // 组装参数（根据后端接口文档）
    const payload = {
        username: form.username,
        password: form.password,
        option: isSenior.value, // true为学长，false为学员
        inviteCode: isSenior.value ? form.inviteCode : undefined,
        groupId: isSenior.value ? undefined : form.groupId
    }

    try {
        const res = await register(payload)
        if (res.code === 1) {
            ElMessage.success('注册成功，请登录')
            router.push('/login')
        } else {
            ElMessage.error(res.msg || '注册失败')
        }
    } catch (error) {
        ElMessage.error('注册发生异常')
    }
}

// 重置
const handleReset = () => {
    form.username = ''
    form.password = ''
    form.confirmPassword = ''
    form.groupId = ''
    form.inviteCode = ''
}

// 返回登录
const handleBack = () => {
    router.push('/login')
}
</script>

<template>
    <div class="register-page">
        <div class="register-card">
            <h1 class="title">注册账号</h1>

            <div class="form-container">
                <div class="form-row">
                    <label>用户名</label>
                    <el-input v-model="form.username" placeholder="请输入用户名" size="large" />
                </div>
                
                <div class="form-row">
                    <label>密码</label>
                    <el-input v-model="form.password" type="password" placeholder="请输入密码" show-password size="large" />
                </div>

                <div class="form-row">
                    <label>确认密码</label>
                    <el-input v-model="form.confirmPassword" type="password" placeholder="请再次输入密码" show-password size="large" />
                </div>

                <!-- 动态区域：学员选小组，学长填邀请码 -->
                <div class="form-row" v-if="!isSenior">
                    <label>选择小组</label>
                    <el-select v-model="form.groupId" placeholder="请选择小组" size="large" style="flex: 1">
                        <el-option v-for="item in groupOptions" :key="item.value" :label="item.label" :value="item.value" />
                    </el-select>
                </div>
                
                <div class="form-row" v-else>
                    <label>邀请码</label>
                     <el-input v-model="form.inviteCode" type="password" show-password placeholder="请输入学长邀请码" size="large" />
                </div>

                <!-- 按钮区 -->
                <div class="btn-group">
                    <el-button type="primary" size="large" @click="handleRegister">注 册</el-button>
                    <el-button size="large" @click="handleReset">重 置</el-button>
                </div>
                
                <!-- 底部操作区 -->
                <div class="footer-row">
                    <button class="mode-btn" @click="switchMode(isSenior ? 'member' : 'senior')">
                        {{ isSenior ? '学员注册' : '学长注册' }}
                    </button>
                    <button class="back-btn" @click="handleBack">返回登录</button>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
/* 背景与登录页保持一致，美化 */
.register-page {
    height: 100vh;
    width: 100vw;
    display: flex;
    justify-content: center;
    align-items: center;
    background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
}

.register-card {
    width: 550px;
    background: rgba(255, 255, 255, 0.95); /* 半透明白 */
    backdrop-filter: blur(10px); /* 毛玻璃效果 */
    padding: 50px 60px;
    border-radius: 20px;
    box-shadow: 0 8px 32px 0 rgba(17, 153, 142, 0.3);
    border: 1px solid rgba(255, 255, 255, 0.5);
}

.title {
    text-align: center;
    font-size: 30px;
    font-weight: bold;
    color: #0f7b3a; /* 呼应主题绿 */
    margin-bottom: 40px;
    letter-spacing: 2px;
}

.form-row {
    display: flex;
    align-items: center;
    margin-bottom: 24px;
}

.form-row .el-input__password {
    display: none;
}

.form-row label {
    width: 90px;
    font-size: 16px;
    font-weight: 500;
    color: #555;
    text-align: right;
    margin-right: 15px;
}

.form-row .el-input, .form-row .el-select {
    flex: 1;
}

.btn-group {
    display: flex;
    justify-content: center;
    gap: 20px;
    margin-top: 40px;
}

.btn-group .el-button {
    width: 140px;
    font-size: 16px;
    border-radius: 8px;
    letter-spacing: 2px;
}

/* 底部：学长注册按钮和返回登录按钮 */
.footer-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 30px;
}

.mode-btn {
    background: none;
    border: none;
    color: #666;
    font-size: 14px;
    cursor: pointer;
    padding: 0;
}

.mode-btn:hover {
    color: #11998e;
}

.back-btn {
    background: none;
    border: none;
    color: #666;
    font-size: 14px;
    cursor: pointer;
    padding: 0;
}

.back-btn:hover {
    color: #11998e;
}
</style>