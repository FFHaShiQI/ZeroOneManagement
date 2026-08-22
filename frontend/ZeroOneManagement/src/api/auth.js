import { post } from './request'

// ===== 登录 / 注册接口 =====

/** 登录 POST /login */
export function apiLogin(data) {
  return post('/login', data)
}

/** 注册 POST /register */
export function apiRegister(data) {
  return post('/register', data)
}

// ===== 登录态（token）管理 =====

const TOKEN_KEY = 'zom_token'
const ROLE_KEY = 'zom_role'

/** 获取 token */
export function getToken() {
  return localStorage.getItem(TOKEN_KEY)
}

/** 获取角色（MEMBER / SENIOR） */
export function getRole() {
  return localStorage.getItem(ROLE_KEY)
}

/** 登录成功后保存 token 和角色 */
export function setAuth(token, role) {
  localStorage.setItem(TOKEN_KEY, token)
  localStorage.setItem(ROLE_KEY, role)
}

/** 退出登录 / token 失效时清除 */
export function clearAuth() {
  localStorage.removeItem(TOKEN_KEY)
  localStorage.removeItem(ROLE_KEY)
}
