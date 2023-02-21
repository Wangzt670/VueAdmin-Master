<template>
  <el-container>
    <el-aside width="200px">

      <SideMenu></SideMenu>

    </el-aside>

    <el-container>
      <el-header>
        <strong>私家车位共享平台app后端系统</strong>

        <div class="header-avatar">

          <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>

          <el-dropdown>
						<span class="el-dropdown-link">
<!--						{{userInfo.username}}<i class="el-icon-arrow-down el-icon&#45;&#45;right"></i>-->
						{{userInfo.username}}<i class="el-icon-arrow-down el-icon--right"></i>
						</span>
            <el-dropdown-menu slot="dropdown">
              <!--              <el-dropdown-item>
                              <router-link :to="{name: 'UserCenter'}">个人中心</router-link>
                            </el-dropdown-item>
                            <el-dropdown-item @click.native="logout">退出</el-dropdown-item>-->
              <el-dropdown-item>
                <router-link :to="{name: 'UserCenter'}">
                  个人中心
                </router-link>
              </el-dropdown-item>
              <el-dropdown-item @click.native="logout">
                退出
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>

      <el-main>
        <div style="margin: 0 15px;">
          <router-view/>
        </div>
      </el-main>

    </el-container>
  </el-container>
</template>

<script>
import SideMenu from "@/views/inc/SideMenu.vue";
export default {
  name: "Home",
  components:{
    SideMenu
  },
  data(){
    return{
      userInfo:{
        id:"",
        username:""
      }
    }
  },
  created() {
    this.getUserInfo()
  },
  methods:{
    getUserInfo(){
      this.$axios.get("/sys/userInfo").then(res => {
        this.userInfo = res.data.data
      })
    },
    logout() {
      this.$axios.post("/logout").then(res => {
        localStorage.clear()
        sessionStorage.clear()

        this.$store.commit("resetState")

        this.$router.push("/login")
      })
    }
  }
}
</script>

<style scoped>
.el-header{
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  line-height: 200px;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
/*  text-align: center;*/
/*  line-height: 500px;*/
}

.el-container {
  padding: 0;
  margin: 0;
  height: 100%;
}

.header-avatar {
  float: right;
  width: 150px;
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.el-dropdown-link {
  cursor: pointer;
}

a {
  text-decoration: none;
}

</style>