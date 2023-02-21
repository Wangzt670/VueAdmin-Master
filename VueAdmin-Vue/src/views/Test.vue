<template>
  <el-row type="flex" class="row-bg" justify="center">

    <el-col :xl="6" :lg="7">
      <h2>欢迎来到VueAdmin管理系统</h2>
      <el-image :src="require('@/assets/login-car.jpeg')" style="height: 300px; width: 300px;"></el-image>
      <p>重庆大学 计算机学院</p>
      <p>王正霆 20194249</p>
    </el-col>

    <el-col :span="1">
      <el-divider direction="vertical"></el-divider>
    </el-col>

    <el-col :xl="6" :lg="7">
      <h2>欢迎来到VueAdmin管理系统</h2>
      <el-image :src="require('@/assets/login-car.jpeg')" style="height: 300px; width: 300px;"></el-image>
      <p>重庆大学 计算机学院</p>
      <p>王正霆 20194249</p>
    </el-col>

  </el-row>
</template>

<script>
export default {
  name: "Test"
}
</script>

<style scoped>
.el-row {
  background-color: #fafafa;
  height: 100%;
  display: flex;
  align-items: center;
  text-align: center;
  justify-content: center;
}
</style>