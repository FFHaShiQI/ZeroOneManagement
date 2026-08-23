import request from '@/utils/request'
// 获取个人信息接口
export const getMemberInfo = async(id) => {
   return await request.get("/member?id=" + id)
}
// 更新个人信息接口
export const updateMemberInfo = async(data) => {
   return await request.put("/member", data)
}
// 获取小组信息接口
export const getGroupInfo = async(groupId) => {
   return await request.get("/member/group?groupId=" + groupId)
}
// 查询个人请假列表
export const getLeaveList = async(id) => {
   return await request.get("/member/leaveApply?id=" + id)
}

// 提交请假申请
export const addLeaveApply = async(data) => {
   return await request.post("/member/leaveApply", data)
}
