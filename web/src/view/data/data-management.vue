<template>
  <div>
    <Card style="height: 500px">
      <div class="left">
        <AgeStatistic></AgeStatistic>
      </div>
      <div class="right">
        <YearStatistic></YearStatistic>
      </div>
    </Card>
    <Card>
      <PlaceDataStatistic></PlaceDataStatistic>
    </Card>
  </div>
</template>

<script>
import PlaceDataStatistic from '@/view/data/place-data-statistic'
import AgeStatistic from '@/view/data/age-statistic'
import YearStatistic from '@/view/data/year-statistic'
export default {
  name: 'data-management',
  components: {
    PlaceDataStatistic,
    AgeStatistic,
    YearStatistic
  }
}
</script>

<style scoped>
  .left{
    float: left;
    width: 48%;
    border-right:2px solid #ebedf0;
  }
  .right{
    float: right;
    width: 50%;
  }
</style>
