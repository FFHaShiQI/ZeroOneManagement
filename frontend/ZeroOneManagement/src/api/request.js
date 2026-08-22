import axios from 'axios'

// 创建 axios 实例对象
const request = axios.create({
  baseURL: '/api',
  timeout: 600000
})

// axios 的响应 response 拦截器
request.interceptors.response.use(
  (response) => { // 成功回调
    return response.data
  },
  (error) => { // 失败回调

  }
)

// 封装 post/get，供登录注册等接口使用
export function post(url, data) {
  return request.post(url, data)
}

export function get(url, params) {
  return request.get(url, { params })
}

export default request
