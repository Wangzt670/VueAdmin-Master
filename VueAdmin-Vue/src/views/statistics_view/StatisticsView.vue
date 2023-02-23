<template>
  <div>

    <el-row :gutter="20" type="flex">

      <el-col :span="7">
        <e-charts class="chart1" :option="option1"></e-charts>
      </el-col>

      <el-col :span="7">
        <e-charts class="chart2" :option="option2"></e-charts>
      </el-col>

    </el-row>


  </div>
</template>

<script>
export default {
  name: "StatisticsView",
  data(){
    return{
      //chart1
      Usertotal:0,
      Villagetotal:0,
      Parktotal:0,
      Cartotal:0,
      Ordertotal:0,

      //chart2
      tableDataPark:[],
      countstatu0:0,
      countstatu1:0,
    }
  },
  created() {
    //chart1
    this.getUserTotal()
    this.getVillageTotal()
    this.getParkTotal()
    this.getCarTotal()
    this.getOrderTotal()

    //chart2
    this.getParkList()
  },
  computed:{
    //chart1
    option1(){
      return{
        xAxis: {
          type: 'category',
          data: ['用户总数', '小区总数', '车位总数', '车辆总数', '订单总数']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [
              this.Usertotal,
              this.Villagetotal,
              this.Parktotal,
              this.Cartotal,
              this.Ordertotal,
            ],
            type: 'bar'
          }
        ]
      }
    },
    //chart2
    option2(){
      return{
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '5%',
          left: 'center'
        },
        series: [
          {
            name: '车位状态',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 40,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: [
              { value: this.countstatu0, name: '占用' },
              { value: this.countstatu1, name: '空闲' },
            ]
          }
        ]
      }
    },
  },
  methods:{
    //chart1
    getUserTotal(){
      this.$axios.get("/sta/staview/usertotal").then(res => {
        this.Usertotal = res.data.data.total
      })
    },
    getVillageTotal(){
      this.$axios.get("/sta/staview/villagetotal").then(res => {
        this.Villagetotal = res.data.data.total
      })
    },
    getParkTotal(){
      this.$axios.get("/sta/staview/parktotal").then(res => {
        this.Parktotal = res.data.data.total
      })
    },
    getCarTotal(){
      this.$axios.get("/sta/staview/cartotal").then(res => {
        this.Cartotal = res.data.data.total
      })
    },
    getOrderTotal(){
      this.$axios.get("/sta/staview/ordertotal").then(res => {
        this.Ordertotal = res.data.data.total
      })
    },

    //chart2
    getParkList(){
      this.$axios.get("/sta/staview/parklist").then(res => {
        this.tableDataPark = res.data.data.records
        this.countstatu0 =0
        this.countstatu1 =0
        for(var i = 0; i < this.tableDataPark.length; i++){

          if(this.tableDataPark[i]["statu"]==0){
            this.countstatu0+=1
          }
          else if(this.tableDataPark[i]["statu"]==1){
            this.countstatu1+=1
          }
        }
      })

    },
  }
}
</script>

<style scoped>
.chart1{
  height: 500px;
}

.chart2{
  height: 500px;
}
</style>
