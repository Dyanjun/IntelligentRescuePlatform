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
            { name: '江苏', value: 1 },
            { name: '安徽', value: 2 },
            { name: '湖北', value: 2 },
            { name: '上海', value: 19 }
          ]
        }],
        visualMap: {
          min: 0,
          max: 50,
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
