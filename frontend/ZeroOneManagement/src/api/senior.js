import request from '../utils/request'

// 获取学长个人信息
export function getSeniorProfile(id) {
  return request.get('/senior', { params: { id } })
}

// 修改学长个人信息(必填 id)
export function updateSeniorProfile(data) {
  return request.put('/senior', data)
}
