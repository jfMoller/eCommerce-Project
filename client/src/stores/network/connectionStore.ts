import { defineStore } from 'pinia'
import { callPost } from './requests'
import { ref } from 'vue'
import { useAuthenticationStore } from '../authenticationStore'
import { useAccountStore } from './accountStore'
import navigationProvider from '@/router/navigationProvider'

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
    loginErrorResponse: ref<ResponseError | ResponseSuccess | null>(null),
    isConfirmationError: ref<boolean | null>(null)
  }

  const API = {
    submitLogin: async (email: string, password: string): Promise<any> => {
      const response: LoginResponseSuccess | ResponseError = await callPost('/account/login', {
        email: email,
        password: password
      })

      handleLoginErrorResponse(response as ResponseError)
      useAuthenticationStore().methods.handleAuthentication(response as LoginResponseSuccess)

      return response
    },

    submitLogout: async () => useAuthenticationStore().methods.handleRevokeAuthentication(),

    reconnect: async (password: string, origin: string): Promise<any> => {
      await API.submitLogout()
      await API.submitLogin(useAccountStore().states.email as string, password)
      await navigationProvider.navigate(origin)
    },

    isValidLoginCredentials: async (password: string): Promise<boolean> => {
      const isConfirmed: boolean = await callPost('/account/confirm', {
        email: useAccountStore().states.email,
        password: password
      })

      if (!isConfirmed) {
        states.isConfirmationError.value = true
      }

      return isConfirmed
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
