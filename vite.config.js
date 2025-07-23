import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  server: {
    cors: true,
    proxy: {
      '/api': {
        target: "http://localhost:8080",
        changeOrigin: true,
        ws: true,
        rewrite: (path) => path.replace(/^\/api/, '') // 重写请求

      }
    }
  },
  plugins: [
    vue(),
    // vueDevTools(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
})
// module.exports = {
   
//   devServer: {
   
//     proxy: {
   
//       '/api': {
   
//         target: 'https://localhost/8080',
//         changeOrigin: true, // 修改目标的origin为代理服务器origin
//         pathRewrite: {
//     '^/api': '' }, // 重写路径，去除'/api'前缀
//       },
//     },
//   },
// };
