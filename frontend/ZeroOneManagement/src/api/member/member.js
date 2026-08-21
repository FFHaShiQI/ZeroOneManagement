import request from '@/utils/request'

export const getMemberInfo = async(id) => {
   return await request.get("/member?id=" + id)
}

// 分页条件查询组员
// params: { name?, gender?, groupId?, pos?, page, pageSize }
export function pageQueryMembers(params) {
  return request.get('/senior/members', { params })
}
