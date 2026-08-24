// 学长端各页面共用的枚举常量(与后端枚举保持一致,修改时需同步后端)

// 性别: 1男 2女
export const GENDER_OPTIONS = [
  { label: '男', value: 1 },
  { label: '女', value: 2 }
]

// 职位: 1组长 2副组长 3产品经理 4信息官 5技术官 6监督官
export const POS_OPTIONS = [
  { label: '组长', value: 1 },
  { label: '副组长', value: 2 },
  { label: '产品经理', value: 3 },
  { label: '信息官', value: 4 },
  { label: '技术官', value: 5 },
  { label: '监督官', value: 6 }
]

// 请假类型: 0病假 1事假 2其他(与后端 LeaveApply 表枚举一致)
export const LEAVE_TYPE_OPTIONS = [
  { label: '病假', value: 0 },
  { label: '事假', value: 1 },
  { label: '其他', value: 2 }
]

// 审核状态: 0审核中 1通过 2驳回(与后端枚举一致)
export const LEAVE_STATUS_OPTIONS = [
  { label: '待审批', value: 0 },
  { label: '审核成功', value: 1 },
  { label: '已驳回', value: 2 }
]

// 小组语言类型: 0=C 1=C++ 2=Java
export const GROUP_LANGUAGE_OPTIONS = [
  { label: 'C', value: 0 },
  { label: 'C++', value: 1 },
  { label: 'Java', value: 2 }
]

// 学长类型: 1大学长 2小学长
export const SENIOR_TYPE_OPTIONS = [
  { label: '大学长', value: 1 },
  { label: '小学长', value: 2 }
]

// 通用: 按常量选项取文案,无匹配返回 '-'
export function optionLabel(options, value) {
  return options.find((opt) => opt.value === value)?.label ?? '-'
}
