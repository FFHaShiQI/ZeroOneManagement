import { fileURLToPath, URL } from 'node:url'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    port: 5173,
    // 开发环境代理：前端请求 /login 等路径，转发到后端 Spring Boot（默认 8080）
    // 后端地址以土豆雷实际启动端口为准，改这里即可，前端代码不用动
    proxy: {
      '/login': { target: 'http://localhost:8080', changeOrigin: true },
      '/register': { target: 'http://localhost:8080', changeOrigin: true },
      '/me': { target: 'http://localhost:8080', changeOrigin: true },
      '/upload': { target: 'http://localhost:8080', changeOrigin: true }
    }
  }
})
