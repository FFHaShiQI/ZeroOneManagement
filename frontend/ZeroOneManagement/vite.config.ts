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
    proxy: {
      // 前端路由（/login /register）与后端接口路径相同，
      // 用 bypass 区分：浏览器打开页面（Accept: text/html）→ 返回前端页面；
      // axios 发起的 API 请求 → 转发后端。后端地址以土豆雷实际端口为准。
      '/login': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        bypass(req) {
          if (req.headers.accept?.includes('text/html')) {
            return '/index.html'
          }
        }
      },
      '/register': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        bypass(req) {
          if (req.headers.accept?.includes('text/html')) {
            return '/index.html'
          }
        }
      },
      '/me': { target: 'http://localhost:8080', changeOrigin: true },
      '/upload': { target: 'http://localhost:8080', changeOrigin: true }
    }
  }
})
