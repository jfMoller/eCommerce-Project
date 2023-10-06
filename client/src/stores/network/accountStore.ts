import { defineStore } from 'pinia'
import { callPost, callGet, callPut, callDelete } from './requests'
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

    changeEmailResponse: ref<ResponseSuccess | ResponseError | null>(null),

    changePasswordResponse: ref<ResponseSuccess | ResponseError | null>(null),

    deleteAccountResponse: ref<ResponseSuccess | ResponseError | null>(null),

    isConfirmationErrorResponse: ref<boolean | null>(null),

    username: ref<string | null>(null),
    email: ref<string | null>(null),
    orders: ref<any | null>(null)
  }

  watch(
    async () => useAuthenticationStore().states.isAuthenticated,
    async (newState, previousState) => {
      if (newState && newState !== previousState) {
        await API.getUserDetails()
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

    confirmCredentials: async (password: string): Promise<boolean> => {
      const isConfirmed: boolean = await callPost('/account/confirm', {
        email: useAccountStore().states.email,
        password: password
      })

      if (!isConfirmed) {
        states.isConfirmationErrorResponse.value = true
      }

      return isConfirmed
    },

    getUserDetails: async () => {
      const response = await callGet('/account/details')
      if (response.success) {
        states.username.value = response.username
        states.email.value = response.email
      }
    },

    changeUsername: async (newUsername: string) => {
      const response: ResponseSuccess | ResponseError = await callPut('/account/username', {
        newUsername: newUsername
      })

      assignResponse(response, states.changeUsernameResponse)

      return response
    },

    changeEmail: async (newEmail: string) => {
      const response: ResponseSuccess | ResponseError = await callPut('/account/email', {
        newEmail: newEmail
      })

      assignResponse(response, states.changeEmailResponse)

      return response
    },

    changePassword: async (currentPassword: string, newPassword: string) => {
      const response: ResponseSuccess | ResponseError = await callPut('/account/password', {
        currentPassword: currentPassword,
        newPassword: newPassword
      })

      assignResponse(response, states.changePasswordResponse)

      return response
    },

    getOrders: () => callGet('/account/orders/all'),

    deleteAccount: async () => {
      const response: ResponseSuccess | ResponseError = await callDelete('/account/delete')

      assignResponse(response, states.deleteAccountResponse)

      return response
    }
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
