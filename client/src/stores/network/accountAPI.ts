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

export const useAccountAPI = defineStore('accountAPI', () => {
  const values = {
    signupResponse: ref<ResponseSuccess | ResponseError | null>(null)
  }

  const methods = {
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
      values.signupResponse.value = {
        error: response.error,
        message: response.message
      }
    }
    if (response.success) {
      values.signupResponse.value = {
        success: response.error,
        message: response.message
      }
    }
  }

  return {
    values,
    methods
  }
})
