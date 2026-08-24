<script setup>
import { reactive, ref, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { pageQueryLeaves, auditLeave, deleteLeave } from '@/api/senior/leaves'
import { LEAVE_TYPE_OPTIONS, LEAVE_STATUS_OPTIONS, optionLabel } from '@/constants/senior'
import { usePageList } from '@/composables/usePageList'

// 当前登录学长 id(审核时记录审核人)
function getCurrentSeniorId() {
  return Number(localStorage.getItem('id'))
}

function typeLabel(value) {
  return optionLabel(LEAVE_TYPE_OPTIONS, value)
}
function statusLabel(value) {
  return optionLabel(LEAVE_STATUS_OPTIONS, value)
}
function statusTagType(value) {
  return { 0: 'warning', 1: 'success', 2: 'danger' }[value] ?? 'info'
}

const { queryParams, tableData, total, loading, loadList, handleQuery, handleReset, handleSizeChange, handleCurrentChange } = usePageList({
  fetchFn: pageQueryLeaves,
  initialParams: { name: '', type: null, status: null, dateRange: [] },
  defaultPageSize: 5, // 原型默认每页 5 条
  // 日期范围选择器拆分为后端需要的 start/end
  buildParams: ({ dateRange, ...rest }) => ({
    ...rest,
    start: dateRange?.[0] || '',
    end: dateRange?.[1] || ''
  }),
  // 原型要求:默认待审批置顶(仅当前页内排序,跨页一致性需由后端排序支持)
  transformRows: (rows) => [...rows].sort((a, b) => (a.status ?? 99) - (b.status ?? 99))
})

// ---------- 审批弹窗 ----------
const auditVisible = ref(false)
const auditFormRef = ref(null)
const submitting = ref(false)
const auditForm = reactive({
  id: null,
  memberName: '',
  groupName: '',
  type: null,
  reason: '',
  image: '',
  start: '',
  end: '',
  status: null, // 1同意 2驳回
  feedback: ''
})

// 驳回时必须填写审核意见,同意时意见可选
const auditRules = computed(() => ({
  feedback: auditForm.status === 2
    ? [{ required: true, message: '驳回时必须填写审核意见', trigger: 'blur' }]
    : []
}))

function openAuditDialog(row) {
  Object.assign(auditForm, {
    id: row.id,
    memberName: row.memberName ?? '',
    groupName: row.groupName ?? '',
    type: row.type,
    reason: row.reason ?? '',
    image: row.image ?? '',
    start: row.start ?? '',
    end: row.end ?? '',
    status: row.status,
    feedback: ''
  })
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
      feedback: auditForm.feedback,
      seniorId: getCurrentSeniorId()
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
            <el-option v-for="opt in LEAVE_TYPE_OPTIONS" :key="opt.value" :label="opt.label" :value="opt.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="审批状态">
          <el-select v-model="queryParams.status" placeholder="请选择" clearable style="width: 110px">
            <el-option v-for="opt in LEAVE_STATUS_OPTIONS" :key="opt.value" :label="opt.label" :value="opt.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="请假日期">
          <el-date-picker
            v-model="queryParams.dateRange"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="YYYY-MM-DD"
            style="width: 240px"
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
          <template #default="{ row }">{{ row.memberName ?? '-' }}</template>
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
            <el-image v-if="row.image" :src="row.image" :preview-src-list="[row.image]" :preview-teleported="true"
              fit="cover" style="width: 50px; height: 50px; border-radius: 4px; cursor: pointer;" />
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column label="假期开始时间" min-width="120">
          <template #default="{ row }">{{ row.start ?? '-' }}</template>
        </el-table-column>
        <el-table-column label="请假结束时间" min-width="120">
          <template #default="{ row }">{{ row.end ?? '-' }}</template>
        </el-table-column>
        <el-table-column label="审核状态" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="statusTagType(row.status)">{{ statusLabel(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="审核人" min-width="100" show-overflow-tooltip>
          <template #default="{ row }">{{ row.seniorName ?? row.seniorId ?? '-' }}</template>
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
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 审批弹窗:展示完整请假信息后再审批 -->
    <el-dialog v-model="auditVisible" title="审批" width="520px" destroy-on-close>
      <el-form ref="auditFormRef" :model="auditForm" :rules="auditRules" label-width="90px">
        <el-descriptions :column="2" border size="small" style="margin-bottom: 16px">
          <el-descriptions-item label="学员">{{ auditForm.memberName || '-' }}</el-descriptions-item>
          <el-descriptions-item label="所属小组">{{ auditForm.groupName || '-' }}</el-descriptions-item>
          <el-descriptions-item label="请假类型">{{ typeLabel(auditForm.type) }}</el-descriptions-item>
          <el-descriptions-item label="审核状态">
            <el-tag :type="statusTagType(auditForm.status)">{{ statusLabel(auditForm.status) }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="假期时间" :span="2">
            {{ auditForm.start || '-' }} ~ {{ auditForm.end || '-' }}
          </el-descriptions-item>
          <el-descriptions-item label="佐证图片" :span="2">
            <el-image v-if="auditForm.image" :src="auditForm.image" :preview-src-list="[auditForm.image]"
              :preview-teleported="true" fit="cover"
              style="width: 80px; height: 80px; border-radius: 4px; cursor: pointer;" />
            <span v-else>-</span>
          </el-descriptions-item>
          <el-descriptions-item label="请假理由" :span="2">{{ auditForm.reason || '-' }}</el-descriptions-item>
        </el-descriptions>
        <el-form-item label="反馈信息" prop="feedback">
          <el-input
            v-model="auditForm.feedback"
            type="textarea"
            :rows="3"
            :placeholder="auditForm.status === 2 ? '驳回时必须填写审核意见' : '审核意见(同意时可选)'"
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
