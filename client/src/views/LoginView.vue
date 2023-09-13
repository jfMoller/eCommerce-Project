<template>
  <div class="flex justify-center items-center bg-gray-100">
    <div class="bg-white p-8 rounded shadow-lg">
      <h2 class="text-2xl mb-4">Login</h2>
      <form @submit.prevent="login">
        <div class="mb-4">
          <label for="email" class="block text-gray-700 text-sm font-bold mb-2">Email</label>
          <input v-model="email" type="email" id="email"
            class="w-full px-3 py-2 border rounded border-gray-400 focus:outline-none" required />
        </div>
        <div class="mb-6">
          <label for="password" class="block text-gray-700 text-sm font-bold mb-2">Password</label>
          <input v-model="password" type="password" id="password"
            class="w-full px-3 py-2 border rounded border-gray-400 focus:outline-none" required />
        </div>
        <button type="submit"
          class="w-full bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none">Login</button>
      </form>
      <div class="flex justify-center items-center mt-4">
        <h4 class="mr-1">New customer? </h4>
        <StyledRouterLink text="Start here" path="/signup"
        textClass="text-blue-700 hover:text-blue-600 font-semibold" />
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import StyledRouterLink from '@/components/StyledRouterLink.vue';

export default defineComponent({
  setup() {
    const email = ref('');
    const password = ref('');

    function login() {
    console.log("Email: " + email.value + ", Password: " + password.value)
    };

    return { email, password, login };
  },

  components: { StyledRouterLink }
})
</script>
