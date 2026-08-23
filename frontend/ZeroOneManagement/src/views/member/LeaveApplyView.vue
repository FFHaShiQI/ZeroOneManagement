<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getLeaveList, addLeaveApply } from '@/api/member/member'
import { uploadFile } from '@/api/upload/upload'

// 请假列表数据
const leaveList = ref([])

// 请假类型映射 (0病假，1事假，2其它)
const typeMap = { 0: '病假', 1: '事假', 2: '其它' }
// 审批状态映射 (0待审核，1审核通过，2审核不通过)
const statusMap = { 0: '待审核', 1: '审核通过', 2: '审核不通过' }

const getUserId = () => {
    const userDataStr = localStorage.getItem('userData')
    const userData = userDataStr ? JSON.parse(userDataStr) : null
    return userData?.userInfo?.id || localStorage.getItem('id')
}

// 弹窗相关
const dialogVisible = ref(false)
const isSubmitting = ref(false)

// 申请表单
const form = ref({
    memberId: getUserId(), 
    type: 0,
    reason: '',
    image: '',
    start: '',
    end: ''
})

// 查询列表
const fetchLeaveList = async () => {
    const id = getUserId() 
    if (!id) return
    
    const result = await getLeaveList(id)
    if (result.code === 1) {
        leaveList.value = result.data || [] 
    }
}

// 页面加载时查询
onMounted(() => {
    fetchLeaveList()
})

// 打开申请弹窗并重置表单
const handleOpenDialog = () => {
    form.value = {
        memberId: getUserId(), 
        type: 0,
        reason: '',
        image: '',
        start: '',
        end: ''
    }
    dialogVisible.value = true
}

// 上传图片（复用之前的逻辑）
const handleImageChange = async (uploadFileObj) => {
    const file = uploadFileObj.raw
    if (file) {
        const formData = new FormData()
        formData.append('file', file)
        const uploadRes = await uploadFile(formData)
        if (uploadRes.code === 1) {
            form.value.image = uploadRes.data
            ElMessage.success('图片上传成功')
        } else {
            ElMessage.error('图片上传失败')
        }
    }
}

// 提交请假
const handleSubmit = async () => {
    // 基础校验
    if (!form.value.reason) return ElMessage.warning('请填写请假理由')
    if (!form.value.start || !form.value.end) return ElMessage.warning('请选择请假起止时间')
    if (!form.value.image) return ElMessage.warning('请上传佐证图片')
    if (form.value.start > form.value.end) return ElMessage.warning('结束时间不能早于开始时间')

    isSubmitting.value = true
    try {
        // 组装参数（后端需要的是 YYYY-MM-DD 格式）
        const payload = {
            ...form.value,
            start: form.value.start.split('T')[0], // 如果是时间对象转字符串，去除时分秒
            end: form.value.end.split('T')[0]
        }

        const result = await addLeaveApply(payload)
        if (result.code === 1) {
            ElMessage.success('请假申请提交成功！')
            dialogVisible.value = false
            fetchLeaveList() // 重新拉取列表
        } else {
            ElMessage.error(result.msg || '提交失败')
        }
    } catch (error) {
        ElMessage.error('系统异常，请稍后重试')
    } finally {
        isSubmitting.value = false
    }
}

// 格式化时间，只取日期部分
const formatDate = (dateStr) => {
    if (!dateStr) return ''
    return dateStr.replace('T', ' ') // 将 2026-08-22T00:00:00 变成 2026-08-22 00:00:00
}
</script>

<template>
    <div class="leave-apply-page">
        <div class="header-area">
            <h2 class="page-title">请假汇总</h2>
            <el-button type="primary" class="apply-btn" @click="handleOpenDialog">申请</el-button>
        </div>

        <!-- 请假汇总表格 -->
        <div class="table-container">
            <el-table :data="leaveList" stripe border style="width: 100%">
                <el-table-column label="类型" align="center" min-width="100">
                    <template #default="scope">{{ typeMap[scope.row.type] || scope.row.type }}</template>
                </el-table-column>
                <el-table-column prop="reason" label="请假理由" align="center" min-width="200" show-overflow-tooltip />
                
                <el-table-column label="佐证图片" align="center" min-width="120">
                    <template #default="scope">
                        <el-image 
                            v-if="scope.row.image" 
                            :src="scope.row.image" 
                            :preview-src-list="[scope.row.image]"
                            :preview-teleported="true" 
                            fit="cover" 
                            style="width: 50px; height: 50px; border-radius: 4px;"
                        />
                        <span v-else>无</span>
                    </template>
                </el-table-column>

                <el-table-column prop="start" label="请假开始时间" align="center" min-width="110" />
                <el-table-column prop="end" label="请假结束时间" align="center" min-width="110" />
               <el-table-column label="申请时间" align="center" min-width="180">
                    <template #default="scope">
                        {{ formatDate(scope.row.createTime) }}
                    </template>
                </el-table-column>

                <el-table-column label="审批状态" align="center" min-width="120">
                    <template #default="scope">
                        <el-tag :type="scope.row.status === 1 ? 'success' : (scope.row.status === 2 ? 'danger' : 'warning')">
                            {{ statusMap[scope.row.status] || scope.row.status }}
                        </el-tag>
                    </template>
                </el-table-column>

               <el-table-column prop="seniorName" label="审批人" align="center" min-width="100" />
                
                <el-table-column prop="feedback" label="审批反馈" align="center" min-width="100" show-overflow-tooltip />
            </el-table>
        </div>

        <!-- 申请请假弹窗 (假条界面) -->
        <el-dialog v-model="dialogVisible" title="假条界面" width="600px" center>
            <el-form label-position="top">
                <el-row :gutter="20">
                    <el-col :span="8">
                        <el-form-item label="请假类型">
                            <el-select v-model="form.type" placeholder="请选择" style="width: 100%">
                                <el-option label="病假" :value="0" />
                                <el-option label="事假" :value="1" />
                                <el-option label="其它" :value="2" />
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="开始日期">
                            <el-date-picker 
                                v-model="form.start" 
                                type="date" 
                                placeholder="选择日期" 
                                value-format="YYYY-MM-DD"
                                style="width: 100%" 
                            />
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="结束日期">
                            <el-date-picker 
                                v-model="form.end" 
                                type="date" 
                                placeholder="选择日期" 
                                value-format="YYYY-MM-DD"
                                style="width: 100%" 
                            />
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-form-item label="图片上传">
                    <el-upload class="image-uploader" action="#" :show-file-list="false" :auto-upload="false" :on-change="handleImageChange">
                        <div v-if="form.image" class="preview-box">
                            <img :src="form.image" alt="预览" />
                        </div>
                        <div v-else class="upload-box">
                            <span>点击上传图片</span>
                        </div>
                    </el-upload>
                </el-form-item>

                <el-form-item label="理由输入">
                    <el-input v-model="form.reason" type="textarea" :rows="4" placeholder="请输入请假理由" />
                </el-form-item>
            </el-form>

            <template #footer>
                <div class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" :loading="isSubmitting" @click="handleSubmit">确认</el-button>
                </div>
            </template>
        </el-dialog>
    </div>
</template>

<style scoped>
/* 撑满父级 */
.leave-apply-page {
    height: 100%;
    box-sizing: border-box;
    background-color: #fdfcf0;
    padding: 40px;
    overflow-y: auto;
}

.header-area {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 30px;
}

.page-title {
    font-size: 32px;
    font-weight: bold;
    margin: 0;
}

.table-container {
    background: white;
    border-radius: 8px;
    padding: 20px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

/* 图片上传框样式 */
.upload-box, .preview-box {
    width: 120px;
    height: 120px;
    border: 1px dashed #d9d9d9;
    border-radius: 8px;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
    overflow: hidden;
    background: #fafafa;
}

.upload-box span {
    color: #888;
    font-size: 14px;
}

.preview-box img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

:deep(.el-table th) {
    background-color: #f8fafc !important;
    font-weight: 600;
    color: #333;
}
</style>