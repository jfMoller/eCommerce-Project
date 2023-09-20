<template>
  <div class="flex justify-center items-center bg-gray-100">
    <div class="bg-white p-8 rounded shadow-lg w-[25rem]">
      <h2 class="text-2xl mb-4">Sign Up</h2>
      <form @submit.prevent="handleSignup">
        <div class="mb-4">
          <label for="username" class="block text-gray-700 text-sm font-bold mb-2">Username</label>
          <input v-model="username" type="text" id="username"
            class="w-full px-3 py-2 border rounded border-gray-400 focus:outline-none" required />
        </div>
        <div class="mb-4">
          <label for="email" class="block text-gray-700 text-sm font-bold mb-2">Email</label>
          <input v-model="email" type="email" id="email"
            class="w-full px-3 py-2 border rounded border-gray-400 focus:outline-none" required />
        </div>
        <div class="mb-4">
          <label for="password" class="block text-gray-700 text-sm font-bold mb-2">Password</label>
          <input v-model="password" type="password" id="password"
            class="w-full px-3 py-2 border rounded border-gray-400 focus:outline-none" required />
        </div>
        <div class="mb-6">
          <label for="confirmPassword" class="block text-gray-700 text-sm font-bold mb-2">Confirm Password</label>
          <input v-model="confirmPassword" type="password" id="confirmPassword"
            class="w-full px-3 py-2 border rounded border-gray-400 focus:outline-none" required />
        </div>
        <button type="submit" :disabled="password === '' || password != confirmPassword"
          class="w-full bg-blue-500 hover:bg-blue-700 disabled:bg-gray-500 disabled:hover:bg-gray-700 disabled:cursor-not-allowed text-white font-bold py-2 px-4 rounded focus:outline-none">Sign
          Up</button>
      </form>
      <div v-if="signupResponse" :class="['flex', 'justify-center', 'font-semibold', 'mt-2', responseMessageColor]">
        <p>{{ signupResponse.message }}</p>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { useAccountAPI } from '@/stores/network/accountAPI';

export default defineComponent({
  setup() {
    const signupResponse = ref<any>(null)
    const responseMessageColor = ref('')

    const username = ref('');
    const email = ref('');
    const password = ref('');
    const confirmPassword = ref('');

    async function handleSignup() {
      await useAccountAPI().methods.submitSignup(username.value, email.value, password.value)
      handleResponseMessage()
    }

    function handleResponseMessage() {
      signupResponse.value = useAccountAPI().values.signupResponse

      if (signupResponse.value.error) {
        responseMessageColor.value = "text-red-700"
      }
      else {
        responseMessageColor.value = "text-green-700"
      }
    }

    return { username, email, password, confirmPassword, handleSignup, signupResponse, responseMessageColor };
  }
})
</script>@/stores/network/connectionAPI