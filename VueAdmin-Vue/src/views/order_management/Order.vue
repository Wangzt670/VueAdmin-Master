<template>
  <div>
    <!--按钮以及搜索栏-->
    <el-form :inline="true">
      <el-form-item>
        <el-input
            v-model="searchForm.orderstart"
            placeholder="开始时间"
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
          prop="id"
          label="订单编号"
          width="80px"
          :formatter="formatterId">
      </el-table-column>

      <el-table-column
          prop="orderstart"
          label="订单开始时间"
          width="160px">
      </el-table-column>

      <el-table-column
          prop="orderend"
          label="订单结束时间"
          width="160px">
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
          prop="cost"
          label="总价">
      </el-table-column>

      <el-table-column
          prop="icon"
          width="80px"
          label="操作">

        <template slot-scope="scope">
          <el-button type="text" @click="editHandle(scope.row.id)"v-if="hasAuth('ordman:order:update')">编辑</el-button>
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
  name: "Order",
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

      //校验规则
      editFormRules: {
        statu: [
          {required: true, message: '请选择状态', trigger: 'blur'}
        ],

      },

      //能否批量删除的状态量
      delBtlStatu: true,
      //批量删除数组
      multipleSelection: [],

    }
  },
  created() {
    this.getOrderList()
  },
  methods:{
    //更新以及查询
    getOrderList() {
      this.$axios.get("/ordman/order/list", {
        params: {
          orderstart: this.searchForm.orderstart,
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

          if(this.editForm.statu == 0){
            this.editForm.orderend = this.getCurrentTime()

            let hour = this.getHour(this.editForm.orderstart,this.editForm.orderend)

            this.editForm.cost = hour
          }

          this.$axios.post('/ordman/order/' + (this.editForm.id?'update' : 'save'), this.editForm)
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
      this.$axios.get('/ordman/order/info/' + id).then(res => {
        this.editForm = res.data.data
        if(this.editForm.statu != 1){
          this.$message({
            showClose: true,
            message: '订单已结束！',
            type: 'warning',
          });
        }else{
          this.dialogVisible = true
        }
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

    formatterId(rows, column){
      let str = ''+rows.id
      let idformat = str.padStart(7,"0")
      return idformat
    },

    getCurrentTime() {
      //获取当前时间并打印
      var _this = this;
      let yy = new Date().getFullYear();
      let mm = new Date().getMonth()+1;
      let dd = new Date().getDate();
      let hh = new Date().getHours();
      let mf = new Date().getMinutes()<10 ? '0'+new Date().getMinutes() : new Date().getMinutes();
      let ss = new Date().getSeconds()<10 ? '0'+new Date().getSeconds() : new Date().getSeconds();
      _this.gettime = yy+'-'+mm+'-'+dd+' '+hh+':'+mf+':'+ss;
      return _this.gettime
    },
    getHour(s1, s2) {
      var reDate = /\d{4}-\d{1,2}-\d{1,2}/;
      s1 = new Date((reDate.test(s1) ? s1 : '2023-1-1 ' + s1).replace(/-/g, '/'));
      s2 = new Date((reDate.test(s2) ? s2 : '2023-1-1 ' + s2).replace(/-/g, '/'));
      var ms = s2.getTime() - s1.getTime();
      if (ms < 0) return 0;
      return Math.ceil(ms / 1000 / 60 / 60); //小时
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