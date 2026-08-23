<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login } from '@/api/auth' 

const router = useRouter()

// 表单数据
const form = reactive({
    username: '',
    password: '',
    role: 'member' // 默认是学员
})

// 登录状态
const loading = ref(false)

// 切换角色（勾选学长登录）
const handleRoleChange = (val) => {
    form.role = val ? 'senior' : 'member'
}

// 登录方法
const handleLogin = async () => {
    if (!form.username || !form.password) {
        ElMessage.warning('请输入用户名和密码')
        return
    }

    loading.value = true
    try {
        const res = await login(form)
        
        // ✅ 1代表成功
        if (res.code === 1) {
            ElMessage.success('登录成功')
            
            // 【核心修改】：直接存储整个 data 对象
            localStorage.setItem('userData', JSON.stringify(res.data))
            
            // 【兼容旧代码】：为了不破坏之前写的“个人信息”和“小组信息”页面，顺手存一下 id 和 groupId
            if (res.data.userInfo) {
                localStorage.setItem('id', res.data.userInfo.id)
                localStorage.setItem('groupId', res.data.userInfo.groupId)
            }
            
            // 根据角色跳转
            if (res.data.role === 'senior') {
                router.push('/senior/membersManage')
            } else {
                router.push('/member/memberSelfInfo')
            }
        } else {
            ElMessage.error(res.msg || '账号或密码错误')
        }
    } catch (error) {
        ElMessage.error('登录发生异常，请稍后重试')
    } finally {
        loading.value = false
    }
}

// 重置表单
const handleReset = () => {
    form.username = ''
    form.password = ''
    form.role = 'member'
}
</script>

<template>
    <div class="login-page">
        <!-- 登录卡片 -->
        <div class="login-card">
            <h1 class="title">零壹星 · 赋能驾驶舱</h1>
            
            <div class="form-container">
                <!-- 用户名 -->
                <div class="form-row">
                    <label>用户名</label>
                    <el-input v-model="form.username" placeholder="请输入用户名" size="large" />
                </div>
                
                <!-- 密码 -->
                <div class="form-row">
                    <label>密码</label>
                    <el-input v-model="form.password" type="password" placeholder="请输入密码" show-password size="large" />
                </div>
                
                <!-- 操作按钮 -->
                <div class="btn-group">
                    <el-button type="primary" size="large" :loading="loading" @click="handleLogin">登 录</el-button>
                    <el-button size="large" @click="handleReset">重 置</el-button>
                </div>
                
                <!-- 底部操作区 -->
                <div class="footer-row">
                    <el-checkbox @change="handleRoleChange" label="学长登录" />
                    <a href="javascript:void(0)" class="register-link" @click="router.push('/register')">没有账号？立即注册</a>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
/* ② 美化背景：使用清爽绿意全屏渐变 */
.login-page {
    height: 100vh;
    width: 100vw;
    display: flex;
    justify-content: center;
    align-items: center;
    background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
}

/* ③ 美化卡片：增加半透明毛玻璃效果，提升质感 */
.login-card {
    width: 520px;
    background: rgba(255, 255, 255, 0.95); /* 半透明白 */
    backdrop-filter: blur(10px); /* 毛玻璃效果 */
    padding: 50px 60px;
    border-radius: 20px;
    box-shadow: 0 8px 32px 0 rgba(17, 153, 142, 0.3); /* 绿色柔和阴影 */
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

.form-container {
    width: 100%;
}

.form-row {
    display: flex;
    align-items: center;
    margin-bottom: 24px;
}

.form-row label {
    width: 70px;
    font-size: 16px;
    font-weight: 500;
    color: #555;
    text-align: right;
    margin-right: 15px;
}

.form-row .el-input {
    flex: 1;
}

/* ④ 按钮区居中，增加间距 */
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

/* ⑤ 底部行 */
.footer-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 30px;
}

.footer-row .el-checkbox {
    color: #666;
}

.register-link {
    color: #666;
    text-decoration: none;
    font-size: 14px;
    transition: color 0.3s;
}

.register-link:hover {
    color: #11998e; /* 渐变的主色 */
}
</style>