import { post, get } from './request'
import type { LoginParams, LoginResult, RegisterParams, MeResult } from '@/types'

/** 登录 POST /login */
export function apiLogin(data: LoginParams) {
  return post<LoginResult>('/login', data)
}

/** 注册 POST /register */
export function apiRegister(data: RegisterParams) {
  return post<null>('/register', data)
}

/** 获取当前登录用户信息 GET /me */
export function apiGetMe() {
  return get<MeResult>('/me')
}
