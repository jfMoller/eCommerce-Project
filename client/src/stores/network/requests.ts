import axios from 'axios'
import type { Method } from 'axios'
import { useAuthenticationStore } from '../authenticationStore'
import { useLoadingStore } from './loadingStore'

const baseUrl = 'http://localhost:8080/api'

export async function callGet(endpoint: string) {
  return await makeRequest('GET', endpoint)
}

export async function callPost(endpoint: string, data: any) {
  return await makeRequest('POST', endpoint, data)
}

export async function callPut(endpoint: string, data: any) {
  return await makeRequest('PUT', endpoint, data)
}

export async function callPatch(endpoint: string, data: any) {
  return await makeRequest('PATCH', endpoint, data)
}

export async function callDelete(endpoint: string) {
  return await makeRequest('DELETE', endpoint, undefined)
}

async function makeRequest(method: Method, endpoint: string, data?: any) {
  setIsLoading(true)

  try {
    const url = `${baseUrl}${endpoint}`
    const jwtToken = useAuthenticationStore().methods.getJwtToken()

    const result = await axios.request({
      method,
      url,
      data,
      headers: { Authorization: jwtToken ? jwtToken : null }
    })

    setIsLoading(false)
    return result.data
  } catch (error: any) {
    setIsLoading(false)
    return error.response.data
  }
}

function setIsLoading(state: boolean) {
  useLoadingStore().methods.setIsLoading(state)
}
