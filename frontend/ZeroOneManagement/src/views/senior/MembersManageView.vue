<script setup>
import { reactive, ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { pageQueryMembers, addMember, updateMember, deleteMembers } from '@/api/senior/members'
import { getAllGroups } from '@/api/senior/groups'
import { GENDER_OPTIONS, POS_OPTIONS, optionLabel } from '@/constants/senior'
import { uploadFile } from '@/api/upload/upload'
import { usePageList } from '@/composables/usePageList'

const { queryParams, tableData, total, loading, loadList, handleQuery, handleReset, handleSizeChange, handleCurrentChange } = usePageList({
  fetchFn: pageQueryMembers,
  initialParams: { name: '', gender: null, pos: null, groupName: '' }
})

// ---------- 小组下拉选项(新建时选所属小组) ----------
const groupOptions = ref([])
async function loadGroupOptions() {
  const res = await getAllGroups()
  groupOptions.value = res.code === 1 ? res.data || [] : []
}
onMounted(loadGroupOptions)

const selectedRows = ref([])

// ---------- 新建/编辑弹窗(共用) ----------
const dialogVisible = ref(false)
const dialogTitle = ref('')
const submitting = ref(false)
const formRef = ref(null)
const form = reactive({
  id: null,
  name: '',
  username: '',
  password: '',
  groupId: null,
  gender: 1,
  age: null,
  image: '',
  number: '',
  pos: null
})

// 联系方式校验: 支持手机号(1 开头 11 位)/座机/邮箱,留空不校验(非必填)
const CONTACT_PHONE_RE = /^(?:1\d{10}|0\d{2,3}-?\d{7,8}(?:-\d+)?)$/
const CONTACT_EMAIL_RE = /^[\w.%+-]+@[\w.-]+\.[A-Za-z]{2,}$/

function validateContact(rule, value, callback) {
  const v = (value ?? '').trim()
  if (!v || CONTACT_PHONE_RE.test(v) || CONTACT_EMAIL_RE.test(v)) {
    callback()
  } else {
    callback(new Error('请输入正确的电话号码或邮箱'))
  }
}

const rules = {
  name: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  groupId: [{ required: true, message: '请选择所属小组', trigger: 'change' }],
  age: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
  pos: [{ required: true, message: '请选择职位', trigger: 'change' }],
  number: [{ validator: validateContact, trigger: 'blur' }]
}

function openAddDialog() {
  dialogTitle.value = '新建组员'
  Object.assign(form, {
    id: null, name: '', username: '', password: '',
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
    // 密码不回显,留空表示不修改
    password: '',
    groupId: row.groupId,
    gender: row.gender ?? 1,
    age: row.age,
    image: row.image ?? '',
    number: row.number ?? '',
    pos: row.pos
  })
  dialogVisible.value = true
}

// 头像选择本地文件后直接上传,成功后把返回的 url 写入 form.image
async function handleAvatarChange(uploadFileObj) {
  const file = uploadFileObj.raw
  if (!file) return
  const formData = new FormData()
  formData.append('file', file)
  const uploadRes = await uploadFile(formData)
  if (uploadRes.code === 1) {
    form.image = uploadRes.data
    ElMessage.success('头像上传成功')
  } else {
    ElMessage.error(uploadRes.msg || '头像上传失败')
  }
}

async function handleSubmit() {
  try {
    await formRef.value.validate()
  } catch {
    return
  }
  submitting.value = true
  try {
    const payload = { ...form }
    // 编辑时密码留空 = 不修改(后端 update 仅在密码非空时更新)
    if (form.id !== null && !payload.password) {
      delete payload.password
    }
    const res = form.id !== null
      ? await updateMember(payload)
      : await addMember(payload)
    if (res.code === 1) {
      ElMessage.success(form.id !== null ? '修改成功' : '创建成功')
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
  const res = await deleteMembers(selectedRows.value.map((row) => row.id))
  if (res.code === 1) {
    ElMessage.success('删除成功')
    loadList()
  } else {
    ElMessage.error(res.msg || '删除失败')
  }
}

async function handleRowDelete(row) {
  try {
    await ElMessageBox.confirm(`确定删除组员「${row.name}」吗?`, '提示', {
      type: 'warning'
    })
  } catch {
    return
  }
  const res = await deleteMembers([row.id])
  if (res.code === 1) {
    ElMessage.success('删除成功')
    loadList()
  } else {
    ElMessage.error(res.msg || '删除失败')
  }
}
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
          <template #default="{ row }">{{ optionLabel(GENDER_OPTIONS, row.gender) }}</template>
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
            <el-tag>{{ optionLabel(POS_OPTIONS, row.pos) }}</el-tag>
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
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 新建/编辑弹窗 -->
    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="560px" destroy-on-close>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
              <!-- 后端 MemberUpdateDto 不含 username,编辑时修改无效,故仅新建时可填 -->
              <el-input
                v-model="form.username"
                :disabled="form.id !== null"
                :placeholder="form.id !== null ? '用户名不可修改' : '请输入用户名'"
              />
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
              <el-input
                v-model="form.password"
                type="password"
                show-password
                :placeholder="form.id !== null ? '留空则不修改' : '默认 123456'"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属小组" prop="groupId">
              <!-- 后端 MemberUpdateDto 不含 groupId,编辑时修改无效,故仅新建可选 -->
              <el-select
                v-model="form.groupId"
                :disabled="form.id !== null"
                placeholder="请选择所属小组"
                style="width: 100%"
              >
                <el-option v-for="opt in groupOptions" :key="opt.id" :label="opt.name" :value="opt.id" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-radio-group v-model="form.gender">
                <el-radio v-for="opt in GENDER_OPTIONS" :key="opt.value" :label="opt.value">
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
        <el-form-item label="头像" prop="image">
          <el-upload
            class="avatar-uploader"
            action="#"
            :show-file-list="false"
            :auto-upload="false"
            accept="image/*"
            :on-change="handleAvatarChange"
          >
            <div class="avatar-upload-trigger">
              <el-avatar v-if="form.image" :src="form.image" :size="60" />
              <span v-else class="avatar-upload-tip">点击上传头像</span>
            </div>
          </el-upload>
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

.avatar-uploader :deep(.el-upload) {
  border: 1px dashed #dcdfe6;
  border-radius: 6px;
  cursor: pointer;
  padding: 4px;
  overflow: hidden;
  transition: border-color 0.3s;
}

.avatar-uploader :deep(.el-upload:hover) {
  border-color: #409eff;
}

.avatar-uploader :deep(.el-upload:focus-visible) {
  outline: none;
}

.avatar-upload-trigger {
  display: flex;
  align-items: center;
  justify-content: center;
  min-width: 80px;
  min-height: 68px;
}

.avatar-upload-tip {
  color: #909399;
  font-size: 12px;
}
</style>
