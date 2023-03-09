<template>

    <el-row type="flex" class="row-bg" justify="center">

      <el-col :xl="6" :lg="7">
        <h2>欢迎来到私家车位共享平台app后端系统</h2>
        <el-image :src="require('@/assets/login-car.jpeg')" style="height: 300px; width: 300px;"></el-image>
        <p>重庆大学 计算机学院</p>
        <p>王正霆 20194249</p>
      </el-col>

      <el-col :span="1">
        <el-divider direction="vertical"></el-divider>
      </el-col>

      <el-col :xl="6" :lg="7">
        <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="80px">
          <el-form-item label="用户名" prop="username" style="width: 380px;">
            <el-input v-model="loginForm.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password"  style="width: 380px;">
            <el-input v-model="loginForm.password" type="password"></el-input>
          </el-form-item>
          <el-form-item label="验证码" prop="code"  style="width: 380px;">
            <el-input v-model="loginForm.code"  style="width: 172px; float: left" maxlength="5"></el-input>
            <el-image :src="captchaImg" class="captchaImg" @click="getCaptcha"></el-image>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submitForm('loginForm')">登 录</el-button>
            <el-button @click="logonHandle">注 册</el-button>
          </el-form-item>
        </el-form>

        <!--注册对话框-->
        <el-dialog
            title="注册"
            :visible.sync="dialogVisible"
            width="600px"
            :before-close="handleClose">

          <el-form :model="logonForm" :rules="logonFormRules" ref="logonForm" label-width="100px">

            <el-form-item label="用户名" prop="username">
              <el-input v-model="logonForm.username" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="角色名称" prop="role">
              <el-select v-model="logonForm.role" autocomplete="off">
                <template v-for="item in tableDataRole">
                  <el-option :label="item.name" :value="item.id"></el-option>
                </template>
              </el-select>
            </el-form-item>

            <el-form-item label="邮箱"  prop="email">
              <el-input v-model="logonForm.email" autocomplete="off"></el-input>
            </el-form-item>

            <el-form-item label="手机号"  prop="phone">
              <el-input v-model="logonForm.phone" autocomplete="off"></el-input>
            </el-form-item>

          </el-form>

          <div slot="footer" class="dialog-footer">
            <el-button @click="resetForm('logonForm')">取 消</el-button>
            <el-button type="primary" @click="submitlogonForm('logonForm')">确 定</el-button>
          </div>

        </el-dialog>

      </el-col>
    </el-row>




</template>

<script>
import qs from 'qs'

export default {
  name: "Login",
  data() {
    return {
      loginForm: {
        username: '',
        password: '',
        code: '',
        token: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入验证码', trigger: 'blur' },
          { min: 5, max: 5, message: '长度为 5 个字符', trigger: 'blur' }
        ],
      },
      captchaImg: null,



      //注册参数
      dialogVisible:false,
      logonForm:[],
      tableDataRole:[],

      logonFormRules: {
        username: [
          {required: true, message: '请输入用户名称', trigger: 'blur'}
        ],
        role: [
          {required: true, message: '请选择角色名称', trigger: 'blur'}
        ],
      },

    };
  },
  methods: {


    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //提交表单
          this.$axios.post('/login?'+qs.stringify(this.loginForm)).then(res => {

            console.log(res)

            const jwt = res.headers['authorization']
            // 调用SET_TOKEN方法将jwt存储到应用store中
            this.$store.commit('SET_TOKEN', jwt)
            this.$router.push("/index")
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },

    //纯前端登录
/*    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          //提交表单
          this.$axios.post('/login',this.loginForm).then(res => {

            console.log(res)

            const jwt = res.headers['authorization']
            // 调用SET_TOKEN方法将jwt存储到应用store中
            this.$store.commit('SET_TOKEN', jwt)
            this.$router.push("/index")
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },*/

    getCaptcha() {
      this.$axios.get('/captcha').then(res => {

        console.log("/captcha")
        console.log(res)

        this.loginForm.token = res.data.data.token
        this.captchaImg = res.data.data.captchaImg
        this.loginForm.code = ''
      })
    },

    //注册方法
    getRoleList() {
      this.$axios.get("/login/getrolelist").then(res => {
        this.tableDataRole = res.data.data.records
      })
    },
    logonHandle(){
      this.getRoleList()
      this.dialogVisible=true
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.dialogVisible = false
      this.logonForm={}
    },
    handleClose() {
      this.resetForm('logonForm')
    },
    submitlogonForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.logonForm.statu = 1
          this.$axios.post('/login/logon' , this.logonForm)
              .then(res => {
                this.$message({
                  showClose: true,
                  message: '操作成功！',
                  type: 'success',
                  /*                onClose:() => {
                                    this.getUserList()
                                  }*/
                });

                this.resetForm(formName)

              })
        } else {
          console.log('操作失败！');
          return false;
        }
      });
    },

  },
  created() {
    this.getCaptcha()
  }
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

  .el-divider {
    height: 350px;
  }

  .captchaImg {
    float: left;
    margin-left: 8px;
    border-radius: 4px;
  }

</style>