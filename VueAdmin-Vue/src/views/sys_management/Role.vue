<template>
  <div>
    <!--按钮以及搜索栏-->
    <el-form :inline="true">
      <el-form-item>
        <el-input
            v-model="searchForm.name"
            placeholder="名称"
            clearable
        >
        </el-input>
      </el-form-item>

      <el-form-item>
        <el-button @click="getRoleList">搜索</el-button>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="dialogVisible = true" v-if="hasAuth('sys:role:save')">新增</el-button>
      </el-form-item>

      <el-form-item>
        <el-popconfirm title="确定批量删除吗？" @confirm="delHandle(null)">
          <el-button type="danger" slot="reference" :disabled="delBtlStatu"v-if="hasAuth('sys:role:delete')">批量删除</el-button>
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
          prop="name"
          label="名称"
          width="120">
      </el-table-column>

      <el-table-column
          prop="code"
          label="唯一编码"
          show-overflow-tooltip>
      </el-table-column>

      <el-table-column
          prop="remark"
          label="描述"
          show-overflow-tooltip>
      </el-table-column>

      <el-table-column
          prop="statu"
          label="状态">
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.statu === 1" type="success">正常</el-tag>
          <el-tag size="small" v-else-if="scope.row.statu === 0" type="danger">禁用</el-tag>
        </template>
      </el-table-column>

      <el-table-column
          prop="icon"
          label="操作">

        <template slot-scope="scope">
          <el-button type="text" @click="permHandle(scope.row.id)">分配权限</el-button>

          <el-divider direction="vertical"></el-divider>

          <el-button type="text" @click="editHandle(scope.row.id)"v-if="hasAuth('sys:role:update')">编辑</el-button>

          <el-divider direction="vertical"></el-divider>

          <template>
            <el-popconfirm title="确定删除吗？" @confirm="delHandle(scope.row.id)">
              <el-button type="text" slot="reference"v-if="hasAuth('sys:role:delete')">删除</el-button>
            </el-popconfirm>
          </template>
        </template>

      </el-table-column>
    </el-table>

    <!--分页组件-->
        <!--page-sizes每页数据数量选择-->
        <!--current-page当前页-->
        <!--page-size每页数据数量-->
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

      <el-form :model="editForm" :rules="editFormRules" ref="editForm" label-width="100px" class="demo-editForm">

        <el-form-item label="角色名称" prop="name" label-width="100px">
          <el-input v-model="editForm.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="唯一编码" prop="code" label-width="100px">
          <el-input v-model="editForm.code" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="描述" prop="remark" label-width="100px">
          <el-input v-model="editForm.remark" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="状态" prop="statu" label-width="100px">
          <el-radio-group v-model="editForm.statu">
            <el-radio :label=1>正常</el-radio>
            <el-radio :label=0>禁用</el-radio>
          </el-radio-group>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm('editForm')">取 消</el-button>
        <el-button type="primary" @click="submitForm('editForm')">确 定</el-button>
      </div>

    </el-dialog>

    <!--分配权限对话框-->
    <el-dialog
        title="分配权限"
        :visible.sync="permDialogVisible"
        width="600px">

      <el-form :model="permForm">

        <el-tree
            :data="permTreeData"
            show-checkbox
            ref="permTree"
            :default-expand-all=true
            node-key="id"
            :check-strictly=true
            :props="defaultProps">
        </el-tree>

      </el-form>

      <span slot="footer" class="dialog-footer">
			    <el-button @click="permDialogVisible = false">取 消</el-button>
			    <el-button type="primary" @click="submitPermFormHandle('permForm')">确 定</el-button>
			</span>

    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "Role",

  data() {
    return {
      searchForm: {},

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
        name: [
          {required: true, message: '请输入角色名称', trigger: 'blur'}
        ],
        code: [
          {required: true, message: '请输入唯一编码', trigger: 'blur'}
        ],
        statu: [
          {required: true, message: '请选择状态', trigger: 'blur'}
        ]
      },

      //能否批量删除的状态量
      delBtlStatu: true,
      //批量删除数组
      multipleSelection: [],

      //分配权限对话框参数声明
      permDialogVisible: false,
      permForm: {},
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      permTreeData: []

    }
  },

  created() {
    this.getRoleList()

    this.$axios.get('/sys/menu/list').then(res => {
      this.permTreeData = res.data.data
    })
  },

  methods: {
    //更新以及查询
    getRoleList() {
      this.$axios.get("/sys/role/list", {
        params: {
          name: this.searchForm.name,
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
          this.$axios.post('/sys/role/' + (this.editForm.id?'update' : 'save'), this.editForm)
              .then(res => {

                this.$message({
                  showClose: true,
                  message: '操作成功！',
                  type: 'success',
                  onClose:() => {
                    this.getRoleList()
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
      this.$axios.get('/sys/role/info/' + id).then(res => {
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

      this.$axios.post("/sys/role/delete", ids).then(res => {
        this.$message({
          showClose: true,
          message: '删除成功！',
          type: 'success',
          onClose:() => {
            this.getRoleList()
          }
        });
      })
    },

    //分页方法设置
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.size = val
      this.getRoleList()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.current = val
      this.getRoleList()
    },

    //分配权限功能
    permHandle(id) {
      this.permDialogVisible = true

      this.$axios.get("/sys/role/info/" + id).then(res => {

        this.$refs.permTree.setCheckedKeys(res.data.data.menuIds)
        this.permForm = res.data.data
      })
    },

    submitPermFormHandle(formName) {
      var menuIds = this.$refs.permTree.getCheckedKeys()

      this.$axios.post('/sys/role/perm/' + this.permForm.id, menuIds).then(res => {
        this.$message({
          showClose: true,
          message: '操作成功!',
          type: 'success',
          onClose:() => {
            this.getRoleList()
          }
        });
        this.permDialogVisible = false
        this.resetForm(formName)
      })
    },
  }
}
</script>

<style scoped>
/*分页组件样式*/
.el-pagination {
  float: right;
  margin-top: 22px;
}
</style>