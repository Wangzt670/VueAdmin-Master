<template>
  <div>
    <el-header>
      <strong>欢迎使用私家车位共享平台app！</strong>
    </el-header>

    <baidu-map
        class="bm-view"
        :center="mapCenter"
        :zoom="mapZoom"
        :scroll-wheel-zoom="true"
        @ready="onReady"
    >

      <template v-for="item in tableDataVillage">
        <bm-marker :position="{lng: item.lng, lat: item.lat}"></bm-marker>
      </template>
    </baidu-map>
  </div>
</template>

<script>
export default {
  name: "Index",
  data(){
    return{
      BMap: null,
      map: null,
      mapZoom: 6,
      mapCenter: { lng: 108.9258, lat: 34.5459 },

      tableDataVillage: [],

    }
  },
  created() {
    this.getVillageList()
  },

  methods:{
    onReady({ BMap, map }) {
      this.BMap = BMap
      this.map = map
    },

    getVillageList(){
      this.$axios.get("/index/getvillagelist").then(res => {
        this.tableDataVillage = res.data.data.records
      })
    },

  }

}
</script>

<style scoped>

.bm-view {
  width: 100%;
  height: 1000px;
}

.el-header{
  color: #333;
  text-align: center;
  line-height: 60px;
}

</style>