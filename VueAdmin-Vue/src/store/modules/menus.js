import Vue from 'vue'
import Vuex from 'vuex'

//引入Vuex插件，组件间通信
Vue.use(Vuex)

export default {
    state: {

        menuList: [],
        permList: [],
        hasRoutes: false

    },
    mutations:{
        setMenuList(state, menus) {
            state.menuList = menus
        },
        setPermList(state, perms) {
            state.permList = perms
        },
        changeRouteStatus(state, hasRoutes) {
            state.hasRoutes = hasRoutes
        }
    },
    actions:{

    },
}