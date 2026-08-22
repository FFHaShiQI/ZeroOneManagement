import request from '@/api/request'

export const uploadFile = async(file) => {
   return await request.post("/upload", file)
}