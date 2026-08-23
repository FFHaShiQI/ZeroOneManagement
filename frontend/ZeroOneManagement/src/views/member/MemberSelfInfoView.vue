<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getMemberInfo, updateMemberInfo } from '@/api/member/member'
import { uploadFile } from '@/api/upload/upload'

const userInfo = ref({
    name: '', username: '', password: '', groupName: '', gender: '', age: '',
    image: '', selfIntro: '', number: '', pos: '',  
})
const editForm = ref({})

const getUserId = () => {
    const userDataStr = localStorage.getItem('userData')
    const userData = userDataStr ? JSON.parse(userDataStr) : null
    return userData?.userInfo?.id || localStorage.getItem('id')
}

onMounted(async () => {
    const userId = getUserId()
    if (userId) {
        const result = await getMemberInfo(userId)
        if (result.code === 1) {
            userInfo.value = result.data
        }
    } else {
        ElMessage.warning('未获取到用户ID，请重新登录')
    }
})

const genderOptions = [ { value: 1, label: '男' }, { value: 2, label: '女' } ]
const posOptions = [ { value: 1, label: '组长' }, { value: 2, label: '副组长' }, { value: 3, label: '产品经理' }, { value: 4, label: '信息官' }, { value: 5, label: '技术官' }, { value: 6, label: '监督官' } ]

const dialogVisible = ref(false)

const handleEdit = () => {
    editForm.value = { ...userInfo.value }
    dialogVisible.value = true
}

// 修改：上传成功直接赋值整个字符串
const handleAvatarChange = async (uploadFileObj) => {
    const file = uploadFileObj.raw
    if (file) {
        const formData = new FormData()
        formData.append('file', file)

        const uploadRes = await uploadFile(formData)
        if (uploadRes.code === 1) {
            editForm.value.image = uploadRes.data 
            ElMessage.success('头像上传成功')
        } else {
            ElMessage.error('头像上传失败')
        }
    }
}

// 修改：保存后重新查询接口
const handleSave = async () => {
    const payload = {
        id: editForm.value.id,
        name: editForm.value.name,
        password: editForm.value.password,
        gender: editForm.value.gender,
        age: editForm.value.age,
        image: editForm.value.image,
        selfIntro: editForm.value.selfIntro,
        number: editForm.value.number,
        pos: editForm.value.pos
    }

    const result = await updateMemberInfo(payload)
    
    if (result.code === 1) {
        
        const freshData = await getMemberInfo(getUserId())
        if (freshData.code === 1) {
            userInfo.value = freshData.data
        }

        dialogVisible.value = false
        ElMessage.success('个人信息修改成功！')
    } else {
        ElMessage.error(result.msg || '保存失败，请重试')
    }
}
</script>

<template>
    <div class="self-info-page">
        <h2 class="page-title">个人信息</h2>

        <!-- 页面主体信息展示区 -->
        <div class="info-layout">
            <!-- 左侧：大头像 -->
           <div class="avatar-area">
                <div class="avatar-circle">
                    <!-- 如果后端 image 有值，则显示图片 -->
                    <img v-if="userInfo.image" :src="userInfo.image" alt="头像" class="avatar-img" />
                    <!-- 如果没有值（null 或空字符串），则显示默认文字 -->
                    <span v-else>头像</span>
                </div>
            </div>

            <!-- 中间：基础信息 -->
            <div class="base-info">
                <div class="info-row">
                    <span class="label">昵称：</span>
                    <span class="value">{{ userInfo.name }}</span>
            </div>
                <div class="info-row">
                    <span class="label">性别：</span>
                    <span class="value">
                        <span v-if="userInfo.gender === 1">男</span>
                        <span v-else-if="userInfo.gender === 2">女</span>
                        <span v-else>{{ userInfo.gender }}</span>
                    </span>
                </div>
                <div class="info-row">
                    <span class="label">年龄：</span>
                    <span class="value">{{ userInfo.age }}</span>
                </div>
               <div class="info-row">
                    <span class="label">职位：</span>
                    <span class="value">
                        <span v-if="userInfo.pos === 1">组长</span>
                        <span v-else-if="userInfo.pos === 2">副组长</span>
                        <span v-else-if="userInfo.pos === 3">产品经理</span>
                        <span v-else-if="userInfo.pos === 4">信息官</span>
                        <span v-else-if="userInfo.pos === 5">技术官</span>
                        <span v-else-if="userInfo.pos === 6">监督官</span>
                        <span v-else>{{ userInfo.pos }}</span>
                    </span>
                </div>
                <div class="info-row">
                    <span class="label">小组：</span>
                    <span class="value">{{ userInfo.groupName }}</span> 
                </div>
                <div class="info-row">
                    <span class="label">联系方式：</span>
                    <span class="value">{{ userInfo.number }}</span>
                </div>
            </div>

            <!-- 右侧：自我介绍 -->
            <div class="intro-area">
                <h3 class="intro-title">自我介绍：</h3>
                <div class="intro-box">
                    {{ userInfo.selfIntro }}
                </div>
            </div>
        </div>

        <!-- 右下角修改按钮 -->
        <div class="footer-action">
            <el-button class="edit-btn" @click="handleEdit">修改信息</el-button>
        </div>

        <!-- 修改信息弹窗 -->
        <el-dialog v-model="dialogVisible" title="修改信息" width="800px" center>
            <div class="edit-dialog-content">

                <!-- 左侧：头像上传 -->
               <div class="left-avatar-area">
                    <el-upload class="avatar-uploader" action="#" :show-file-list="false" :auto-upload="false"
                        :on-change="handleAvatarChange">
                        <div class="avatar-circle-upload">
                            <!-- 核心：判断 editForm.image 是否有值，有值显示图片，没值显示文字 -->
                           <img v-if="editForm.image" :src="editForm.image" class="avatar-upload-img" />
                            <span v-else>上传头像</span>
                        </div>
                    </el-upload>
                </div>

                <!-- 右侧：两列表单 -->
                <div class="right-form-area">
                    <el-form :model="editForm" label-position="top">
                        <el-row :gutter="20">
                            <!-- 第一行：昵称 / 用户名(只读) -->
                            <el-col :span="12">
                                <el-form-item label="昵称">
                                    <el-input v-model="editForm.name" />
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="用户名">
                                    <!-- 用户名设置为 disabled，不可修改 -->
                                    <el-input v-model="editForm.username" disabled />
                                </el-form-item>
                            </el-col>

                            <!-- 第二行：联系方式 / 密码 -->
                            <el-col :span="12">
                                <el-form-item label="联系方式">
                                    <el-input v-model="editForm.number" />
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="密码">
                                    <el-input v-model="editForm.password" type="password" show-password />
                                </el-form-item>
                            </el-col>

                            <!-- 第三行：年龄 / 小组下拉 / 职位下拉 -->
                            <el-col :span="8">
                                <el-form-item label="年龄">
                                    <el-input v-model="editForm.age" />
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="性别">
                                    <el-select v-model="editForm.gender" placeholder="请选择" style="width: 100%">
                                        <el-option v-for="item in genderOptions" :key="item.value" :label="item.label"
                                            :value="item.value" />
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="职位">
                                    <el-select v-model="editForm.pos" placeholder="请选择" style="width: 100%">
                                        <el-option v-for="item in posOptions" :key="item.value" :label="item.label"
                                            :value="item.value" />
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>

                        <!-- 底部：自我介绍填写 -->
                        <el-form-item label="自我介绍">
                            <el-input v-model="editForm.selfIntro" type="textarea" :rows="7" resize="none"
                                placeholder="(自我介绍填写)" />
                        </el-form-item>
                    </el-form>
                </div>
            </div>

            <!-- 弹窗右下角提交按钮 -->
            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="handleSave">提交</el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>

<style scoped>
/* 撑满父级，还原淡黄色背景 */
.self-info-page {
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
    margin-bottom: 50px;
}

.info-layout {
    display: flex;
    gap: 60px;
    align-items: flex-start;
}

/* 左侧大头像 */
.avatar-area {
    margin-top: 30px;
}

.avatar-circle {
    width: 250px;
    height: 250px;
    border-radius: 50%;
    border: 1px solid #999;
    background-color: #f9f9f9;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 20px;
    color: #666;
    overflow: hidden; 
}

/* 新增图片样式 */
.avatar-img {
    width: 100%;
    height: 100%;
    object-fit: cover; /* 保持比例并填满圆圈 */
    display: block;
}

/* 中间基础信息 */
.base-info {
    display: flex;
    flex-direction: column;
    gap: 25px;
    margin-top: 40px;
    width: 220px;
}

.info-row {
    display: flex;
    align-items: center;
}

.label {
    font-size: 18px;
    font-weight: bold;
    width: 90px;
}

.value {
    font-size: 18px;
    color: #333;
}

/* 右侧自我介绍 */
.intro-area {
    margin-top: 40px;
    width: 400px;
}

.intro-title {
    font-size: 18px;
    font-weight: bold;
    margin-bottom: 15px;
}

.intro-box {
    width: 100%;
    height: 250px;
    background: white;
    border: 2px solid #ccc;
    border-radius: 8px;
    padding: 20px;
    font-size: 16px;
    color: #333;
}

/* 页面右下角按钮 */
.footer-action {
    display: flex;
    justify-content: flex-end;
    margin-top: 50px;
}

.edit-btn {
    background: white;
    border: none;
    padding: 15px 35px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    border-radius: 4px;
    font-size: 16px;
}

.edit-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 8px rgba(0, 0, 0, 0.15);
}

/* 弹窗内容布局 */
.edit-dialog-content {
    display: flex;
    gap: 40px;
}

.left-avatar-area {
    margin-top: 10px;
}

.avatar-uploader :deep(.el-upload) {
    border: 1px solid #ccc;
    border-radius: 50%;
    cursor: pointer;
    overflow: hidden;
    width: 180px;
    height: 180px;
    display: flex;
    justify-content: center;
    align-items: center;
    background: #f9f9f9;
}

.right-form-area {
    flex: 1;
}

.dialog-footer {
    display: flex;
    justify-content: flex-end;
}

/* 覆盖原有样式，确保对话框表单正确展示 */
:deep(.el-dialog__body) {
    padding: 30px 40px;
}
/* 弹窗内头像预览图片样式 */
.avatar-upload-img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    display: block;
    border-radius: 50%;
}
</style>