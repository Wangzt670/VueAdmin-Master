<template>
  <div>
    <!--按钮以及搜索栏-->
    <el-form :inline="true">
      <el-form-item>
        <el-input
            v-model="searchForm.parknum"
            placeholder="车位编号"
            clearable
        >
        </el-input>
      </el-form-item>

      <el-form-item>
        <el-button @click="getParkList">搜索</el-button>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="dialogVisible = true"v-if="hasAuth('parkman:park:save')">新增</el-button>
      </el-form-item>
      <el-form-item>
        <el-popconfirm title="确定批量删除吗？" @confirm="delHandle(null)">
          <el-button type="danger" slot="reference" :disabled="delBtlStatu"v-if="hasAuth('parkman:park:delete')">批量删除</el-button>
        </el-popconfirm>
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
          prop="parknum"
          label="车位编号">
      </el-table-column>

      <el-table-column
          prop="villagename"
          label="小区">
      </el-table-column>

      <el-table-column
          prop="username"
          label="用户名">
      </el-table-column>

      <el-table-column
          prop="statu"
          label="状态">
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.statu === 1" type="success">空闲</el-tag>
          <el-tag size="small" v-else-if="scope.row.statu === 0" type="danger">禁用</el-tag>
          <el-tag size="small" v-else-if="scope.row.statu === 2" type="info">占用</el-tag>
        </template>
      </el-table-column>

      <el-table-column
          prop="price"
          label="价格/小时">
      </el-table-column>

      <el-table-column
          prop="remark"
          label="描述">
      </el-table-column>

      <el-table-column
          prop="icon"
          width="260px"
          label="操作">

        <template slot-scope="scope">
          <el-button type="text" @click="editHandle(scope.row.id)"v-if="hasAuth('parkman:mypark:update')">编辑</el-button>

          <el-divider direction="vertical"></el-divider>

          <template>
            <el-popconfirm title="确定删除吗？" @confirm="delHandle(scope.row.id)">
              <el-button type="text" slot="reference"v-if="hasAuth('parkman:mypark:delete')">删除</el-button>
            </el-popconfirm>
          </template>

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

    <!--新增或编辑对话框-->
    <el-dialog
        title="新建/编辑"
        :visible.sync="dialogVisible"
        width="600px"
        :before-close="handleClose">

      <el-form :model="editForm" :rules="editFormRules" ref="editForm" label-width="100px">

        <el-form-item label="车位编号"  prop="parknum">
          <el-input v-model="editForm.parknum" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="小区" prop="villagename">
          <el-select v-model="editForm.villagename" autocomplete="off">
            <template v-for="item in tableDataVillage">
              <el-option :label="item.villagename" :value="item.id"></el-option>
            </template>
          </el-select>
        </el-form-item>

        <el-form-item label="用户名" prop="username">
          <el-select v-model="editForm.username" autocomplete="off">
            <el-option :label="userInfo.username" :value="userInfo.id"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="状态"  prop="statu">
          <el-radio-group v-model="editForm.statu">
            <el-radio :label="0">禁用</el-radio>
            <el-radio :label="1">空闲</el-radio>
            <el-radio :label="2">占用</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="价格/小时"  prop="price">
          <el-input v-model.number="editForm.price" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="描述"  prop="remark">
          <el-input v-model="editForm.remark" autocomplete="off"></el-input>
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
  name: "MyPark",
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
      tableDataVillage: [],

      //校验规则
      editFormRules: {
        parknum: [
          {required: true, message: '请输入车位编号', trigger: 'blur'}
        ],
        villagename: [
          {required: true, message: '请选择小区名', trigger: 'blur'}
        ],
        username: [
          {required: true, message: '请选择用户名', trigger: 'blur'}
        ],
        statu: [
          {required: true, message: '请选择状态', trigger: 'blur'}
        ],
        price: [
          {required: true, message: '请输入价格', trigger: 'blur'},
          { type:"number" ,min: 0, max: 8, message: '请输入合适价格(0-8元/小时)', trigger: 'blur' }
        ],
      },

      //能否批量删除的状态量
      delBtlStatu: true,
      //批量删除数组
      multipleSelection: [],

    }
  },
  created() {
    this.getParkList()
    this.getUserInfo()
    this.getVillageList()
  },
  methods:{

    getUserInfo() {
      this.$axios.get("/parkman/mypark/getuserinfo").then(res => {
        this.userInfo = res.data.data
      })
    },

    getVillageList() {
      this.$axios.get("/parkman/mypark/getvillagelist").then(res => {
        this.tableDataVillage = res.data.data.records
      })
    },

    //更新以及查询
    getParkList() {
      this.$axios.get("/parkman/mypark/list", {
        params: {
          parknum: this.searchForm.parknum,
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
          this.$axios.post('/parkman/mypark/' + (this.editForm.id?'update' : 'save'), this.editForm)
              .then(res => {

                this.$message({
                  showClose: true,
                  message: '操作成功！',
                  type: 'success',
                  onClose:() => {
                    this.getParkList()
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
      this.$axios.get('/parkman/mypark/info/' + id).then(res => {
        this.editForm = res.data.data

        this.dialogVisible = true
      })
    },

    //批量删除激活
    handleSelectionChange(val) {
      this.multipleSelection = val;

      this.delBtlStatu = val.length == 0
    },

    //删除以及批量删除
    delHandle(id) {
      //记录勾选项id
      var ids = []

      if (id) {
        ids.push(id)
      } else {
        this.multipleSelection.forEach(row => {
          ids.push(row.id)
        })
      }

      this.$axios.post("/parkman/mypark/delete", ids).then(res => {
        this.$message({
          showClose: true,
          message: '删除成功！',
          type: 'success',
          onClose:() => {
            this.getParkList()
          }
        });
      })
    },

    //分页方法设置
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.size = val
      this.getParkList()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.current = val
      this.getParkList()
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