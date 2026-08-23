import request from '@/utils/request'

// 分页条件查询小组列表
// params: { groupName?, groupType?, senior?, page, pageSize }
export function pageQueryGroups(params) {
  return request.get('/senior/groups', { params })
}

// 创建小组
// body: { name, language, groupSize, clockLink?, leaderId? }
export function addGroup(data) {
  return request.post('/senior/groups', data)
}

// 修改小组信息(需带 id)
export function updateGroup(data) {
  return request.put('/senior/groups', data)
}

// 批量删除小组(按 id 列表)
export function deleteGroups(groupIds) {
  return request.delete('/senior/groups', {
    params: { groupIds: groupIds.join(',') }
  })
}

// 查询所有小组(仅 id + name,供下拉选择使用)
export function getAllGroups() {
  return request.get('/senior/groups/All')
}
