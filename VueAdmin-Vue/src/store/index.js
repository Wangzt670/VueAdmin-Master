import Vue from 'vue'
import Vuex from 'vuex'

import menus from "@/store/modules/menus";

//引入Vuex插件，组件间通信
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: ''
  },
  getters: {
  },
  mutations: {
    //将传递进来的token传递到state里面
    SET_TOKEN: (state, token) => {
      state.token = token
      localStorage.setItem("token", token)
    },

    resetState:(state)=>{
      state.token = ''
    }
  },
  actions: {
  },
  modules: {
    menus
  }
})
