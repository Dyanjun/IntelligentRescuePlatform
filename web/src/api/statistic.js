import axios from '@/libs/api.request'

export const getLocation = () => {
  return axios.request({
    url: 'data/place',
    method: 'get'
  })
}

export const getLocationData = () => {
  return axios.request({
    url: 'data/place/data',
    method: 'get'
  })
}

export const getAge = () => {
  return axios.request({
    url: 'data/age',
    method: 'get'
  })
}
