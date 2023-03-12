import Vue from 'vue'
import VueRouter from 'vue-router'

import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Index from '../views/Index.vue'


import UserCenter from "../views/sys_management/UserCenter.vue";


// import axios from "axios";
import axios from "../axios";
import store from "../store";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home,
    children:[
      {
        path: '/index',
        name: 'index',
        component: Index
      },
      {
        path: '/UserCenter',
        name: 'UserCenter',
        component: UserCenter
      }
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


// 动态绑定路由
router.beforeEach((to, from, next) => {

    //判断是否获取过路由，设置一个参数
    let hasRoute = store.state.menus.hasRoutes

    let token = localStorage.getItem("token")

    if(to.path == '/login'){
        next()
    }else if(!token){
        next({path:'/login'})
    }else if(token && !hasRoute){
      //获取导航栏请求
      axios.get("/sys/menu/nav", {
        headers: {
          Authorization: localStorage.getItem("token")
        }
      }).then(res => {

        // 拿到menuList
        store.commit("setMenuList", res.data.data.nav)

        // 拿到用户权限
        store.commit("setPermList", res.data.data.authoritys)

        // 动态绑定路由
        // 加入现有路由
        let newRoutes = router.options.routes

        //对导航数据进行for循环
        res.data.data.nav.forEach(menu => {
          if (menu.children) {
            //有子菜单则对每个子菜单进行如下操作
            menu.children.forEach(e => {

              // 转成路由
              let route = menuToRoute(e)

              // 把路由添加到路由管理中
              if (route) {
                newRoutes[0].children.push(route)
              }

            })
          }
        })

        router.addRoutes(newRoutes)

        //hasRoute置为true,表示已经获取过路由
        hasRoute = true
        store.commit("changeRouteStatus", hasRoute)
      })
    }


  next()
})

// 导航转成路由
const menuToRoute = (menu) => {
//一级菜单无component直接return空
  if (!menu.component) {
    return null
  }

  let route = {
    name: menu.name,
    path: menu.path,
    meta: {
      icon: menu.icon,
      title: menu.title
    }
  }
  route.component = () => import('@/views/' + menu.component +'.vue')

  return route
}




export default router
