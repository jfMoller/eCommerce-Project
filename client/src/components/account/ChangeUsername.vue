<template>
    <ConfirmDialogue :isPasswordRequired="true" header="Confirm username change"
        text="Are you sure you want to change your username?" v-if="isConfirmationVisible" :onConfirm="handleChangeUsername"
        :onCancel="closeConfirmation" />

    <div class="p-4 bg-white rounded shadow">
        <h2 class="text-xl font-semibold mb-4">Change Username</h2>
        <form @submit.prevent="openConfirmation">
            <div class="mb-4">
                <label for="newUsername" class="block text-gray-700 font-bold mb-2">New Username:</label>
                <input v-model="newUsername" type="text" id="newUsername" class="border w-full p-2 rounded" />
            </div>
            <div v-if="changeUsernameResponse"
                :class="['flex', 'justify-center', 'font-semibold', 'my-2', responseMessageColor]">
                <p>{{ changeUsernameResponse.message }}</p>
            </div>
            <button type="submit" class="bg-blue-500 text-white py-2 px-4 rounded">Change username</button>
        </form>
    </div>
</template>
  
<script lang="ts">
import { defineComponent, ref } from 'vue';
import { useAccountStore } from '@/stores/network/accountStore';
import { useConnectionStore } from '@/stores/network/connectionStore';
import ConfirmDialogue from '@/components/ConfirmDialogue.vue'

export default defineComponent({
    name: 'EditUsername',
    setup() {
        const accountStore = useAccountStore();
        const connectionStore = useConnectionStore();

        const newUsername = ref('');
        const changeUsernameResponse = ref<any | null>(null);
        const responseMessageColor = ref<string | null>(null);

        const isConfirmationVisible = ref<boolean>(false);

        function openConfirmation() {
            isConfirmationVisible.value = true;
        }

        function closeConfirmation() {
            isConfirmationVisible.value = false;
        }

        async function handleChangeUsername() {
            const response = await accountStore.API.changeUsername(newUsername.value);
            handleResponseMessage()

            if ('success' in response) {
                setTimeout(async () => {
                    await connectionStore.API.submitRelog('EditAccountView');
                }, 2000);
            }
            closeConfirmation();
            newUsername.value = '';
        }

        function handleResponseMessage() {
            changeUsernameResponse.value = accountStore.states.changeUsernameResponse

            if (changeUsernameResponse.value.error) {
                responseMessageColor.value = "text-red-700"
            }
            else {
                responseMessageColor.value = "text-green-700"
            }
        }

        return { newUsername, openConfirmation, closeConfirmation, handleChangeUsername, changeUsernameResponse, responseMessageColor, isConfirmationVisible }
    },
    components: { ConfirmDialogue }
});
</script>
  