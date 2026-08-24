<script setup>
import { reactive, ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { pageQueryGroups, addGroup, updateGroup, deleteGroups } from '@/api/senior/groups'
import { getAllSeniors } from '@/api/senior/senior'
import { GROUP_LANGUAGE_OPTIONS, optionLabel } from '@/constants/senior'
import { usePageList } from '@/composables/usePageList'

function languageLabel(value) {
  return optionLabel(GROUP_LANGUAGE_OPTIONS, value)
}

const { queryParams, tableData, total, loading, loadList, handleQuery, handleReset, handleSizeChange, handleCurrentChange } = usePageList({
  fetchFn: pageQueryGroups,
  initialParams: { groupName: '', groupType: null, senior: '' }
})

// ---------- 学长下拉选项 ----------
const seniorOptions = ref([])
async function loadSeniorOptions() {
  const res = await getAllSeniors()
  seniorOptions.value = res.code === 1 ? res.data || [] : []
}
onMounted(loadSeniorOptions)

// 表格中按学长 id 显示姓名,查不到时回退 id
function seniorNameById(seniorId) {
  return seniorOptions.value.find((opt) => opt.id === seniorId)?.name ?? seniorId ?? '-'
}

const selectedRows = ref([])

// ---------- 新建/编辑弹窗(共用) ----------
const dialogVisible = ref(false)
const dialogTitle = ref('')
const submitting = ref(false)
const formRef = ref(null)
const form = reactive({
  id: null,
  name: '',
  language: null,
  groupSize: null,
  clockLink: '',
  seniorId: null // 关联学长 id(后端字段名 seniorId)
})

const rules = {
  name: [{ required: true, message: '请输入小组名称', trigger: 'blur' }],
  language: [{ required: true, message: '请选择小组语言类型', trigger: 'change' }],
  groupSize: [{ required: true, message: '请输入小组人数', trigger: 'blur' }],
  clockLink: [{ type: 'url', message: '请输入正确的链接格式', trigger: 'blur' }]
}

function openAddDialog() {
  dialogTitle.value = '新建小组'
  Object.assign(form, {
    id: null, name: '', language: null, groupSize: null, clockLink: '', seniorId: null
  })
  dialogVisible.value = true
}

function openEditDialog(row) {
  dialogTitle.value = '修改小组'
  Object.assign(form, {
    id: row.id,
    name: row.name,
    language: row.language,
    groupSize: row.groupSize,
    clockLink: row.clockLink,
    seniorId: row.seniorId ?? row.leaderId ?? null
  })
  dialogVisible.value = true
}

async function handleSubmit() {
  try {
    await formRef.value.validate()
  } catch {
    return
  }
  submitting.value = true
  try {
    const res = form.id
      ? await updateGroup({ ...form })
      : await addGroup({ ...form })
    if (res.code === 1) {
      ElMessage.success(form.id ? '修改成功' : '创建成功')
      dialogVisible.value = false
      loadList()
    } else {
      ElMessage.error(res.msg || '保存失败')
    }
  } finally {
    submitting.value = false
  }
}

// ---------- 删除 ----------
async function handleBatchDelete() {
  if (!selectedRows.value.length) {
    ElMessage.warning('请先勾选要删除的小组')
    return
  }
  try {
    await ElMessageBox.confirm(`确定删除选中的 ${selectedRows.value.length} 个小组吗?`, '提示', {
      type: 'warning'
    })
  } catch {
    return
  }
  const res = await deleteGroups(selectedRows.value.map((row) => row.id))
  if (res.code === 1) {
    ElMessage.success('删除成功')
    loadList()
  } else {
    ElMessage.error(res.msg || '删除失败')
  }
}

async function handleRowDelete(row) {
  try {
    await ElMessageBox.confirm(`确定删除小组「${row.name}」吗?`, '提示', {
      type: 'warning'
    })
  } catch {
    return
  }
  const res = await deleteGroups([row.id])
  if (res.code === 1) {
    ElMessage.success('删除成功')
    loadList()
  } else {
    ElMessage.error(res.msg || '删除失败')
  }
}
</script>

<template>
  <div class="group-manage">
    <!-- 查询区 -->
    <el-card shadow="never" class="group-card">
      <el-form inline :model="queryParams" @submit.prevent="handleQuery">
        <el-form-item label="模糊查询">
          <el-input
            v-model="queryParams.groupName"
            placeholder="小组名称"
            clearable
            style="width: 160px"
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="小组类型">
          <el-select
            v-model="queryParams.groupType"
            placeholder="请选择"
            clearable
            style="width: 120px"
          >
            <el-option
              v-for="opt in GROUP_LANGUAGE_OPTIONS"
              :key="opt.value"
              :label="opt.label"
              :value="opt.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="学长名">
          <el-input
            v-model="queryParams.senior"
            placeholder="学长姓名"
            clearable
            style="width: 160px"
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 操作区 + 表格 -->
    <el-card shadow="never" class="group-card">
      <div class="group-toolbar">
        <el-button type="primary" @click="openAddDialog">新建</el-button>
        <el-button type="danger" @click="handleBatchDelete">批量删除</el-button>
      </div>

      <el-table
        v-loading="loading"
        :data="tableData"
        border
        @selection-change="selectedRows = $event"
      >
        <el-table-column type="index" label="序号" width="70" align="center" />
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column prop="name" label="组名" min-width="120" show-overflow-tooltip />
        <el-table-column label="小组类型" width="100" align="center">
          <template #default="{ row }">
            <el-tag>{{ languageLabel(row.language) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="打卡表链接" min-width="200" show-overflow-tooltip>
          <template #default="{ row }">
            <el-link v-if="row.clockLink" type="primary" :href="row.clockLink" target="_blank">
              {{ row.clockLink }}
            </el-link>
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column prop="groupSize" label="小组人数" width="100" align="center" />
        <el-table-column label="关联学长" width="120" align="center">
          <template #default="{ row }">{{ seniorNameById(row.seniorId) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="140" align="center" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="openEditDialog(row)">修改</el-button>
            <el-button link type="danger" @click="handleRowDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="group-pagination">
        <el-pagination
          v-model:current-page="queryParams.page"
          v-model:page-size="queryParams.pageSize"
          :total="total"
          :page-sizes="[10, 20, 50]"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 新建/编辑弹窗 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px" destroy-on-close>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="小组名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入小组名称" />
        </el-form-item>
        <el-form-item label="小组类型" prop="language">
          <el-select v-model="form.language" placeholder="请选择语言类型" style="width: 100%">
            <el-option
              v-for="opt in GROUP_LANGUAGE_OPTIONS"
              :key="opt.value"
              :label="opt.label"
              :value="opt.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="小组规模" prop="groupSize">
          <el-input-number v-model="form.groupSize" :min="1" :max="99" placeholder="预设小组人数" />
        </el-form-item>
        <el-form-item label="打卡表链接" prop="clockLink">
          <el-input v-model="form.clockLink" placeholder="https://..." />
        </el-form-item>
        <el-form-item label="关联学长" prop="seniorId">
          <el-select v-model="form.seniorId" clearable filterable placeholder="请选择学长" style="width: 100%">
            <el-option v-for="opt in seniorOptions" :key="opt.id" :label="opt.name" :value="opt.id" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" :loading="submitting" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.group-manage {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.group-toolbar {
  margin-bottom: 16px;
}

.group-pagination {
  display: flex;
  justify-content: flex-end;
  margin-top: 16px;
}
</style>
