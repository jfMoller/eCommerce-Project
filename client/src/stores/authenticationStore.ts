import { ref } from 'vue'
import { defineStore } from 'pinia'
import type { LoginResponseSuccess } from './network/connectionStore'
import navigationProvider from '../router/navigationProvider'

export const useAuthenticationStore = defineStore('authenticationStore', () => {
  const states = {
    isAuthenticated: ref<boolean>(false),
    isAdmin: ref<boolean>(false)
  }

  const methods = {
    handleAuthentication: async (response: LoginResponseSuccess, routerOriginName?: string) => {
      if (response.success && response.token) {
        storeJwtToken(response.token)
        states.isAuthenticated.value = true

        if (response.userRole === 'ADMIN') {
          states.isAdmin.value = true
        }

        navigationProvider.navigateOnCondition(
          states.isAuthenticated.value,
          routerOriginName ? routerOriginName : 'home',
          'login'
        )
      }
    },

    handleRevokeAuthentication: () => {
      clearJwtToken()
      revokeAuthentication()
      navigationProvider.navigate('home')
    },

    getJwtToken: () => {
      return sessionStorage.getItem('jwtToken')
    }
  }

  function revokeAuthentication() {
    states.isAuthenticated.value = false
    states.isAdmin.value = false
  }

  function storeJwtToken(token: string) {
    sessionStorage.setItem('jwtToken', token)
  }

  function clearJwtToken() {
    sessionStorage.removeItem('jwtToken')
  }

  return {
    states,
    methods
  }
})
