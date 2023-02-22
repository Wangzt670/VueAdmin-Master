
<!--
<template>
  <baidu-map :center="center" :zoom="zoom" @ready="handler" style="height:1080px" @click="getClickInfo" :scroll-wheel-zoom='true'>
  </baidu-map>
</template>
<script>
export default {
  name: 'LoctionView',
  data() {
    return {
      center: {lng: 109.45744048529967, lat: 36.49771311230842},
      zoom: 13
    }
  },
  methods: {
    handler({BMap, map}) {
      var point = new BMap.Point(109.49926175379778, 36.60449676862417)
      map.centerAndZoom(point, 13)
      var marker = new BMap.Marker(point) // 创建标注
      map.addOverlay(marker) // 将标注添加到地图中
      var circle = new BMap.Circle(point, 6, {
        strokeColor: 'Red',
        strokeWeight: 6,
        strokeOpacity: 1,
        Color: 'Red',
        fillColor: '#f03'
      })
      map.addOverlay(circle)
    },
    getClickInfo(e) {
      console.log(e.point.lng)
      console.log(e.point.lat)
      this.center.lng = e.point.lng
      this.center.lat = e.point.lat
    }
  }
}
</script>

<style>

</style>-->

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
        <bm-marker :position="{lng: item.lng, lat: item.lat}"></bm-marker>
      </template>

      <bm-local-search
          :keyword="keyword"
          :auto-viewport="true"
          :panel="false"
      ></bm-local-search>
    </baidu-map>

    </div>
</template>


<script>
export default {
  name: 'MapMaker',
  data() {
    return {
      BMap: null,
      map: null,
      mapZoom: 15,
      mapCenter: { lng: 106.53369, lat: 29.612911 },
      prekeyword: '',
      keyword: '',
      tableDataVillage: [],
      lnglist:[],
      latlist:[],
    }
  },
  created() {
    this.getVillageList()
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
  }
}
</script>

<style>
.bm-view {
  width: 100%;
  height: 1000px;
}

</style>

