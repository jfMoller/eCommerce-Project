import { ref } from 'vue'
import { defineStore } from 'pinia'
import type { LoginResponseSuccess } from './network/accountAPI'
import { useNavigationProvider } from './navigationProvider'

export const useAuthenticationProvider = defineStore('authenticationProvider', () => {
  const isAuthenticated = ref<boolean>(false)
  const isAdmin = ref<boolean>(false)

  function handleAuthentication(response: LoginResponseSuccess) {
    if (response.success && response.token) {
      storeJwtToken(response.token)
      isAuthenticated.value = true

      if (response.userRole === 'ADMIN') {
        isAdmin.value = true
      }

      useNavigationProvider().conditionalNavigate(isAuthenticated.value, 'home', 'login')
    }
  }

  function handleRevokeAuthentication() {
    clearJwtToken()
    revokeAuthentication()
    useNavigationProvider().navigate('home')
  }

  function revokeAuthentication() {
    isAuthenticated.value = false
  }

  function storeJwtToken(token: string) {
    sessionStorage.setItem('jwtToken', token)
  }

  function getJwtToken() {
    return sessionStorage.getItem('jwtToken')
  }

  function clearJwtToken() {
    sessionStorage.removeItem('jwtToken')
  }

  return {
    isAuthenticated,
    isAdmin,
    handleAuthentication,
    handleRevokeAuthentication,
    getJwtToken
  }
})
