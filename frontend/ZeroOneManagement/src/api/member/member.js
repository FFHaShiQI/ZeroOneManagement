import request from '@/utils/request'

export const getMemberInfo = async(id) => {
   return await request.get("/member?id=" + id)
}