import { ref } from 'vue'
import { defineStore } from 'pinia'
import type { LoginResponseSuccess } from './network/connectionAPI'
import navigationProvider from '../router/navigationProvider'

export const useAuthenticationProvider = defineStore('authenticationProvider', () => {
  const values = {
    isAuthenticated: ref<boolean>(false),
    isAdmin: ref<boolean>(false)
  }

  const methods = {
    handleAuthentication: (response: LoginResponseSuccess) => {
      if (response.success && response.token) {
        storeJwtToken(response.token)
        values.isAuthenticated.value = true

        if (response.userRole === 'ADMIN') {
          values.isAdmin.value = true
        }

        navigationProvider.navigateOnCondition(values.isAuthenticated.value, 'home', 'login')
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
    values.isAuthenticated.value = false
  }

  function storeJwtToken(token: string) {
    sessionStorage.setItem('jwtToken', token)
  }

  function clearJwtToken() {
    sessionStorage.removeItem('jwtToken')
  }

  return {
    values,
    methods
  }
})
