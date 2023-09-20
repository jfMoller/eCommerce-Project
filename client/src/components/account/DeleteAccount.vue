<template>
    <div class="p-4 bg-white rounded shadow">
      <h2 class="text-xl font-semibold mb-4">Delete Account</h2>
      <form @submit.prevent="submitDeleteAccount">
        <div class="mb-4">
          <label for="currentPassword" class="block text-gray-700 font-bold mb-2">Current Password:</label>
          <input v-model="currentPassword" type="password" id="currentPassword" class="border w-full p-2 rounded" />
        </div>
        <button type="submit" class="bg-red-500 text-white py-2 px-4 rounded">Delete Account</button>
      </form>
    </div>
  </template>
  
  <script lang="ts">
  import { defineComponent, ref } from 'vue';
  export default defineComponent({
    name: 'DeleteAccount',
  
    setup() {
      const currentPassword = ref('');
  
      function submitDeleteAccount(){
        // store
      }
  
      return { currentPassword, submitDeleteAccount }
    }
  });
  </script>
  