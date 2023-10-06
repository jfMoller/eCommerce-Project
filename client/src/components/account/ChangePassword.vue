<template>
    <ConfirmDialogue :isPasswordRequired="true" header="Confirm password change"
        text="Are you sure you want to change your password?" v-if="isConfirmationVisible" :onConfirm="handleChangePassword"
        :onCancel="closeConfirmation" />

    <div class="p-4 bg-white rounded shadow">
        <h2 class="text-xl font-semibold mb-4">Change Password</h2>
        <form @submit.prevent="openConfirmation">
            <div class="mb-4">
                <label for="newPassword" class="block text-gray-700 font-bold mb-2">New password:</label>
                <input v-model="newPassword" type="password" class="border w-full p-2 rounded" />
            </div>
            <div class="mb-4">
                <label for="confirmNewPassword" class="block text-gray-700 font-bold mb-2">Confirm New password:</label>
                <input v-model="confirmNewPassword" type="password" class="border w-full p-2 rounded" />
            </div>
            <div v-if="changePasswordResponse"
                :class="['flex', 'justify-center', 'font-semibold', 'my-2', responseMessageColor]">
                <p>{{ changePasswordResponse.message }}</p>
            </div>
            <button type="submit" class="bg-blue-500 text-white py-2 px-4 rounded disabled:bg-gray-500 disabled:hover:bg-gray-700 disabled:cursor-not-allowed"
            :disabled="newPassword === '' || newPassword != confirmNewPassword">Change password</button>
        </form>
    </div>
</template>
  
<script lang="ts">
import { defineComponent, ref } from 'vue';
import { useAccountStore } from '@/stores/network/accountStore';
import { useConnectionStore } from '@/stores/network/connectionStore';
import ConfirmDialogue from '@/components/ConfirmDialogue.vue'

export default defineComponent({
    name: 'ChangePassword',
    setup() {
        const accountStore = useAccountStore();
        const connectionStore = useConnectionStore();

        const newPassword = ref('');
        const confirmNewPassword = ref('');

        const changePasswordResponse = ref<any | null>(null);
        const responseMessageColor = ref<string | null>(null);

        const isConfirmationVisible = ref<boolean>(false);

        function openConfirmation() {
            isConfirmationVisible.value = true;
        }

        function closeConfirmation() {
            isConfirmationVisible.value = false;
        }

        async function handleChangePassword(currentPassword: string) {
            const response = await accountStore.API.changePassword(currentPassword, newPassword.value);
            handleResponseMessage()

            if ('success' in response) {
                setTimeout(async () => {
                    await connectionStore.API.submitRelog('EditAccountView');
                }, 2000);
            }
            closeConfirmation();

            newPassword.value = '';
            confirmNewPassword.value = '';
        }

        function handleResponseMessage() {
            changePasswordResponse.value = accountStore.states.changePasswordResponse

            if (changePasswordResponse.value.error) {
                responseMessageColor.value = "text-red-700"
            }
            else {
                responseMessageColor.value = "text-green-700"
            }
        }

        return { newPassword, confirmNewPassword, openConfirmation, closeConfirmation, handleChangePassword, changePasswordResponse, responseMessageColor, isConfirmationVisible }
    },
    components: { ConfirmDialogue }
});
</script>
  