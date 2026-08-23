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