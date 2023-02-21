<template>
  <el-menu
      default-active="2"
      class="el-menu-vertical-demo"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b">

    <router-link to="/index">
    <el-menu-item index="0">
      <template slot="title">
        <i class="el-icon-s-home"></i>
        <span slot="title">首页</span>
      </template>
    </el-menu-item>
    </router-link>

<!--    <el-submenu index="1">
      <template slot="title">
        <i class="el-icon-s-tools"></i>
        <span>系统管理</span>
      </template>

      <router-link to="/sys/users">
      <el-menu-item index="1-1">
        <template slot="title">
          <i class="el-icon-s-custom"></i>
          <span slot="title">用户管理</span>
        </template>
      </el-menu-item>
      </router-link>

      <router-link to="/sys/roles">
      <el-menu-item index="1-2">
        <template slot="title">
          <i class="el-icon-rank"></i>
          <span slot="title">角色管理</span>
        </template>
      </el-menu-item>
      </router-link>

      <router-link to="/sys/menus">
      <el-menu-item index="1-3">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span slot="title">菜单管理</span>
        </template>
      </el-menu-item>
      </router-link>
    </el-submenu>

    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-house"></i>
        <span>小区管理</span>
      </template>

      <el-menu-item index="2-1">
        <template slot="title">
          <i class="el-icon-view"></i>
          <span slot="title">小区概览</span>
        </template>
      </el-menu-item>

      <el-menu-item index="2-2">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span slot="title">我的小区</span>
        </template>
      </el-menu-item>

      <el-menu-item index="2-3">
        <template slot="title">
          <i class="el-icon-plus"></i>
          <span slot="title">新增小区</span>
        </template>
      </el-menu-item>
    </el-submenu>

    <el-submenu index="3">
      <template slot="title">
        <i class="el-icon-full-screen"></i>
        <span>车位管理</span>
      </template>

      <el-menu-item index="3-1">
        <template slot="title">
          <i class="el-icon-view"></i>
          <span slot="title">车位概览</span>
        </template>
      </el-menu-item>

      <el-menu-item index="3-2">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span slot="title">我的车位</span>
        </template>
      </el-menu-item>

      <el-menu-item index="3-3">
        <template slot="title">
          <i class="el-icon-plus"></i>
          <span slot="title">新增车位</span>
        </template>
      </el-menu-item>
    </el-submenu>

    <el-submenu index="4">
      <template slot="title">
        <i class="el-icon-truck"></i>
        <span>车辆管理</span>
      </template>

      <el-menu-item index="4-1">
        <template slot="title">
          <i class="el-icon-view"></i>
          <span slot="title">车辆概览</span>
        </template>
      </el-menu-item>

      <el-menu-item index="4-2">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span slot="title">我的车辆</span>
        </template>
      </el-menu-item>

      <el-menu-item index="4-3">
        <template slot="title">
          <i class="el-icon-plus"></i>
          <span slot="title">新增车辆</span>
        </template>
      </el-menu-item>
    </el-submenu>

    <el-submenu index="5">
      <template slot="title">
        <i class="el-icon-document"></i>
        <span>订单管理</span>
      </template>

      <el-menu-item index="5-1">
        <template slot="title">
          <i class="el-icon-view"></i>
          <span slot="title">订单概览</span>
        </template>
      </el-menu-item>

      <el-menu-item index="5-2">
        <template slot="title">
          <i class="el-icon-menu"></i>
          <span slot="title">我的订单</span>
        </template>
      </el-menu-item>

      <el-menu-item index="5-3">
        <template slot="title">
          <i class="el-icon-plus"></i>
          <span slot="title">新增订单</span>
        </template>
      </el-menu-item>
    </el-submenu>

    <router-link to="/loc/LocationView">
    <el-menu-item index="6">
      <template slot="title">
        <i class="el-icon-location-outline"></i>
        <span>定位显示</span>
      </template>
    </el-menu-item>
    </router-link>

    <el-menu-item index="7">
      <template slot="title">
        <i class="el-icon-s-data"></i>
        <span>数据统计</span>
      </template>
    </el-menu-item>-->

    <el-submenu :index="menu.name" v-for="menu in menuList">
      <template slot="title">
        <i :class="menu.icon"></i>
        <span>{{menu.title}}</span>
      </template>

      <router-link :to="item.path" v-for="item in menu.children">
        <el-menu-item :index="item.name">
          <template slot="title">
            <i :class="item.icon"></i>
            <span slot="title">{{item.title}}</span>
          </template>
        </el-menu-item>
      </router-link>
    </el-submenu>

  </el-menu>
</template>

<script>
export default {
  name: "SideMenu",
  data(){
    return{
/*        {
          name: 'SysManga',
          title: '系统管理',
          icon: 'el-icon-s-tools',
          /!*component: '',*!/
          path: '',
          children: [
            {
              name: 'SysUser',
              title: '用户管理',
              icon: 'el-icon-s-custom',
              path: '/sys/users',
            /!*  component: 'sys/User',*!/
              children: []
            },
            {
              name: 'SysRole',
              title: '角色管理',
              icon: 'el-icon-rank',
              path: '/sys/roles',
              component: 'sys/Role',
              children: []
            },
            {
              name: 'SysMenu',
              title: '菜单管理',
              icon: 'el-icon-menu',
              path: '/sys/menus',
            /!*  component: 'sys/Menu',*!/
              children: []
            }
          ]
        },
        {
          name: 'LocationView',
          title: '定位显示',
          icon: 'el-icon-location-outline',
          path: '',
          /!*component: '',*!/
          children: [
            {
              name: 'LocView',
              title: '定位显示',
              icon: 'el-icon-location-outline',
              path: '/loc/LocationView',
              /!*component: '',*!/
              children: []
            }
          ]
        }*/
    }
  },
  computed:  {
    menuList: {//动态监控菜单栏变化
      get() {
        return this.$store.state.menus.menuList
      }
    }
  }
}
</script>

<style scoped>
.el-menu-vertical-demo{
  height: 100%;
}
</style>