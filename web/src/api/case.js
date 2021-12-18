import axios from '@/libs/api.request'

export const getCase = () => {
  return axios.request({
    url: 'case',
    method: 'get'
  })
}

export const getAuditCase = () => {
  return axios.request({
    url: 'case/audit',
    method: 'get'
  })
}

export const publishCase = (id) => {
  return axios.request({
    url: `case/${id}/publish`,
    method: 'post'
  })
}

export const rejectCase = (id) => {
  return axios.request({
    url: `case/${id}/reject`,
    method: 'post'
  })
}
