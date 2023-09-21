import { defineStore } from 'pinia'
import { callPost, callGet, callPut } from './requests'
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
    signupResponse: ref<ResponseSuccess | ResponseError | null>(null),
    username: ref<string | null>(null),
    email: ref<string | null>(null),
    orders: ref<any | null>(null)
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
    },

    getUsername: () => callGet('/account/get_username'),

    getEmail: () => callGet('/account/get_email'),

    changePassword: (currentPassword: string, newPassword: string) =>
      callPost('/account/change_password', {
        currentPassword: currentPassword,
        newPassword: newPassword
      }),

    editUsername: (newUsername: string) =>
      callPut('/account/edit_username', { newUsername: newUsername }),

    editEmail: (newEmail: string) => callPut('/account/edit_email', { newUsername: newEmail }),

    showOrders: () => callGet('/account/orders/get_all'),

    deleteAccount: (currentPassword: string) =>
      callPost('/account/delete_account', { currentPassword: currentPassword })
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
