<template>
  <div class="fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-50">
    <div class="bg-white rounded-md p-4 shadow-lg space-y-4">
      <span class="close absolute top-2 right-2 cursor-pointer text-gray-600">&times;</span>
      <h3 class="font-semibold text-2xl">{{ header }}</h3>
      <p class="text-gray-700">{{ text }}</p>
      <p v-if="requirePassword" class="text-gray-700">You will be logged out for the changes to take effect.</p>
      <div class="flex justify-start space-x-2">
        <input v-if="requirePassword" v-model="password"
          class="border border-gray-300 rounded py-2 px-4 outline-none focus:border-blue-500"
          placeholder="Enter your Password" type="password">
        <button :disabled="requirePassword && password.length <= 0"
          class="bg-blue-500 hover:bg-blue-600 disabled:bg-gray-500 diabled:hover:none disabled:cursor-not-allowed text-white py-2 px-4 rounded"
          @click="handleConfirm">Confirm</button>
        <button class="bg-gray-400 hover:bg-gray-500 text-white py-2 px-4 rounded" @click="cancel">Cancel</button>
      </div>
      <div v-if="isConfirmationErrorResponse" class="flex justify-center font-semibold text-red-700 mt-2">
        <p>Invalid password.</p>
      </div>
  
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { useAccountStore } from '@/stores/network/accountStore';

export default defineComponent({
  props: {
    isPasswordRequired: {
      type: Boolean,
      required: false
    },
    header: {
      type: String,
      required: true
    },
    text: {
      type: String,
      required: true
    },
    onConfirm: {
      type: Function,
      required: true
    },
    onCancel: {
      type: Function,
      required: true
    },

  },
  setup(props) {
    const accountStore = useAccountStore()
    const isConfirmationErrorResponse = ref<any | null>(null);

    const requirePassword = props.isPasswordRequired;
    const password = ref('')


    async function handleConfirm() {
      const confirmedCredentials = await accountStore.API.confirmCredentials(password.value)

      if (confirmedCredentials) {
        props.onConfirm(password.value);
      } else {
        handleErrorResponseMessage()
      }

    };

    function handleErrorResponseMessage() {
      isConfirmationErrorResponse.value = accountStore.states.isConfirmationErrorResponse
    }

    function cancel() {
      props.onCancel();
    };

    return {
      requirePassword,
      password,
      handleConfirm,
      cancel,
      isConfirmationErrorResponse,
    };
  }
});
</script>
