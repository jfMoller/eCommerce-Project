<template>
    <div class="p-4 bg-white rounded shadow">
        <h2 class="text-xl font-semibold mb-4">Account Information</h2>
        <div>
            <p><strong>Username:</strong> {{ username }}</p>
            <p><strong>Email:</strong> {{ email }}</p>
        </div>
    </div>
</template>
  
<script lang="ts">
import { useAccountStore } from '@/stores/network/accountStore';
import { defineComponent, ref, onMounted, watch } from 'vue';

export default defineComponent({
    name: 'AccountInfo',
    setup() {
        const accountStore = useAccountStore();

        const username = ref<string | null>(null);
        const email = ref<string | null>(null)

        onMounted(async () => {
            username.value = accountStore.states.username
            email.value = accountStore.states.email
        });

        watch(
            () => accountStore.states.username,
            (newUsernameState: any) => {
                if (newUsernameState) {
                    username.value = accountStore.states.username;
                }
            }
        );

        watch(
            () => accountStore.states.email,
            (newEmailState: any) => {
                if (newEmailState) {
                    email.value = accountStore.states.email;
                }
            }
        );

        return { username, email }
    },

});
</script>
  