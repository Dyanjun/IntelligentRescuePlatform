<template>
  <div style="width:100%; height:1000px">
    <div id="placeData" style="height:1000px"></div>
  </div>
</template>

<script>
import 'echarts/map/js/china.js'
import 'echarts/lib/component/title'
import 'echarts/lib/component/legend'
import 'echarts/lib/chart/heatmap'
import 'echarts/lib/component/toolbox'
import 'echarts/lib/component/tooltip'
import 'echarts/extension/bmap/bmap'
import { getLocation } from '@/api/statistic'

export default {
  name: 'place-data-statistic',
  data () {
    return {
      // 热力图
      locationCarsList: []
    }
  },
  mounted () {
    this.getCarLocationData()
  },
  methods: {
    getLocation,
    DrawChartsHeartMap: function () {
      // 基于准备好的dom，初始化echarts实例
      let myChartapp = this.$echarts.init(document.getElementById('placeData'))
      let option = {
        title: {
          text: '省份统计',
          x: 'center',
          textStyle: {
            color: '#9c0505'
          }
        },
        tooltip: {
          trigger: 'item',
          formatter: '{b}<br/>{c} (例)'
        },
        toolbox: {
          show: true,
          orient: 'vertical',
          left: 'right',
          top: 'center',
          feature: {
            dataView: { readOnly: false },
            restore: {},
            saveAsImage: {}
          }
        },
        // 数据和类型
        series: [{
          type: 'map',
          map: 'china',
          label: {
            show: true,
            color: 'red',
            fontSize: 10
          },
          // 地图大小倍数
          zoom: 1.2,
          data: [
            { name: '北京', value: 40000 },
            { name: '山西', value: 30000 },
            { name: '内蒙古', value: 5000 },
            { name: '青海', value: 7000 },
            { name: '河北', value: 25000 },
            { name: '广东', value: 10000 },
            { name: '黑龙江', value: 40000 },
            { name: '南海诸岛', value: 20000 }
          ]
        }],
        visualMap: {
          min: 0,
          max: 50000,
          text: ['High', 'Low'],
          realtime: false,
          calculable: true,
          inRange: {
            color: ['lightskyblue', 'yellow', 'orangered']
          }
        }
      }
      // 指定数据项和数据显示
      myChartapp.setOption(option)
    },
    getCarLocationData () {
      this.getLocation().then((res) => {
        this.locationCarsList = res
        this.DrawChartsHeartMap()
      }).catch((error) => {
        console.log(error)
      })
    }
  }
}
</script>

<style scoped>

</style>
