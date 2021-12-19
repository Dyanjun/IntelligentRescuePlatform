<template>
  <div>
    <Card>
      <Row>
        <Tabs :animated="false" class="Tabs">
          <TabPane label="全部">
            <paged-table
              :loading="caseLoading"
              :columns="caseColumns"
              :data-source="caseList"
              :total="caseList.length"
              style="margin-bottom: 50px"
            />
          </TabPane>
          <TabPane label="待审核">
              <paged-table
                :loading="loading"
                :columns="columns"
                :data-source="auditCaseList"
                :total="auditCaseList.length"
                style="margin-bottom: 50px"
              />
          </TabPane>
        </Tabs>
      </Row>
    </Card>
    <Modal v-model="show">
      <p slot="header" class="end-task-modal"><Icon type="ios-information-circle"></Icon><span>失踪人员详情</span></p>
      <div style="text-align: left; font-size: large">
        <Row>姓名：{{missing.name || ''}}</Row>
        <Row>性别：{{missing.gender || ''}}</Row>
        <Row>年龄：{{missing.age || ''}}</Row>
        <Row>近期照片：</Row>
        <Row><img :src="url" style="width: 400px"></Row>
      </div>
      <div slot="footer"><Button type="primary" size="large" long @click="show=false">关闭</Button></div>
    </Modal>
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
import PagedTable from '_c/paged-table/paged-table.vue'
import { getMissingPerson } from '@/api/missingPerson'
import { caseStatusNames } from '@/constants/caseStatus'
export default {
  name: 'case-management',
  components: {
    PagedTable
  },
  data () {
    return {
      loading: true,
      caseLoading: true,
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
      ],
      caseColumns: [
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
          title: '状态',
          key: 'status',
          render: (h, { row }) => h('span', caseStatusNames[row.status])
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
            const buttons = [detailButton]
            return h('div', buttons)
          }
        }
      ],
      show: false,
      missing: {
        name: '',
        gender: '',
        age: ''
      },
      url: ''
    }
  },
  mounted () {
    this.refresh()
  },
  computed: mapState({
    auditCaseList: (state) => state.lostCase.auditCaseList,
    caseList: (state) => state.lostCase.caseList
  }),
  methods: {
    ...mapActions(['getCaseAction', 'getAuditCaseAction', 'publishCaseAction', 'rejectCaseAction']),
    getMissingPerson,
    refresh () {
      this.loading = true
      this.getAuditCaseAction().then(() => {
        this.loading = false
      })
      this.caseLoading = true
      this.getCaseAction().then(() => {
        this.caseLoading = false
      })
    },
    showDetail (item) {
      this.show = false
      this.getMissingPerson(item.missingPerson.id).then((res) => {
        this.missing = res
        this.url = res.photo == null ? '' : res.photo.url
        this.show = true
      }).catch(e => {
        this.$Message.error(e.message)
      })
    },
    publish (id) {
      this.loading = true
      this.publishCaseAction(id).then(() => {
        this.loading = false
        this.$Message.success('案件发布成功')
      }).catch(e => {
        this.$Message.error(e.message)
      })
    },
    reject (id) {
      this.loading = true
      this.rejectCaseAction(id).then(() => {
        this.loading = false
        this.$Message.success('案件驳回成功')
      }).catch(e => {
        this.$Message.error(e.message)
      })
    }
  }
}
</script>

<style scoped>

</style>
