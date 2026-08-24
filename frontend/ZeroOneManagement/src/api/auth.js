import request from '@/utils/request'
//  登录接口
export const login = async(data) => {
   return await request.post("/login", data)
}

//  注册接口
export const register = async(data) => {
   return await request.post("/register", data)
}
// 获取所有小组列表
export const getAllGroups = async() => {
   return await request.get("/senior/groups/All")
}
