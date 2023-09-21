import axios from 'axios'

const baseUrl = 'http://localhost:8080/api'

export async function callGet(endpoint: string) {
  try {
    const result = await axios.request({
      method: 'GET',
      url: baseUrl + endpoint
    })
    return result.data
  } catch (error: any) {
    return error.response.data
  }
}

export async function callPost(endpoint: string, data: any) {
  try {
    const result = await axios.request({
      method: 'POST',
      url: baseUrl + endpoint,
      data: data
    })
    return result.data
  } catch (error: any) {
    return error.response.data
  }
}

export async function callPut(endpoint: string, data: any) {
  try {
    const result = await axios.request({
      method: 'PUT',
      url: baseUrl + endpoint,
      data: data
    })
    return result.data
  } catch (error: any) {
    return error.response.data
  }
}

export async function callDelete(endpoint: string, _id: string) {
  try {
    await axios.request({
      method: 'DELETE',
      url: baseUrl + endpoint + '/' + _id
    })
  } catch (error: any) {
    return error.response.data
  }
}
