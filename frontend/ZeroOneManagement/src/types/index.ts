/** 后端统一响应包装：code=1 成功，code=0 失败 */
export interface ApiResponse<T = null> {
  code: number
  msg?: string          // 成功提示（部分接口）
  message?: string      // 失败提示（部分接口用 message）
  data: T
}

/** 用户角色：组员 / 学长 */
export type UserRole = 'MEMBER' | 'SENIOR'

/** 组员信息（对应 Member 表） */
export interface Member {
  id: number
  username: string
  name: string          // 昵称
  groupId: number
  gender: number        // 1 男 2 女
  age: number
  image?: string        // 头像
  selfIntro?: string
  number?: string       // 联系方式
  pos?: number          // 小组职位
}

/** 学长信息（对应 Senior 表） */
export interface Senior {
  id: number
  type: number          // 1 大学长 2 小学长
  name: string
  number?: string
  username: string
  image?: string
}

/** 登录后返回的用户信息 */
export interface UserInfo {
  id: number
  username?: string
  nickname: string
  avatar?: string
  role?: UserRole
}

/** 登录请求体 */
export interface LoginParams {
  username: string
  password: string
  role: UserRole        // 勾选「学长登录」传 SENIOR，否则 MEMBER
}

/** 登录成功返回的 data */
export interface LoginResult {
  token: string
  role: UserRole
  userInfo: UserInfo
}

/** 注册请求体 */
export interface RegisterParams {
  username: string
  password: string
  inviteCode?: string   // 学长邀请码，选填
  groupId?: number      // 学员（未填邀请码）时必填
  option: boolean       // true=注册为学长，false=注册为学员
}

/** /me 返回的当前用户信息 */
export interface MeResult {
  id: number
  nickname: string
  avatar?: string
  role: UserRole
}