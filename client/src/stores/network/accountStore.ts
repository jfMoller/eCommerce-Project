import { defineStore } from 'pinia'
import { callPost } from './calls'
import { ref } from 'vue'

export interface ResponseError {
  error: boolean
  message: string
}

export interface ResponseSuccess {
  success: boolean
  message: string
}

export const useAccountStore = defineStore('accountStore', () => {
  const states = {
    signupResponse: ref<ResponseSuccess | ResponseError | null>(null)
  }

  const API = {
    submitSignup: async (username: string, email: string, password: string): Promise<any> => {
      const response: ResponseSuccess | ResponseError = await callPost('/account/signup', {
        username: username,
        email: email,
        password: password
      })

      assignSignupResponse(response)
      return response
    }
  }

  function assignSignupResponse(response: any) {
    if (response.error) {
      states.signupResponse.value = {
        error: response.error,
        message: response.message
      }
    }
    if (response.success) {
      states.signupResponse.value = {
        success: response.error,
        message: response.message
      }
    }
  }

  return {
    states,
    API
  }
})
