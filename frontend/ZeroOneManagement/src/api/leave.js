import request from '../utils/request'

// 分页条件查询请假记录
// params: { name?, type?, status?, start?, end?, page, pageSize }
export function pageQueryLeaves(params) {
  return request.get('/senior/leaves', { params })
}

// 审批请假记录
// body: { id, status: 1同意/2驳回, feedback }
export function auditLeave(data) {
  return request.put('/senior/leaves', data)
}

// 删除请假记录(路径参数)
export function deleteLeave(id) {
  return request.delete(`/senior/leaves/${id}`)
}
