<template>
  <div>
    <!--主体表单-->
    <el-form :inline="true">
      <el-form-item>
        <el-button type="primary" @click="dialogVisible = true" v-if="hasAuth('sys:menu:save')">新增</el-button>
      </el-form-item>
    </el-form>

    <el-table
        :data="tableData"
        style="width: 100%;margin-bottom: 20px;"
        row-key="id"
        border
        stripe
        default-expand-all
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}">

      <el-table-column
          prop="name"
          label="名称"
          sortable
          width="180">
      </el-table-column>

      <el-table-column
          prop="perms"
          label="权限编码"
          sortable
          width="180">
      </el-table-column>

      <el-table-column
          prop="icon"
          label="图标">
      </el-table-column>

      <el-table-column
          prop="type"
          label="类型">
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.type === 0">目录</el-tag>
          <el-tag size="small" v-else-if="scope.row.type === 1" type="success">菜单</el-tag>
          <el-tag size="small" v-else-if="scope.row.type === 2" type="info">按钮</el-tag>
        </template>
      </el-table-column>

      <el-table-column
          prop="path"
          label="菜单URL">
      </el-table-column>

      <el-table-column
          prop="component"
          label="菜单组件">
      </el-table-column>

      <el-table-column
          prop="icon"
          label="操作">

        <template slot-scope="scope">
          <el-button type="text" @click="editHandle(scope.row.id)"v-if="hasAuth('sys:menu:update')">编辑</el-button>
          <el-divider direction="vertical"></el-divider>

          <template>
            <el-popconfirm title="确定删除吗？" @confirm="delHandle(scope.row.id)">
              <el-button type="text" slot="reference" v-if="hasAuth('sys:menu:delete')">删除</el-button>
            </el-popconfirm>
          </template>

        </template>
      </el-table-column>

    </el-table>


    <!--新增/编辑窗口-->
    <el-dialog
        title="提示"
        :visible.sync="dialogVisible"
        width="600px"
        :before-close="handleClose">

      <el-form :model="editForm" :rules="editFormRules" ref="editForm" label-width="100px" class="demo-editForm">

        <el-form-item label="上级菜单" prop="parentId">
          <el-select v-model="editForm.parentId" placeholder="请选择上级菜单">
            <template v-for="item in tableData">
              <el-option :label="item.name" :value="item.id"></el-option>
              <template v-for="child in item.children">
                <el-option :label="child.name" :value="child.id">
                  <span>{{ "- " + child.name }}</span>
                </el-option>
              </template>
            </template>
          </el-select>
        </el-form-item>

        <el-form-item label="菜单名称" prop="name" label-width="100px">
          <el-input v-model="editForm.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="权限编码" prop="perms" label-width="100px">
          <el-input v-model="editForm.perms" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="图标" prop="icon" label-width="100px">
          <el-input v-model="editForm.icon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="菜单URL" prop="path" label-width="100px">
          <el-input v-model="editForm.path" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="菜单组件" prop="component" label-width="100px">
          <el-input v-model="editForm.component" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="类型" prop="type" label-width="100px">
          <el-radio-group v-model="editForm.type">
            <el-radio :label=0>目录</el-radio>
            <el-radio :label=1>菜单</el-radio>
            <el-radio :label=2>按钮</el-radio>
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

  name: "Menu",

  data(){
    return {
      dialogVisible: false,

      editForm: {

      },

      editFormRules: {
        parentId: [
          {required: true, message: '请选择上级菜单', trigger: 'blur'}
        ],
        name: [
          {required: true, message: '请输入名称', trigger: 'blur'}
        ],
        perms: [
          {required: true, message: '请输入权限编码', trigger: 'blur'}
        ],
        type: [
          {required: true, message: '请选择状态', trigger: 'blur'}
        ],
        orderNum: [
          {required: true, message: '请填入排序号', trigger: 'blur'}
        ],
        statu: [
          {required: true, message: '请选择状态', trigger: 'blur'}
        ]
      },

      tableData: []
    }
  },

  created() {
    this.getMenuTree()
  },

  methods: {
    getMenuTree() {
      this.$axios.get("/sys/menu/list").then(res => {
        this.tableData = res.data.data
      })
    },

    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/sys/menu/' + (this.editForm.id?'update' : 'save'), this.editForm)
              .then(res => {

                this.$message({
                  showClose: true,
                  message: '操作成功！',
                  type: 'success',
                  onClose:() => {
                    this.getMenuTree()
                    //更新逻辑是否有问题
                  }
                });

                this.resetForm('editForm')
              })
        } else {
          console.log('操作失败！');
          return false;
        }
      });
    },

    editHandle(id) {
      this.$axios.get('/sys/menu/info/' + id).then(res => {
        this.editForm = res.data.data

        this.dialogVisible = true
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

    delHandle(id) {
      this.$axios.post("/sys/menu/delete/" + id).then(res => {
        this.$message({
          showClose: true,
          message: '删除成功！',
          type: 'success',
          onClose:() => {
            this.getMenuTree()
          }
        });

      })
    }
  }
}
</script>


<style scoped>

</style>
