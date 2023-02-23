import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'


import BaiduMap from 'vue-baidu-map'
Vue.use(BaiduMap, {
  // ak 是在百度地图开发者平台申请的密钥 详见 http://lbsyun.baidu.com/apiconsole/key */
  ak: 'sdhic8saQoRvzbucbOLHmfKIGLpQVTHC'
})

import "echarts";
import ECharts from "vue-echarts";
Vue.component("ECharts", ECharts);


//引入全局功能
import global from './globalFun'

//引入element-ui依赖
import Element from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"
Vue.use(Element)

//引入axios依赖
//import axios from 'axios'
//引入axios实例
import axios from './axios'
Vue.prototype.$axios = axios //

Vue.config.productionTip = false

//调用mock测试
require("./mock.js")

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
