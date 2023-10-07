import { defineStore } from 'pinia'
import { callGet, callPost } from './requests'
import { ref } from 'vue'
import { useAuthenticationStore } from '../authenticationStore'
import { useAccountStore } from './accountStore'

export interface LoginResponseSuccess {
  success: boolean
  userRole: string
  token: string
}

export interface ResponseError {
  error: boolean
  message: string
}

export interface ResponseSuccess {
  success: boolean
  message: string
}

export const useConnectionStore = defineStore('connectionStore', () => {
  const states = {
    loginErrorResponse: ref<ResponseError | ResponseSuccess | null>(null)
  }

  const API = {
    submitLogin: async (email: string, password: string): Promise<any> => {
      const response: LoginResponseSuccess | ResponseError = await callPost('/account/login', {
        email: email,
        password: password
      })

      handleLoginErrorResponse(response as ResponseError)
      useAuthenticationStore().methods.handleAuthentication(response as LoginResponseSuccess)

      if (!useAccountStore().states.username || !useAccountStore().states.email) {
        useAccountStore().API.getUserDetails()
      }

      return response
    },

    submitLogout: async () => useAuthenticationStore().methods.handleRevokeAuthentication(),

    submitRelog: async (routerOriginName: string) => {
      const response: LoginResponseSuccess | ResponseError = await callGet(
        '/account/re-authenticate'
      )

      if ('success' in response) {
        useAuthenticationStore().methods.handleRevokeAuthentication()
        useAuthenticationStore().methods.handleAuthentication(
          response as LoginResponseSuccess,
          routerOriginName
        )
      }
      return response as ResponseError
    }
  }

  function handleLoginErrorResponse(response: any) {
    if (response.error) {
      states.loginErrorResponse.value = {
        error: response.error,
        message: response.message
      }
    } else states.loginErrorResponse.value = null
  }

  return {
    states,
    API
  }
})
