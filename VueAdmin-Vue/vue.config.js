const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
/*  为避免构建后的代码中出现未转译的第三方依赖，
  可以设置 transpileDependencies: true，
   表示对 node_modules 中的文件(夹)也进行编译。
   不过，对所有的依赖都进行转译会降低构建速度*/
  transpileDependencies: true
})
