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
console.log(response)
      if (response.success && response.token) {
        console.log("1")
        storeJwtToken(response.token)
        states.isAuthenticated.value = true
console.log("2")
        if (response.userRole === 'ADMIN') {
          states.isAdmin.value = true
        }
console.log("3")
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
