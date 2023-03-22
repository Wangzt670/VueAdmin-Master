import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

//关闭生产版本的提示
Vue.config.productionTip = false


//引入全局功能
import global from './globalFun'


//引入BaiduMap插件
import BaiduMap from 'vue-baidu-map'
Vue.use(BaiduMap, {
  // ak 是在百度地图开发者平台申请的密钥 详见 http://lbsyun.baidu.com/apiconsole/key */
  ak: 'sdhic8saQoRvzbucbOLHmfKIGLpQVTHC'
})

//引入echarts组件
import "echarts";
import ECharts from "vue-echarts";
Vue.component("ECharts", ECharts);

//引入element-ui插件
import Element from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"
Vue.use(Element)

//引入用axios插件实例化的axios实例
import axios from './axios'
Vue.prototype.$axios = axios //

//引入mockjs插件
/*require("./mock.js")*/

//实例化Vue
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
