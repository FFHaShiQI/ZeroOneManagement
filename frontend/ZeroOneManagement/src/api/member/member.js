import request from '@/utils/request'

export const getMemberInfo = async(id) => {
   return await request.get("/member?id=" + id)
}

export const updateMemberInfo = async(data) => {
   return await request.put("/member", data)
}