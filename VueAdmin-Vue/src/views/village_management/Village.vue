<template>
  <div>
    <!--按钮以及搜索栏-->
    <el-form :inline="true">
      <el-form-item>
        <el-input
            v-model="searchForm.villagename"
            placeholder="小区名称"
            clearable
        >
        </el-input>
      </el-form-item>

      <el-form-item>
        <el-button @click="getVillageList">搜索</el-button>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="dialogVisible = true"v-if="hasAuth('vilman:village:save')">新增</el-button>
      </el-form-item>
      <el-form-item>
        <el-popconfirm title="确定批量删除吗？" @confirm="delHandle(null)">
          <el-button type="danger" slot="reference" :disabled="delBtlStatu"v-if="hasAuth('vilman:village:delete')">批量删除</el-button>
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
          prop="villagename"
          label="小区名称">
      </el-table-column>

      <el-table-column
          prop="keyword"
          label="地址">
      </el-table-column>

      <el-table-column
          prop="lng"
          label="经度">
      </el-table-column>

      <el-table-column
          prop="lat"
          label="纬度">
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
          prop="remark"
          label="描述">
      </el-table-column>

      <el-table-column
          prop="icon"
          width="260px"
          label="操作">

        <template slot-scope="scope">
          <el-button type="text" @click="editHandle(scope.row.id)"v-if="hasAuth('vilman:village:update')">编辑</el-button>

          <el-divider direction="vertical"></el-divider>

          <template>
            <el-popconfirm title="确定删除吗？" @confirm="delHandle(scope.row.id)">
              <el-button type="text" slot="reference"v-if="hasAuth('vilman:village:delete')">删除</el-button>
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
        width="1000px"
        :before-close="handleClose">

      <el-form :model="editForm" :rules="editFormRules" ref="editForm" label-width="100px">

        <el-form-item label="小区名称"  prop="villagename">
          <el-input v-model="editForm.villagename" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="地址"  prop="keyword">
          <el-input v-model="prekeyword" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button @click="handleSearch">检索地址</el-button>
          <el-alert
              title="请点击地图中小区位置获取经纬度"
              :closable="false"
              type="info"
              style="line-height: 12px;"
          ></el-alert>
        </el-form-item>

        <el-form-item>
          <baidu-map
              class="bm-view"
              :center="mapCenter"
              :zoom="mapZoom"
              :scroll-wheel-zoom="true"
              @ready="onReady"
              @click="getClickInfo"
          >
            <bm-local-search
                :keyword="keyword"
                :auto-viewport="true"
                :panel="false"
            ></bm-local-search>
          </baidu-map>
        </el-form-item>

        <el-form-item label="经度"  prop="lng">
          <el-input v-model="lng" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="纬度"  prop="lat">
          <el-input v-model="lat" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="状态"  prop="statu">
          <el-radio-group v-model="editForm.statu">
            <el-radio :label="1">正常</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
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
  name: "Village",
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
      tableDataUser: [],

      //校验规则
      editFormRules: {
        villagename: [
          {required: true, message: '请输入小区名称', trigger: 'blur'}
        ],
        lng: [
          {required: true, message: '请点击地图获取经纬度', trigger: 'blur'}
        ],
        lat: [
          {required: true, message: '请点击地图获取经纬度', trigger: 'blur'}
        ],
        statu: [
          {required: true, message: '请选择状态', trigger: 'blur'}
        ]
      },

      //能否批量删除的状态量
      delBtlStatu: true,
      //批量删除数组
      multipleSelection: [],

      //地图参数
      BMap: null,
      map: null,
      mapZoom: 15,
      mapCenter: { lng: 116.404, lat: 39.915 },
      center: {lng: 116.404, lat: 39.915},
      prekeyword: '',
      keyword: '',
      lng:null,
      lat:null,
    }
  },
  created() {
    this.getVillageList()

  },
  methods:{
    //更新以及查询
    getVillageList() {
      this.$axios.get("/vilman/village/list", {
        params: {
          villagename: this.searchForm.villagename,
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
      this.lng = null
      this.lat = null
      this.prekeyword =''
    },

    handleClose() {
      this.resetForm('editForm')
    },

    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/vilman/village/' + (this.editForm.id?'update' : 'save'), this.editForm)
              .then(res => {

                this.$message({
                  showClose: true,
                  message: '操作成功！',
                  type: 'success',
                  onClose:() => {
                    this.getVillageList()
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
      this.$axios.get('/vilman/village/info/' + id).then(res => {
        this.editForm = res.data.data
        this.prekeyword = this.editForm.keyword
        this.lng=this.editForm.lng
        this.lat=this.editForm.lat
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

      this.$axios.post("/vilman/village/delete", ids).then(res => {
        this.$message({
          showClose: true,
          message: '删除成功！',
          type: 'success',
          onClose:() => {
            this.getVillageList()
          }
        });
      })
    },

    //分页方法设置
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.size = val
      this.getVillageList()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.current = val
      this.getVillageList()
    },

    //地图初始化以及地图功能
    onReady({ BMap, map }) {
      this.BMap = BMap
      this.map = map
    },

    handleSearch() {
/*      this.keyword = "重庆市渝北区锦上华庭"*/
      this.keyword = this.prekeyword
      this.editForm.keyword = this.prekeyword
/*      this.$axios.get('/vilman/village/info/' + id).then(res => {
        this.editForm = res.data.data

        this.dialogVisible = true
      })*/
    },

    getClickInfo(e) {
      this.lng = e.point.lng
      this.lat = e.point.lat
      this.editForm.lng = e.point.lng
      this.editForm.lat = e.point.lat
    }
  }
}
</script>

<style scoped>

.el-pagination {
  float: right;
  margin-top: 22px;
}

.bm-view {
  width: 100%;
  height: 500px;
}

</style>