import request from '../utils/request'

// 分页条件查询组员
// params: { name?, gender?, pos?, groupName?, page, pageSize }
export function pageQueryMembers(params) {
  return request.get('/senior/members', { params })
}

// 添加组员
// body: { name, username, password, groupId, gender, age, image?, number?, pos }
export function addMember(data) {
  return request.post('/senior/members', data)
}

// 修改组员信息(必填 id)
export function updateMember(data) {
  return request.put('/senior/members', data)
}

// 批量删除组员(按 id 列表)
export function deleteMembers(ids) {
  return request.delete('/senior/members', {
    params: { ids: ids.join(',') }
  })
}
