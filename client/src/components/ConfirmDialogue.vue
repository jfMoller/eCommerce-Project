<template>
  <div class="fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-50">
    <div class="bg-white rounded-md p-4 shadow-lg space-y-4">
      <div class="mb-4">
        <div class="flex items-center">
          <i class="fas fa-warning mr-2 text-red-600 text-xl" />
          <h2 class="text-xl font-semibold"> {{ header }}</h2>
        </div>
        <div class="border border-t-1 w-full mt-1"></div>
      </div>
      <p class="text-gray-700">{{ text }}</p>
      <div class="flex flex-col sm:flex-row justify-start space-y-2 sm:space-y-0 sm:space-x-2">
        <input v-if="requirePassword" v-model="password"
          class="border border-gray-300 rounded py-2 px-4 outline-none focus:border-blue-500 mb-2 sm:mb-0"
          placeholder="Enter your Password" type="password">
        <button class="bg-gray-400 hover:bg-gray-500 text-white py-2 px-4 rounded" @click="cancel">Cancel</button>
        <button :disabled="requirePassword && password.length <= 0"
          class="bg-blue-500 hover:bg-blue-600 disabled:bg-gray-500 diabled:hover:none disabled:cursor-not-allowed text-white py-2 px-4 rounded"
          @click="handleConfirm">Confirm</button>
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
      if (props.isPasswordRequired) {
        confirmWithPassword()
      } else {
        confirmWithoutPassword()
      }
      props.onCancel()
    };

    async function confirmWithPassword() {
      const confirmedCredentials = await accountStore.API.confirmCredentials(password.value)

      if (confirmedCredentials) {
        props.onConfirm(password.value);
      } else {
        handleErrorResponseMessage()
      }
    }

    function handleErrorResponseMessage() {
      isConfirmationErrorResponse.value = accountStore.states.isConfirmationErrorResponse
    }

    async function confirmWithoutPassword() {
      props.onConfirm(password.value);
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
