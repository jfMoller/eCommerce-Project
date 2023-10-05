<template>
    <ConfirmDialogue :isPasswordRequired="true" header="Confirm Email change"
        text="Are you sure you want to change your Email?" v-if="isConfirmationVisible" :onConfirm="handleChangeEmail"
        :onCancel="closeConfirmation" />

    <div class="p-4 bg-white rounded shadow">
        <h2 class="text-xl font-semibold mb-4">Change Email</h2>
        <form @submit.prevent="openConfirmation">
            <div class="mb-4">
                <label for="newEmail" class="block text-gray-700 font-bold mb-2">New Email:</label>
                <input v-model="newEmail" type="text" class="border w-full p-2 rounded" />
            </div>
            <div v-if="changeEmailResponse"
                :class="['flex', 'justify-center', 'font-semibold', 'my-2', responseMessageColor]">
                <p>{{ changeEmailResponse.message }}</p>
            </div>
            <button type="submit" class="bg-blue-500 text-white py-2 px-4 rounded">Change Email</button>
        </form>
    </div>
</template>
  
<script lang="ts">
import { defineComponent, ref } from 'vue';
import { useAccountStore } from '@/stores/network/accountStore';
import { useConnectionStore } from '@/stores/network/connectionStore';
import ConfirmDialogue from '@/components/ConfirmDialogue.vue'

export default defineComponent({
    name: 'ChangeEmail',
    setup() {
        const accountStore = useAccountStore();
        const connectionStore = useConnectionStore();

        const newEmail = ref('');
        const changeEmailResponse = ref<any | null>(null);
        const responseMessageColor = ref<string | null>(null);

        const isConfirmationVisible = ref<boolean>(false);

        function openConfirmation() {
            isConfirmationVisible.value = true;
        }

        function closeConfirmation() {
            isConfirmationVisible.value = false;
        }

        async function handleChangeEmail() {
            const response = await accountStore.API.changeEmail(newEmail.value);
            handleResponseMessage()

            if ('success' in response) {
                setTimeout(async () => {
                    await connectionStore.API.submitLogout();;
                }, 2000);
            }
            closeConfirmation();
        }

        function handleResponseMessage() {
            changeEmailResponse.value = accountStore.states.changeEmailResponse

            if (changeEmailResponse.value.error) {
                responseMessageColor.value = "text-red-700"
            }
            else {
                responseMessageColor.value = "text-green-700"
            }
        }

        return { newEmail, openConfirmation, closeConfirmation, handleChangeEmail, changeEmailResponse, responseMessageColor, isConfirmationVisible }
    },
    components: { ConfirmDialogue }
});
</script>
  