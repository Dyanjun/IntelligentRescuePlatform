<template>
  <Card>
    <Row>
      <paged-table
        :loading="loading"
        :columns="columns"
        :data-source="auditCaseList"
        :total="auditCaseList.length"
        style="margin-bottom: 50px"
      />
    </Row>
  </Card>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import PagedTable from '_c/paged-table/paged-table.vue'
export default {
  name: 'case-management',
  components: {
    PagedTable
  },
  data () {
    return {
      loading: true,
      columns: [
        {
          title: '丢失时间',
          key: 'lost_time'
        },
        {
          title: '紧急程度',
          key: 'emergency_level'
        },
        {
          title: '描述',
          key: 'description'
        },
        {
          title: '失踪地址',
          key: 'place'
        },
        {
          title: '操作',
          key: 'operation',
          render: (h, { row }) => {
            const detailButton = h('Button',
              {
                props: {
                  size: 'small',
                  type: 'primary'
                },
                style: {
                  marginRight: '1%'
                },
                on: {
                  click: () => {
                    this.showDetail(row)
                  }
                }
              },
              '查看详情'
            )
            const publishButton = h('Button',
              {
                props: {
                  size: 'small',
                  type: 'primary'
                },
                style: {
                  marginRight: '1%'
                },
                on: {
                  click: () => {
                    this.publish(row.id)
                  }
                }
              },
              '发布案件'
            )
            const rejectButton = h('Button',
              {
                props: {
                  size: 'small',
                  type: 'primary'
                },
                style: {
                  marginRight: '1%'
                },
                on: {
                  click: () => {
                    this.reject(row.id)
                  }
                }
              },
              '驳回案件'
            )
            const buttons = [detailButton, publishButton, rejectButton]
            return h('div', buttons)
          }
        }
      ]
    }
  },
  mounted () {
    this.refresh()
  },
  computed: mapState({
    auditCaseList: (state) => state.lostCase.auditCaseList
  }),
  methods: {
    ...mapActions(['getAuditCaseAction', 'publishCaseAction', 'rejectCaseAction']),
    refresh () {
      this.loading = true
      this.getAuditCaseAction().then(() => {
        this.loading = false
      })
    },
    showDetail (item) {

    },
    publish (id) {
      this.loading = true
      this.publishCaseAction(id).then(() => {
        this.loading = false
        this.$Message.success('案件发布成功')
      }).catch(e => {
        this.$Message.error(e.message())
      })
    },
    reject (id) {
      this.loading = true
      this.rejectCaseAction(id).then(() => {
        this.loading = false
        this.$Message.success('案件驳回成功')
      }).catch(e => {
        this.$Message.error(e.message())
      })
    }
  }
}
</script>

<style scoped>

</style>
