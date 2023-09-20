<template>
    <div class="absolute pt-2">
        <div class="flex flex-col justify-center items-center bg-white p-4 shadow-md rounded-md">
            <div class="text-left space-y-2 flex flex-col">
                <router-link :to="{ name: 'EditAccountView' }" class="text-blue-700 hover:text-blue-500">
                    Edit my information
                </router-link>
                <router-link :to="{ name: 'ShowAccountOrdersView' }" class="text-blue-700 hover:text-blue-500">
                    Show my orders
                </router-link>
                <button @click="handleLogout"
                    class="w-full bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none transition duration-300">
                    <p class="text-md">Logout</p>
                </button>
            </div>


        </div>
    </div>
</template>
    
<script lang="ts">
import { defineComponent } from 'vue';
import { useConnectionAPI } from '@/stores/network/connectionAPI';

export default defineComponent({
    name: "AccountOrLogoutPopup",
    setup() {
        function handleLogout() {
            useConnectionAPI().methods.submitLogout()
        }

        return { handleLogout }
    },

    components: {}
});
</script>
  @/stores/network/connectionAPI