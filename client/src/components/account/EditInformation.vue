<template>
    <div class="p-4 bg-white rounded shadow">
        <h2 class="text-xl font-semibold mb-4">Edit Information</h2>
        <form @submit.prevent="submitForm">
            <div class="mb-4">
                <label for="newUsername" class="block text-gray-700 font-bold mb-2">New Username:</label>
                <input v-model="newUsername" type="text" id="newUsername" class="border w-full p-2 rounded" />
            </div>
            <div class="mb-4">
                <label for="newEmail" class="block text-gray-700 font-bold mb-2">New Email:</label>
                <input v-model="newEmail" type="email" id="newEmail" class="border w-full p-2 rounded" />
            </div>
            <button type="submit" class="bg-blue-500 text-white py-2 px-4 rounded">Save Changes</button>
        </form>
    </div>
</template>
  
<script lang="ts">
import { defineComponent, ref } from 'vue';
export default defineComponent({
    name: 'EditInformation',

    setup() {

        const newUsername = ref('');
        const newEmail = ref('');

        const submitForm = () => {
            //useUserStore().updateUserAccount({ newUsername: newUsername.value, newEmail: newEmail.value });
        }

        return { newUsername, newEmail, submitForm }
    }
});
</script>
  