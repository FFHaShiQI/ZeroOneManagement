import axios from 'axios'
import type { ApiResponse } from '@/types'
import { getToken, clearAuth } from '@/utils/auth'

const request = axios.create({
  baseURL: '',          // 开发环境靠 vite proxy
  timeout: 10000
})

// 请求前自动带上 token
request.interceptors.request.use((config) => {
  const token = getToken()
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

// 统一处理响应：code=0 抛错；401 清除登录态回登录页
request.interceptors.response.use(
  (response) => {
    const res = response.data
    if (res.code === 0) {
      return Promise.reject(new Error(res.message || res.msg || '请求失败'))
    }
    return response
  },
  (error) => {
    if (error.response?.status === 401) {
      clearAuth()
      window.location.href = '/login'
    }
    return Promise.reject(new Error(error.response?.data?.message || error.message || '网络异常'))
  }
)

/** 封装 POST，返回 { code, data, msg } */
export function post<T = null>(url: string, data?: unknown): Promise<ApiResponse<T>> {
  return request.post(url, data).then((res) => res.data as ApiResponse<T>)
}

/** 封装 GET，返回 { code, data, msg } */
export function get<T = null>(url: string): Promise<ApiResponse<T>> {
  return request.get(url).then((res) => res.data as ApiResponse<T>)
}

export default request
