import { ref } from 'vue'
import { defineStore } from 'pinia'
import type { LoginResponseSuccess } from './network/accountAPI'

export const useAuthenticationProvider = defineStore('authenticationProvider', () => {
  const isAuthenticated = ref<boolean>(false)
  const isAdmin = ref<boolean>(false)

  function handleAuthentication(response: LoginResponseSuccess) {
    console.log(response)
    if (response.success) {
      storeJwtToken(response.token)
      if (response.userRole === 'ADMIN') isAdmin.value = true
      isAuthenticated.value = true

      console.log(isAuthenticated.value)
      console.log(getJwtToken())
    }
  }

  function storeJwtToken(token: string) {
    sessionStorage.setItem('jwtToken', token)
  }

  function getJwtToken() {
    return sessionStorage.getItem("jwtToken")
  }

  return {
    isAuthenticated,
    isAdmin,
    handleAuthentication,
    getJwtToken
  }
})
