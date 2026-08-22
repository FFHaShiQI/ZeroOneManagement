import request from '@/utils/request'

export const uploadFile = async(file) => {
   return await request.post("/upload", file)
}