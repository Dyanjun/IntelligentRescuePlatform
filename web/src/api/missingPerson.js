import axios from '@/libs/api.request'

export const getMissingPerson = (id) => {
  return axios.request({
    url: `missing_person/${id}`,
    method: 'get'
  })
}
