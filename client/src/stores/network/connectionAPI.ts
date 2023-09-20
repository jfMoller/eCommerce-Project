import { defineStore } from 'pinia'
import { callPost } from './calls'
import { ref } from 'vue'
import { useAuthenticationProvider } from '../authenticationProvider'

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

export const useConnectionAPI = defineStore('connectionAPI', () => {
  const values = {
    loginErrorResponse: ref<ResponseError | ResponseSuccess | null>(null)
  }

  const methods = {
    submitLogin: async (email: string, password: string): Promise<any> => {
      const response: LoginResponseSuccess | ResponseError = await callPost('/account/login', {
        email: email,
        password: password
      })

      handleLoginErrorResponse(response as ResponseError)
      useAuthenticationProvider().methods.handleAuthentication(response as LoginResponseSuccess)

      return response
    },

    submitLogout: () => useAuthenticationProvider().methods.handleRevokeAuthentication()
  }

  function handleLoginErrorResponse(response: any) {
    if (response.error) {
      values.loginErrorResponse.value = {
        error: response.error,
        message: response.message
      }
    } else values.loginErrorResponse.value = null
  }

  return {
    methods,
    values
  }
})
