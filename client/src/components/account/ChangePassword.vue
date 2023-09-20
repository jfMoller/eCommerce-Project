<template>
    <div class="p-4 bg-white rounded shadow">
        <h2 class="text-xl font-semibold mb-4">Change Password</h2>
        <form @submit.prevent="submitChangePassword">
            <div class="mb-4">
                <label for="currentPassword" class="block text-gray-700 font-bold mb-2">Current Password:</label>
                <input v-model="currentPassword" type="password" id="currentPassword" class="border w-full p-2 rounded" />
            </div>
            <div class="mb-4">
                <label for="newPassword" class="block text-gray-700 font-bold mb-2">New Password:</label>
                <input v-model="newPassword" type="password" id="newPassword" class="border w-full p-2 rounded" />
            </div>
            <button type="submit" class="bg-blue-500 text-white py-2 px-4 rounded">Change Password</button>
        </form>
    </div>
</template>
  
<script lang="ts">
import { defineComponent, ref } from 'vue';
export default defineComponent({
    name: 'ChangePassword',

    setup() {
        const currentPassword = ref('');
        const newPassword = ref('');

        const submitChangePassword = () => {
            //useUserStore.changePassword({ currentPassword: currentPassword.value, newPassword: newPassword.value });
        }

        return { currentPassword, newPassword, submitChangePassword }
    }
});

</script>
  