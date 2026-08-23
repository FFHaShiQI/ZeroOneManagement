<script setup>
import { reactive, ref, onMounted, onBeforeUnmount } from 'vue'
import { use, init } from 'echarts/core'
import { BarChart, PieChart } from 'echarts/charts'
import { GridComponent, TooltipComponent, LegendComponent } from 'echarts/components'
import { CanvasRenderer } from 'echarts/renderers'
import { pageQueryGroups } from '@/api/senior/groups'
import { pageQueryMembers } from '@/api/senior/members'
import { pageQueryLeaves } from '@/api/senior/leaves'
import { POS_OPTIONS, LEAVE_TYPE_OPTIONS } from '@/constants/senior'

use([BarChart, PieChart, GridComponent, TooltipComponent, LegendComponent, CanvasRenderer])

// 统计口径说明:
// 后端暂无 dashboard 统计接口,以下数据通过现有分页接口的 total 聚合而来,
// 待后端提供 /dashboard/statistics 后替换。
// 注意: pos/type 字段为空的记录不会进入对应图表(以分页接口筛选口径为准),与总数卡片存在口径差异。

const loading = ref(false)
const stats = reactive({
  groupTotal: 0, // 小组总数
  memberTotal: 0, // 组员总数
  leaveTotal: 0, // 请假记录总数
  pendingAudit: 0 // 待审批数
})

const barRef = ref(null)
const pieRef = ref(null)
let barChart = null
let pieChart = null

async function loadData() {
  loading.value = true
  try {
    // 一次并行发出全部统计请求(卡片 4 个 + 柱状图 6 个 + 饼图 3 个),避免串行分批
    const [groups, members, leaves, pending, posCounts, typeCounts] = await Promise.all([
      pageQueryGroups({ page: 1, pageSize: 1 }),
      pageQueryMembers({ page: 1, pageSize: 1 }),
      pageQueryLeaves({ page: 1, pageSize: 1 }),
      pageQueryLeaves({ page: 1, pageSize: 1, status: 0 }),
      Promise.all(POS_OPTIONS.map(({ value: pos }) => pageQueryMembers({ page: 1, pageSize: 1, pos }))),
      Promise.all(LEAVE_TYPE_OPTIONS.map(({ value: type }) => pageQueryLeaves({ page: 1, pageSize: 1, type })))
    ])

    // ---------- 统计卡片 ----------
    stats.groupTotal = groups?.data?.total ?? 0
    stats.memberTotal = members?.data?.total ?? 0
    stats.leaveTotal = leaves?.data?.total ?? 0
    stats.pendingAudit = pending?.data?.total ?? 0

    // ---------- 柱状图: 组员职位分布 ----------
    barChart.setOption({
      xAxis: { data: POS_OPTIONS.map((item) => item.label) },
      series: [{ data: posCounts.map((d) => d?.data?.total ?? 0) }]
    })

    // ---------- 饼图: 请假类型分布 ----------
    pieChart.setOption({
      series: [
        {
          data: LEAVE_TYPE_OPTIONS.map((item, index) => ({
            name: item.label,
            value: typeCounts[index]?.data?.total ?? 0
          }))
        }
      ]
    })
  } finally {
    loading.value = false
  }
}

function initCharts() {
  // 左侧: 组员职位分布柱状图
  barChart = init(barRef.value)
  barChart.setOption({
    tooltip: {},
    grid: { left: 40, right: 20, top: 30, bottom: 30 },
    xAxis: {
      type: 'category',
      data: []
    },
    yAxis: {
      type: 'value',
      minInterval: 1
    },
    series: [
      {
        name: '人数',
        type: 'bar',
        barMaxWidth: 40,
        itemStyle: { color: '#409eff' },
        data: []
      }
    ]
  })

  // 右侧: 请假类型分布饼图
  pieChart = init(pieRef.value)
  pieChart.setOption({
    tooltip: { trigger: 'item' },
    legend: { bottom: 0 },
    series: [
      {
        name: '请假类型',
        type: 'pie',
        radius: ['35%', '60%'],
        center: ['50%', '45%'],
        data: []
      }
    ]
  })
}

function handleResize() {
  barChart?.resize()
  pieChart?.resize()
}

onMounted(() => {
  initCharts()
  loadData()
  window.addEventListener('resize', handleResize)
})

onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize)
  barChart?.dispose()
  pieChart?.dispose()
})
</script>

<template>
  <div v-loading="loading" class="dashboard">

    <!-- 统计卡片 -->
    <el-row :gutter="16">
      <el-col :span="6">
        <el-card shadow="never">
          <el-statistic title="小组总数" :value="stats.groupTotal" />
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="never">
          <el-statistic title="组员总数" :value="stats.memberTotal" />
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="never">
          <el-statistic title="请假记录总数" :value="stats.leaveTotal" />
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="never">
          <el-statistic title="待审批" :value="stats.pendingAudit">
            <template #suffix>
              <span style="font-size: 14px; color: #e6a23c">条</span>
            </template>
          </el-statistic>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表区 -->
    <div class="dashboard-charts">
      <el-card shadow="never" class="chart-card">
        <template #header>组员职位分布(柱状图)</template>
        <div ref="barRef" class="chart-box" />
      </el-card>
      <el-card shadow="never" class="chart-card">
        <template #header>请假类型分布(饼图)</template>
        <div ref="pieRef" class="chart-box" />
      </el-card>
    </div>
  </div>
</template>

<style scoped>
.dashboard {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.dashboard-charts {
  display: flex;
  gap: 16px;
}

.chart-card {
  flex: 1;
}

.chart-box {
  height: 360px;
}
</style>
