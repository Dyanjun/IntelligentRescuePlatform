<template>
  <div>
    <div id="yearId" style="width: 90%; height:500px"></div>
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
import { getAge } from '@/api/statistic'

export default {
  name: 'age-statistic',
  data () {
    return {
      femaleList: [],
      maleList: [],
      allList: []
    }
  },
  mounted () {
    this.getCarLocationData()
  },
  methods: {
    getAge,
    DrawChartsHeartMap () {
      // 基于准备好的dom，初始化echarts实例
      let myChartapp = this.$echarts.init(document.getElementById('yearId'))

      let option = {
        title: {
          text: '年份统计',
          x: 'left',
          textStyle: {
            color: '#9c0505'
          }
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            crossStyle: {
              color: '#999'
            }
          }
        },
        toolbox: {
          feature: {
            dataView: { show: true, readOnly: false },
            magicType: { show: true, type: ['line', 'bar'] },
            restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        legend: {
          data: ['女性', '男性', '总数']
        },
        xAxis: [
          {
            type: 'category',
            data: ['2015', '2016', '2017', '2018', '2019', '2020', '2021'],
            axisPointer: {
              type: 'shadow'
            }
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '人次',
            min: 0,
            max: 100,
            interval: 20,
            axisLabel: {
              formatter: '{value}'
            }
          },
          {
            type: 'value',
            name: '人次',
            min: 0,
            max: 100,
            interval: 20,
            axisLabel: {
              formatter: '{value}'
            }
          }
        ],
        series: [
          {
            name: '女性',
            type: 'bar',
            data: [1, 0, 0, 0, 0, 0, 14]
          },
          {
            name: '男性',
            type: 'bar',
            data: [1, 0, 0, 1, 0, 0, 7]
          },
          {
            name: '总数',
            type: 'line',
            yAxisIndex: 1,
            data: [2, 0, 0, 1, 0, 0, 21]
          }
        ]
      }

      // 使用刚指定的配置项和数据显示图表。
      myChartapp.setOption(option)

      let bmap = myChartapp.getModel().getComponent('bmap').getBMap()
      bmap.addControl(new BMap.MapTypeControl())
    },
    getCarLocationData: function () {
      this.getAge().then((res) => {
        console.log(res)
        const femaleMap = res['female']
        this.femaleList = [femaleMap['60岁及以下'], femaleMap['61-65岁'], femaleMap['66-70岁'], femaleMap['71-75岁'], femaleMap['76-80岁'], femaleMap['81-85岁'], femaleMap['86岁及以上']]
        const maleMap = res['male']
        this.maleList = [maleMap['60岁及以下'], maleMap['61-65岁'], maleMap['66-70岁'], maleMap['71-75岁'], maleMap['76-80岁'], maleMap['81-85岁'], maleMap['86岁及以上']]
        const allMap = res['all']
        this.allList = [allMap['60岁及以下'], allMap['61-65岁'], allMap['66-70岁'], allMap['71-75岁'], allMap['76-80岁'], allMap['81-85岁'], allMap['86岁及以上']]
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
