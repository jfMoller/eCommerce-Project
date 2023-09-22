import { defineStore } from 'pinia'
import { callPost, callGet, callPut } from './requests'
import { ref, watch } from 'vue'
import { useAuthenticationStore } from '../authenticationStore'

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
    changeUsernameResponse: ref<ResponseSuccess | ResponseError | null>(null),

    username: ref<string | null>(null),
    email: ref<string | null>(null),
    orders: ref<any | null>(null)
  }

  
  watch(
    async () => useAuthenticationStore().states.isAuthenticated,
    async (newState) => {
      if (newState && await newState != false) {
        await API.getUserDetails().then((response) => {
          if (response.success) {
            states.username.value = response.username
            states.email.value = response.email
          }
        })
      }
    }
  )

  const API = {
    submitSignup: async (username: string, email: string, password: string): Promise<any> => {
      const response: ResponseSuccess | ResponseError = await callPost('/account/signup', {
        username: username,
        email: email,
        password: password
      })

      assignResponse(response, states.signupResponse)
      return response
    },

    getUserDetails: () => callGet('/account/details'),

    changeUsername: async (newUsername: string) => {
      const response: ResponseSuccess | ResponseError = await callPut('/account/username', {
        newUsername: newUsername
      })

      assignResponse(response, states.changeUsernameResponse)

      return response
    },

    changeEmail: (newEmail: string) => callPut('/account/email', { newUsername: newEmail }),

    changePassword: (currentPassword: string, newPassword: string) =>
      callPost('/account/change_password', {
        currentPassword: currentPassword,
        newPassword: newPassword
      }),

    getOrders: () => callGet('/account/orders/all'),

    deleteAccount: (currentPassword: string) =>
      callPost('/account/delete', { currentPassword: currentPassword })
  }

  function assignResponse(response: any, state: any) {
    if (response.success) {
      const { success, message } = response
      state.value = { success, message }
    }
    if (response.error) {
      const { error, message } = response
      state.value = { error, message }
    }
  }

  return {
    states,
    API
  }
})
