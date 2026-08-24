import axios from 'axios'

// 创建 axios 实例对象
const request = axios.create({
  baseURL: '/api',
  timeout: 600000
})

// 请求拦截器: 自动携带登录 token(后端 TokenInterceptor 按 token 头校验)
request.interceptors.request.use((config) => {
  const userData = localStorage.getItem('userData')
  if (userData) {
    try {
      const token = JSON.parse(userData).token
      if (token) {
        config.headers.token = token
      }
    } catch {
      // userData 非合法 JSON 时忽略
    }
  }
  return config
})

// axios 的响应 response 拦截器
request.interceptors.response.use(
  (response) => { // 成功回调
    return response.data
  },
  (error) => { // 失败回调

  }
)

export default request