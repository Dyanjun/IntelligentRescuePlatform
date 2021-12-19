import { getCase, getAuditCase, publishCase, rejectCase } from '@/api/case'

export default {
  state: {
    caseList: [],
    auditCaseList: []
  },
  mutations: {

  },
  getters: {

  },
  actions: {
    getCaseAction ({ state, commit }) {
      return new Promise((resolve, reject) => {
        getCase().then(res => {
          state.caseList = res
          resolve()
        }).catch(err => {
          reject(err)
        })
      })
    },
    getAuditCaseAction ({ state, commit }) {
      return new Promise((resolve, reject) => {
        getAuditCase().then((res) => {
          state.auditCaseList = res
          resolve()
        }).catch(err => {
          reject(err)
        })
      })
    },
    publishCaseAction ({ state, commit }, id) {
      return new Promise((resolve, reject) => {
        publishCase(id).then((res) => {
          state.auditCaseList = state.auditCaseList.filter(c => c.id !== id)
          state.caseList = state.caseList.map(c => {
            if (c.id === id) {
              c = res
            }
            return c
          })
          resolve()
        }).catch(err => {
          reject(err)
        })
      })
    },
    rejectCaseAction ({ state, commit }, id) {
      return new Promise((resolve, reject) => {
        rejectCase(id).then((res) => {
          state.auditCaseList = state.auditCaseList.filter(c => c.id !== id)
          state.caseList = state.caseList.map(c => {
            if (c.id === id) {
              c = res
            }
            return c
          })
          resolve()
        }).catch(err => {
          reject(err)
        })
      })
    }
  }
}
