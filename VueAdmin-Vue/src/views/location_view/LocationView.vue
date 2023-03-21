<template>
  <div class="map-maker-wrapper">

    <el-form :inline="true">
      <el-form-item>
        <el-input
            v-model="prekeyword"
            placeholder="地址"
            clearable
        >
        </el-input>
      </el-form-item>

      <el-form-item>
        <el-button @click="handleSearch">搜索</el-button>
      </el-form-item>
    </el-form>

    <baidu-map
        class="bm-view"
        :center="mapCenter"
        :zoom="mapZoom"
        :scroll-wheel-zoom="true"
        @ready="onReady"
    >
      <bm-scale anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-scale>
      <bm-navigation anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-navigation>
      <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_RIGHT" :showAddressBar="true" :autoLocation="true"></bm-geolocation>

      <template v-for="item in tableDataVillage">
        <bm-marker :position="{lng: item.lng, lat: item.lat}" @click="infoWindowOpen(item)"></bm-marker>
          <el-dialog
              :title= "dialogTitle"
              :visible.sync="infoVisible"
              width="1000px"
              :before-close="handleClose"
              >

            <!--表单主体-->
            <el-table
                ref="multipleTable"
                :data="tableDataPark"
                tooltip-effect="dark"
                style="width: 100%"
                border
                stripe
               >

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
                  label="状态"
                  width="70">
                <template slot-scope="scope">
                  <el-tag size="small" v-if="scope.row.statu === 1" type="success">空闲</el-tag>
                  <el-tag size="small" v-else-if="scope.row.statu === 0" type="danger">占用</el-tag>
                </template>
              </el-table-column>

              <el-table-column
                  prop="avastart,avaend"
                  label="可用时间段"
                  width="150">
                <template slot-scope="scope">{{scope.row.avastart}} - {{scope.row.avaend}}</template>
              </el-table-column>

              <el-table-column
                  prop="price"
                  label="价格/小时"
                  width="100">
              </el-table-column>

              <el-table-column
                  prop="remark"
                  label="描述">
              </el-table-column>

              <el-table-column
                  prop="icon"
                  width="100px"
                  label="操作">

                <template slot-scope="scope">
                  <el-button type="text" @click="OrderCreateHandle(scope.row.id)"v-if="hasAuth('locview:locview:save')">创建订单</el-button>
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


            <div slot="footer" class="dialog-footer">
              <el-button @click="infoWindowClose">关 闭</el-button>
            </div>
          </el-dialog>
      </template>

      <bm-local-search
          :keyword="keyword"
          :auto-viewport="true"
          :panel="false"
      ></bm-local-search>
    </baidu-map>



    <!--新增订单对话框-->
    <el-dialog
        title="新建订单"
        :visible.sync="OrderFormVisible"
        width="600px"
        :before-close="handleOrderFormClose">

      <el-form :model="OrderForm" :rules="OrderFormRules" ref="OrderForm" label-width="100px">

        <el-form-item label="订单编号"  prop="ordernum">
          <el-input v-model="OrderForm.orderstart" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="车辆" prop="carnum">
          <el-select v-model="OrderForm.carnum" autocomplete="off">
            <template v-for="item in tableDataCar">
              <el-option :label="item.carnum" :value="item.carnum"></el-option>
            </template>
          </el-select>
        </el-form-item>

        <el-form-item label="小区名称"  prop="villagename">
          <el-input v-model="OrderForm.villagename" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="车位编号"  prop="parknum">
          <el-input v-model="OrderForm.parknum" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="出租"  prop="lease">
          <el-input v-model="OrderForm.lease" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="租借"  prop="rent">
          <el-input v-model="OrderForm.rent" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleOrderFormClose">取 消</el-button>
        <el-button type="primary" @click="submitOrderForm('OrderForm')">确 定</el-button>
      </div>

    </el-dialog>



    </div>
</template>


<script>

export default {
  name: 'LoctionView',
  data() {
    return {
      BMap: null,
      map: null,
      mapZoom: 15,
      mapCenter: { lng: 106.53369, lat: 29.612911 },
      prekeyword: '',
      keyword: '',

      //分页组件参数声明
      total: 0,
      size: 10,
      current: 1,


      tableDataVillage: [],
      tableDataPark: [],

      infoVisible:false,
      currentVillageName:"",
      // dialogTitle:this.currentVillageName+"小区车位详情",
      dialogTitle:"",

      //创建订单
      OrderFormVisible:false,

      OrderFormRules: {
        carnum: [
          {required: true, message: '请选择车辆', trigger: 'blur'}
        ],
      },
      userInfo:{},
      tableDataCar:[],

      OrderForm:{

      },
    }
  },
  created() {
    this.getVillageList()
    this.getUserInfo()
  },
  methods: {


    onReady({ BMap, map }) {
      this.BMap = BMap
      this.map = map
    },
    handleSearch() {
      this.keyword = this.prekeyword
    },
    getVillageList(){
      this.$axios.get("/locview/locview/getvillagelist").then(res => {
        this.tableDataVillage = res.data.data.records
      })
    },


    infoWindowOpen(item){
      // console.log(item.lng)
      // console.log(item.lat)
      // console.log(item.villagename)
      this.currentVillageName = item.villagename
      this.dialogTitle = this.currentVillageName+" 小区车位详情"
      this.getParkList()

      this.infoVisible = true
    },
    infoWindowClose(){
      this.infoVisible = false
      this.currentVillageName = ""
    },
    handleClose(){
      this.infoVisible = false
      this.currentVillageName = ""
    },


/*    getParkList(){
      this.$axios.get("/locview/locview/getparklist", {
        params: {
          id:this.currentVillageID,
          statu:1,
          current: this.current,
          size: this.size
        }
      }).then(res => {
        this.tableDataPark = res.data.data.records
        this.size = res.data.data.size
        this.current = res.data.data.current
        this.total = res.data.data.total
      })
    },*/

    getParkList(){
      // console.log(this.currentVillageName)
      this.$axios.get("/locview/locview/getparklist",{
        params:{
          currentVillageName:this.currentVillageName,
          current: this.current,
          size: this.size
        }
      }).then(res => {
        this.tableDataPark = res.data.data.records
        this.size = res.data.data.size
        this.current = res.data.data.current
        this.total = res.data.data.total
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

//////////////////////////////创建订单子功能项//////////////
    getUserInfo() {
      this.$axios.get("/locview/locview/getuserinfo").then(res => {
        this.userInfo = res.data.data.records[0]
      })
    },

    getCarList() {
      this.$axios.get("/locview/locview/getcarlist").then(res => {
        this.tableDataCar = res.data.data.records
        this.size = res.data.data.size
        this.current = res.data.data.current
        this.total = res.data.data.total
      })
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

    OrderCreateHandle(id){
      this.getCarList()

      this.$axios.get('/locview/locview/parkinfo/' + id).then(res => {

        this.OrderForm.orderstart = this.getCurrentTime()
        this.OrderForm.parknum = res.data.data.parknum
        this.OrderForm.villagename = res.data.data.villagename
        this.OrderForm.lease = res.data.data.username


        this.OrderForm.rent = this.userInfo.username
        this.OrderFormVisible = true
      })
    },

    handleOrderFormClose(){
      this.OrderFormVisible = false
      this.OrderForm = {}
    },

    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.OrderFormVisible = false
      this.editForm = {}
    },

    submitOrderForm(formName){

      this.OrderForm.statu = 0

      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/locview/locview/save', this.OrderForm)
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
    }

  }
}
</script>

<style>

.bm-view {
  width: 100%;
  height: 1000px;
}

.el-pagination {
  float: left;
  margin-top: 22px;
}

</style>

