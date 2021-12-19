<template>
  <div style="width:100%; height:1000px">
    <div id="byteVCellId" style="height:1000px"></div>
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
  name: 'place-statistic',
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
    DrawChartsHeartMap () {
      // 基于准备好的dom，初始化echarts实例
      let myChartapp = this.$echarts.init(document.getElementById('byteVCellId'))

      let option = {
        title: {
          text: '走失老人地理位置热力图',
          x: 'center',
          textStyle: {
            fontSize: 33,
            color: '#28375f',
            fontWeight: 'bold',
            fontFamily: 'testFamily'
          }
        },
        tooltip: {
          trigger: 'item'
        },
        animation: false,
        bmap: {
          center: [108, 35],
          zoom: 6,
          roam: true
        },
        visualMap: {
          show: false,
          top: 'top',
          min: 0,
          max: 5,
          seriesIndex: 0,
          calculable: true,
          inRange: {
            color: ['blue', 'green', 'yellow', 'red']
          }
        },
        series: [
          {
            type: 'heatmap',
            coordinateSystem: 'bmap',
            data: this.locationCarsList,
            pointSize: 15,
            blurSize: 6
          }
        ]
      }
      // 使用刚指定的配置项和数据显示图表。
      myChartapp.setOption(option)

      let bmap = myChartapp.getModel().getComponent('bmap').getBMap()
      bmap.addControl(new BMap.MapTypeControl())
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
