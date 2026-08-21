import axios from 'axios'
<<<<<<< HEAD
import { ElMessage } from 'element-plus'

// 后端统一返回包装: { code: 1成功/0失败, msg, data }
const request = axios.create({
  baseURL: '/api',
  timeout: 10000
})

request.interceptors.response.use(
  (response) => {
    const res = response.data
    if (res.code === 1) {
      return res.data
    }
    ElMessage.error(res.msg || '请求失败')
    return Promise.reject(new Error(res.msg || '请求失败'))
  },
  (error) => {
    ElMessage.error(error.message || '网络错误')
    return Promise.reject(error)
  }
)

export default request
=======

//创建axios实例对象
const request = axios.create({
  baseURL: '/api',
  timeout: 600000
})


//axios的响应 response 拦截器
request.interceptors.response.use(
  (response) => { //成功回调
    return response.data
  },
  (error) => { //失败回调
    
  }
)

export default request
>>>>>>> feature/member
