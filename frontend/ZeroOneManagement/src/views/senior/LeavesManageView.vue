<script setup>
import { reactive, ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { pageQueryLeaves, auditLeave, deleteLeave } from '@/api/senior/leaves'

// 请假类型(TODO: 与后端确认枚举,暂按 1事假 2病假 3其他)
const TYPE_OPTIONS = [
  { label: '事假', value: 1 },
  { label: '病假', value: 2 },
  { label: '其他', value: 3 }
]
// 审核状态: 0待审批 1同意 2驳回
const STATUS_OPTIONS = [
  { label: '待审批', value: 0 },
  { label: '审核成功', value: 1 },
  { label: '已驳回', value: 2 }
]

function typeLabel(value) {
  return TYPE_OPTIONS.find((opt) => opt.value === value)?.label ?? '-'
}
function statusLabel(value) {
  return STATUS_OPTIONS.find((opt) => opt.value === value)?.label ?? '-'
}
function statusTagType(value) {
  return { 0: 'warning', 1: 'success', 2: 'danger' }[value] ?? 'info'
}

// ---------- 查询区 ----------
const queryParams = reactive({
  name: '',
  type: null,
  status: null,
  start: '',
  end: '',
  page: 1,
  pageSize: 5 // 原型默认每页 5 条
})

const tableData = ref([])
const total = ref(0)
const loading = ref(false)

async function loadList() {
  loading.value = true
  try {
    const res = await pageQueryLeaves(queryParams)
    if (res.code === 1) {
      const rows = res.data?.rows || []
      // 原型要求:默认待审批置顶(当前页内排序,分页由后端负责)
      tableData.value = [...rows].sort((a, b) => (a.status ?? 99) - (b.status ?? 99))
      total.value = res.data?.total || 0
    } else {
      ElMessage.error(res.msg || '查询失败')
    }
  } finally {
    loading.value = false
  }
}

function handleQuery() {
  queryParams.page = 1
  loadList()
}

function handleReset() {
  queryParams.name = ''
  queryParams.type = null
  queryParams.status = null
  queryParams.start = ''
  queryParams.end = ''
  queryParams.page = 1
  loadList()
}

function handleSizeChange(size) {
  queryParams.pageSize = size
  queryParams.page = 1
  loadList()
}

// ---------- 审批弹窗 ----------
const auditVisible = ref(false)
const auditFormRef = ref(null)
const submitting = ref(false)
const auditForm = reactive({
  id: null,
  name: '',
  status: null, // 1同意 2驳回
  feedback: ''
})

const auditRules = {
  feedback: [{ required: true, message: '请输入审核意见', trigger: 'blur' }]
}

function openAuditDialog(row) {
  Object.assign(auditForm, { id: row.id, name: row.name ?? row.memberName ?? '', status: null, feedback: '' })
  auditVisible.value = true
}

async function submitAudit(status) {
  auditForm.status = status
  try {
    await auditFormRef.value.validate()
  } catch {
    return
  }
  submitting.value = true
  try {
    const res = await auditLeave({
      id: auditForm.id,
      status: auditForm.status,
      feedback: auditForm.feedback
    })
    if (res.code === 1) {
      ElMessage.success(status === 1 ? '已同意' : '已驳回')
      auditVisible.value = false
      loadList()
    } else {
      ElMessage.error(res.msg || '操作失败')
    }
  } finally {
    submitting.value = false
  }
}

// ---------- 删除 ----------
async function handleRowDelete(row) {
  try {
    await ElMessageBox.confirm(`确定删除该条请假记录吗?`, '提示', {
      type: 'warning'
    })
  } catch {
    return
  }
  const res = await deleteLeave(row.id)
  if (res.code === 1) {
    ElMessage.success('删除成功')
    loadList()
  } else {
    ElMessage.error(res.msg || '删除失败')
  }
}

onMounted(loadList)
</script>

<template>
  <div class="leave-audit">
    <!-- 查询区 -->
    <el-card shadow="never" class="page-card">
      <el-form inline :model="queryParams" @submit.prevent="handleQuery">
        <el-form-item label="学员查询">
          <el-input
            v-model="queryParams.name"
            placeholder="学员昵称"
            clearable
            style="width: 140px"
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="请假类型">
          <el-select v-model="queryParams.type" placeholder="请选择" clearable style="width: 110px">
            <el-option v-for="opt in TYPE_OPTIONS" :key="opt.value" :label="opt.label" :value="opt.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="审批状态">
          <el-select v-model="queryParams.status" placeholder="请选择" clearable style="width: 110px">
            <el-option v-for="opt in STATUS_OPTIONS" :key="opt.value" :label="opt.label" :value="opt.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="开始日期">
          <el-date-picker
            v-model="queryParams.start"
            type="date"
            placeholder="请选择日期"
            value-format="YYYY-MM-DD"
            style="width: 150px"
          />
        </el-form-item>
        <el-form-item label="结束日期">
          <el-date-picker
            v-model="queryParams.end"
            type="date"
            placeholder="请选择日期"
            value-format="YYYY-MM-DD"
            style="width: 150px"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 表格 -->
    <el-card shadow="never" class="page-card">
      <el-table v-loading="loading" :data="tableData" border>
        <el-table-column label="学员" min-width="90" show-overflow-tooltip>
          <template #default="{ row }">{{ row.name ?? row.memberName ?? '-' }}</template>
        </el-table-column>
        <el-table-column label="所属小组" min-width="100" show-overflow-tooltip>
          <template #default="{ row }">{{ row.groupName ?? '-' }}</template>
        </el-table-column>
        <el-table-column label="请假类型" width="90" align="center">
          <template #default="{ row }">{{ typeLabel(row.type) }}</template>
        </el-table-column>
        <el-table-column label="理由" min-width="120" show-overflow-tooltip>
          <template #default="{ row }">{{ row.reason ?? '-' }}</template>
        </el-table-column>
        <el-table-column label="佐证图片" min-width="90" align="center">
          <template #default="{ row }">
            <el-link v-if="row.image" type="primary" :href="row.image" target="_blank">查看</el-link>
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column label="假期开始时间" min-width="120">
          <template #default="{ row }">{{ row.startTime ?? '-' }}</template>
        </el-table-column>
        <el-table-column label="请假结束时间" min-width="120">
          <template #default="{ row }">{{ row.endTime ?? '-' }}</template>
        </el-table-column>
        <el-table-column label="审核状态" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="statusTagType(row.status)">{{ statusLabel(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="审核人" min-width="100" show-overflow-tooltip>
          <template #default="{ row }">{{ row.reviewer ?? '-' }}</template>
        </el-table-column>
        <el-table-column label="反馈信息" min-width="120" show-overflow-tooltip>
          <template #default="{ row }">{{ row.feedback ?? '-' }}</template>
        </el-table-column>
        <el-table-column label="请求创建时间" min-width="150" show-overflow-tooltip>
          <template #default="{ row }">{{ row.createTime ?? '-' }}</template>
        </el-table-column>
        <el-table-column label="操作栏" width="140" align="center" fixed="right">
          <!-- 原型:仅待审批记录显示审批/删除 -->
          <template #default="{ row }">
            <template v-if="row.status === 0">
              <el-button link type="primary" @click="openAuditDialog(row)">审批</el-button>
              <el-button link type="danger" @click="handleRowDelete(row)">删除</el-button>
            </template>
            <span v-else>-</span>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="page-pagination">
        <el-pagination
          v-model:current-page="queryParams.page"
          v-model:page-size="queryParams.pageSize"
          :total="total"
          :page-sizes="[5, 10, 20]"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
        />
      </div>
    </el-card>

    <!-- 审批弹窗 -->
    <el-dialog v-model="auditVisible" title="审批" width="480px" destroy-on-close>
      <el-form ref="auditFormRef" :model="auditForm" :rules="auditRules" label-width="90px">
        <el-form-item label="学员">
          <span>{{ auditForm.name }}</span>
        </el-form-item>
        <el-form-item label="反馈信息" prop="feedback">
          <el-input
            v-model="auditForm.feedback"
            type="textarea"
            :rows="3"
            placeholder="请输入审核意见"
            maxlength="200"
            show-word-limit
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="auditVisible = false">取消</el-button>
        <el-button type="danger" :loading="submitting" @click="submitAudit(2)">驳回</el-button>
        <el-button type="success" :loading="submitting" @click="submitAudit(1)">同意</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.leave-audit {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.page-pagination {
  display: flex;
  justify-content: flex-end;
  margin-top: 16px;
}
</style>
