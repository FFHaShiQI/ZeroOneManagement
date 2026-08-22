<script setup>
import { reactive, ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { pageQueryMembers, addMember, updateMember, deleteMembers } from '../../api/member'

// 性别: 1男 2女
const GENDER_OPTIONS = [
  { label: '男', value: 1 },
  { label: '女', value: 2 }
]
// 职位: 1组长 2副组长 3产品经理 4信息官 5技术官 6监督官
const POS_OPTIONS = [
  { label: '组长', value: 1 },
  { label: '副组长', value: 2 },
  { label: '产品经理', value: 3 },
  { label: '信息官', value: 4 },
  { label: '技术官', value: 5 },
  { label: '监督官', value: 6 }
]

function genderLabel(value) {
  return GENDER_OPTIONS.find((opt) => opt.value === value)?.label ?? '-'
}
function posLabel(value) {
  return POS_OPTIONS.find((opt) => opt.value === value)?.label ?? '-'
}

// ---------- 查询区 ----------
const queryParams = reactive({
  name: '',
  gender: null,
  pos: null,
  groupName: '',
  page: 1,
  pageSize: 10
})

const tableData = ref([])
const total = ref(0)
const loading = ref(false)
const selectedRows = ref([])

async function loadList() {
  loading.value = true
  try {
    const data = await pageQueryMembers(queryParams)
    tableData.value = data.rows || []
    total.value = data.total || 0
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
  queryParams.gender = null
  queryParams.pos = null
  queryParams.groupName = ''
  queryParams.page = 1
  loadList()
}

function handleSizeChange(size) {
  queryParams.pageSize = size
  queryParams.page = 1
  loadList()
}

// ---------- 新建/编辑弹窗(共用) ----------
const dialogVisible = ref(false)
const dialogTitle = ref('')
const submitting = ref(false)
const formRef = ref(null)
const form = reactive({
  id: null,
  name: '',
  username: '',
  password: '123456',
  groupId: null,
  gender: 1,
  age: null,
  image: '',
  number: '',
  pos: null
})

const rules = {
  name: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  groupId: [{ required: true, message: '请输入所属小组id', trigger: 'blur' }],
  age: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
  pos: [{ required: true, message: '请选择职位', trigger: 'change' }]
}

function openAddDialog() {
  dialogTitle.value = '新建组员'
  Object.assign(form, {
    id: null, name: '', username: '', password: '123456',
    groupId: null, gender: 1, age: null, image: '', number: '', pos: null
  })
  dialogVisible.value = true
}

function openEditDialog(row) {
  dialogTitle.value = '修改组员'
  Object.assign(form, {
    id: row.id,
    name: row.name,
    username: row.username,
    password: row.password ?? '',
    groupId: row.groupId,
    gender: row.gender ?? 1,
    age: row.age,
    image: row.image ?? '',
    number: row.number ?? '',
    pos: row.pos
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
    if (form.id) {
      await updateMember({ ...form })
      ElMessage.success('修改成功')
    } else {
      await addMember({ ...form })
      ElMessage.success('创建成功')
    }
    dialogVisible.value = false
    loadList()
  } finally {
    submitting.value = false
  }
}

// ---------- 删除 ----------
async function handleBatchDelete() {
  if (!selectedRows.value.length) {
    ElMessage.warning('请先勾选要删除的组员')
    return
  }
  try {
    await ElMessageBox.confirm(`确定删除选中的 ${selectedRows.value.length} 名组员吗?`, '提示', {
      type: 'warning'
    })
  } catch {
    return
  }
  await deleteMembers(selectedRows.value.map((row) => row.id))
  ElMessage.success('删除成功')
  loadList()
}

async function handleRowDelete(row) {
  try {
    await ElMessageBox.confirm(`确定删除组员「${row.name}」吗?`, '提示', {
      type: 'warning'
    })
  } catch {
    return
  }
  await deleteMembers([row.id])
  ElMessage.success('删除成功')
  loadList()
}

onMounted(loadList)
</script>

<template>
  <div class="member-manage">
    <!-- 查询区 -->
    <el-card shadow="never" class="page-card">
      <el-form inline :model="queryParams" @submit.prevent="handleQuery">
        <el-form-item label="模糊查询">
          <el-input
            v-model="queryParams.name"
            placeholder="昵称"
            clearable
            style="width: 140px"
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="queryParams.gender" placeholder="请选择" clearable style="width: 100px">
            <el-option v-for="opt in GENDER_OPTIONS" :key="opt.value" :label="opt.label" :value="opt.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="职位">
          <el-select v-model="queryParams.pos" placeholder="请选择" clearable style="width: 120px">
            <el-option v-for="opt in POS_OPTIONS" :key="opt.value" :label="opt.label" :value="opt.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="小组名">
          <el-input
            v-model="queryParams.groupName"
            placeholder="小组名"
            clearable
            style="width: 140px"
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
    <el-card shadow="never" class="page-card">
      <div class="page-toolbar">
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
        <el-table-column prop="username" label="用户名" min-width="100" show-overflow-tooltip />
        <el-table-column prop="name" label="昵称" min-width="100" show-overflow-tooltip />
        <el-table-column label="性别" width="80" align="center">
          <template #default="{ row }">{{ genderLabel(row.gender) }}</template>
        </el-table-column>
        <el-table-column prop="age" label="年龄" width="80" align="center" />
        <el-table-column label="小组" min-width="100" show-overflow-tooltip>
          <template #default="{ row }">{{ row.groupName ?? row.groupId ?? '-' }}</template>
        </el-table-column>
        <el-table-column label="头像" width="80" align="center">
          <template #default="{ row }">
            <el-avatar v-if="row.image" :src="row.image" :size="32" />
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column prop="number" label="联系方式" min-width="110" show-overflow-tooltip />
        <el-table-column label="小组职位" width="100" align="center">
          <template #default="{ row }">
            <el-tag>{{ posLabel(row.pos) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="140" align="center" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="openEditDialog(row)">修改</el-button>
            <el-button link type="danger" @click="handleRowDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="page-pagination">
        <el-pagination
          v-model:current-page="queryParams.page"
          v-model:page-size="queryParams.pageSize"
          :total="total"
          :page-sizes="[10, 20, 50]"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
        />
      </div>
    </el-card>

    <!-- 新建/编辑弹窗 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="560px" destroy-on-close>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="form.username" placeholder="请输入用户名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="昵称" prop="name">
              <el-input v-model="form.name" placeholder="请输入昵称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="密码" prop="password">
              <el-input v-model="form.password" type="password" show-password placeholder="默认 123456" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属小组id" prop="groupId">
              <el-input-number v-model="form.groupId" :min="1" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-radio-group v-model="form.gender">
                <el-radio v-for="opt in GENDER_OPTIONS" :key="opt.value" :value="opt.value">
                  {{ opt.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年龄" prop="age">
              <el-input-number v-model="form.age" :min="1" :max="120" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="职位" prop="pos">
              <el-select v-model="form.pos" placeholder="请选择职位" style="width: 100%">
                <el-option v-for="opt in POS_OPTIONS" :key="opt.value" :label="opt.label" :value="opt.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系方式" prop="number">
              <el-input v-model="form.number" placeholder="请输入联系方式" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="头像链接" prop="image">
          <el-input v-model="form.image" placeholder="https://..." />
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
.member-manage {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.page-toolbar {
  margin-bottom: 16px;
}

.page-pagination {
  display: flex;
  justify-content: flex-end;
  margin-top: 16px;
}
</style>
