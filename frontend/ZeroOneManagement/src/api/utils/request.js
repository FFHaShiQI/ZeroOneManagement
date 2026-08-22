import request from '@/utils/request'

// utils/request.js 响应拦截器已返回 response.data（{ code, data, msg }）
// 这里封装 post/get，供登录注册等接口使用
export function post(url, data) {
  return request.post(url, data)
}

export function get(url, params) {
  return request.get(url, { params })
}

export default request
