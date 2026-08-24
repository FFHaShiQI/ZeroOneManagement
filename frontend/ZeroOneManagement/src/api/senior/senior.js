import request from '@/utils/request'

// 获取学长个人信息
export function getSeniorProfile(id) {
  return request.get('/senior', { params: { id } })
}

// 修改学长个人信息(必填 id)
export function updateSeniorProfile(data) {
  return request.put('/senior', data)
}

// 查询全部学长(仅 id + 姓名,供下拉选择使用)
export function getAllSeniors() {
  return request.get('/senior/All')
}
