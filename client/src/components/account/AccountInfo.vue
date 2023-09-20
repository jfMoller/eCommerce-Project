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
import { defineComponent, ref, onMounted } from 'vue';

export default defineComponent({
    name: 'AccountInfo',
    setup() {

        const username = ref<string>('');
        const email = ref<string>('')

        onMounted(async () => {
            //username.value = await useUserStore.getUserName()
            //email.value = await useUserStore.getEmail();
        });

        return { username, email }
    },

});
</script>
  