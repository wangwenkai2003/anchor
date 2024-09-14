const { defineConfig } = require('@vue/cli-service');
const path = require('path');
module.exports = defineConfig({
  configureWebpack: {
    resolve: {
      alias: {
        '@': path.resolve(__dirname, 'src'),
      },
    },
  },
  transpileDependencies: true,
  devServer: {
    host: '0.0.0.0',
    port: 3005,
    // 跳过host检查
    allowedHosts: ['all'],
    proxy: {
      '/api': {
        target: 'http://127.0.0.1:8088/',
        changeOrigin: true,
        pathRewrite: { '^/api': '' },
      },
    },
    client: {
      overlay: false,
    }
  },
});