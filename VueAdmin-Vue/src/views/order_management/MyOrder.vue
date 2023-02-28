<template>
  <div>
    <!--按钮以及搜索栏-->
    <el-form :inline="true">
      <el-form-item>
        <el-input
            v-model="searchForm.ordernum"
            placeholder="订单编号"
            clearable
        >
        </el-input>
      </el-form-item>

      <el-form-item>
        <el-button @click="getOrderList">搜索</el-button>
      </el-form-item>
    </el-form>

    <!--表单主体-->
    <el-table
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
        border
        stripe
        @selection-change="handleSelectionChange">

      <el-table-column
          type="selection"
          width="55">
      </el-table-column>

      <el-table-column
          prop="ordernum"
          label="订单编号">
      </el-table-column>

      <el-table-column
          prop="carnum"
          label="车牌号">
      </el-table-column>

      <el-table-column
          prop="villagename"
          label="小区">
      </el-table-column>

      <el-table-column
          prop="parknum"
          label="车位">
      </el-table-column>

      <el-table-column
          prop="lease"
          label="出租">
      </el-table-column>

      <el-table-column
          prop="rent"
          label="租用">
      </el-table-column>

      <el-table-column
          prop="statu"
          label="状态">
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.statu === 1" type="success">进行中</el-tag>
          <el-tag size="small" v-else-if="scope.row.statu === 0" type="info">已结束</el-tag>
        </template>
      </el-table-column>

      <el-table-column
          prop="icon"
          width="260px"
          label="操作">

        <template slot-scope="scope">
          <el-button type="text" @click="editHandle(scope.row.id)"v-if="hasAuth('ordman:myorder:updata')">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页组件-->
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="total, sizes, prev, pager, next, jumper"
        :page-sizes="[10, 20, 50, 100]"
        :current-page="current"
        :page-size="size"
        :total="total">
    </el-pagination>

    <!--编辑对话框-->
    <el-dialog
        title="编辑"
        :visible.sync="dialogVisible"
        width="600px"
        :before-close="handleClose">

      <el-form :model="editForm" :rules="editFormRules" ref="editForm" label-width="100px">

        <el-form-item label="状态"  prop="statu">
          <el-radio-group v-model="editForm.statu">
            <el-radio :label="1">进行中</el-radio>
            <el-radio :label="0">已结束</el-radio>
          </el-radio-group>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm('editForm')">取 消</el-button>
        <el-button type="primary" @click="submitForm('editForm')">确 定</el-button>
      </div>

    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "MyOrder",
  data(){
    return{
      searchForm:{},
      //分页组件参数声明
      total: 0,
      size: 10,
      current: 1,
      //新增或编辑对话框参数声明
      dialogVisible: false,
      editForm: {

      },

      tableData: [],
      userInfo: {},

      //校验规则
      editFormRules: {
        statu: [
          {required: true, message: '请选择状态', trigger: 'blur'}
        ],

      },
    }
  },
  created() {
    this.getOrderList()
    this.getUserInfo()
  },
  methods:{

    getUserInfo() {
      this.$axios.get("/ordman/myorder/getuserinfo").then(res => {
        this.userInfo = res.data.data
      })
    },

    //更新以及查询
    getOrderList() {
      this.$axios.get("/ordman/myorder/list", {
        params: {
          ordernum: this.searchForm.ordernum,
          current: this.current,
          size: this.size
        }
      }).then(res => {
        this.tableData = res.data.data.records
        this.size = res.data.data.size
        this.current = res.data.data.current
        this.total = res.data.data.total
      })
    },

    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.dialogVisible = false
      this.editForm = {}
    },

    handleClose() {
      this.resetForm('editForm')
    },

    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/ordman/myorder/' + (this.editForm.id?'update' : 'save'), this.editForm)
              .then(res => {
                this.$message({
                  showClose: true,
                  message: '操作成功！',
                  type: 'success',
                  onClose:() => {
                    this.getOrderList()
                  }
                });

                this.resetForm(formName)
              })
        } else {
          console.log('操作失败！');
          return false;
        }
      });
    },

    editHandle(id) {
      this.$axios.get('/ordman/myorder/info/' + id).then(res => {
        this.editForm = res.data.data
        this.dialogVisible = true
      })
    },

    //批量删除激活
    handleSelectionChange(val) {
      this.multipleSelection = val;

      this.delBtlStatu = val.length == 0
    },

    //分页方法设置
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.size = val
      this.getOrderList()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.current = val
      this.getOrderList()
    },
  }
}
</script>


<style scoped>
.el-pagination {
  float: right;
  margin-top: 22px;
}

</style>