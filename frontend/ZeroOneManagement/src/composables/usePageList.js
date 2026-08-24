import { reactive, ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'

/**
 * 列表页通用分页查询逻辑(查询区 + 表格 + 分页器)
 * @param {Object} options
 * @param {(params: Object) => Promise<{code:number,data?:{rows:any[],total:number},msg?:string}>} options.fetchFn 分页查询函数(由 API 层传入)
 * @param {Object} [options.initialParams={}] 查询初始条件(不含 page/pageSize,会并入 queryParams)
 * @param {number} [options.defaultPageSize=10] 默认每页条数
 * @param {(params: Object) => Object} [options.buildParams] 请求前加工参数(如日期范围拆分),不传则原样发送
 * @param {(rows: any[]) => any[]} [options.transformRows] 对返回 rows 的二次加工(如前端排序)
 * @returns 查询状态与分页处理函数
 */
export function usePageList({
  fetchFn,
  initialParams = {},
  defaultPageSize = 10,
  buildParams,
  transformRows
}) {
  const queryParams = reactive({
    ...initialParams,
    page: 1,
    pageSize: defaultPageSize
  })
  const tableData = ref([])
  const total = ref(0)
  const loading = ref(false)

  async function loadList() {
    loading.value = true
    try {
      const params = buildParams ? buildParams(queryParams) : { ...queryParams }
      const res = await fetchFn(params)
      if (res.code === 1) {
        const rows = res.data?.rows || []
        tableData.value = transformRows ? transformRows(rows) : rows
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
    Object.assign(queryParams, initialParams, { page: 1, pageSize: defaultPageSize })
    loadList()
  }

  function handleSizeChange(size) {
    queryParams.pageSize = size
    queryParams.page = 1
    loadList()
  }

  function handleCurrentChange(page) {
    queryParams.page = page
    loadList()
  }

  onMounted(loadList)

  return {
    queryParams,
    tableData,
    total,
    loading,
    loadList,
    handleQuery,
    handleReset,
    handleSizeChange,
    handleCurrentChange
  }
}
