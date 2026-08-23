import { post } from './request'

/** 登录 POST /login */
export function apiLogin(data) {
  return post('/login', data)
}

/** 注册 POST /register */
export function apiRegister(data) {
  return post('/register', data)
}
