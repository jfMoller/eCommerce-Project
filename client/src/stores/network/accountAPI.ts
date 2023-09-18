import { defineStore } from 'pinia'
import { callPost } from './calls'
import { ref } from 'vue'
import { useAuthenticationProvider } from '../authenticationProvider'

const authenticationProvider = useAuthenticationProvider()

export interface LoginResponseSuccess {
  success: boolean
  userRole: string
  token: string
}

export interface ResponseError {
  error: boolean
  message: string
}

export interface ResponseSuccess {
  success: boolean
  message: string
}

const loginErrorResponse = ref<ResponseError | ResponseSuccess | null>(null)

export const useAccountAPI = defineStore('accountAPI', () => {
  async function submitLogin(email: string, password: string): Promise<any> {
    const response: LoginResponseSuccess | ResponseError = await callPost('/account/login', {
      email: email,
      password: password
    })

    handleLoginErrorResponse(response as ResponseError)

    authenticationProvider.handleAuthentication(response as LoginResponseSuccess)

    return response
  }

  function handleLoginErrorResponse(response: any) {
    if (response.error) {
      loginErrorResponse.value = {
        error: response.error,
        message: response.message
      }
    } else loginErrorResponse.value = null
  }

  const signupResponse = ref<ResponseError | ResponseSuccess | null>(null)

  async function submitSignup(username: string, email: string, password: string): Promise<any> {
    const response: LoginResponseSuccess | ResponseError = await callPost('/account/signup', {
      username: username,
      email: email,
      password: password
    })

    assignSignupResponse(response)

    return response
  }

  function assignSignupResponse(response: any) {
    if (response.error) {
      signupResponse.value = {
        error: response.error,
        message: response.message
      }
    }
    if (response.success) {
      signupResponse.value = {
        success: response.error,
        message: response.message
      }
    }
  }

  return {
    submitLogin,
    loginErrorResponse,
    submitSignup,
    signupResponse
  }
})
