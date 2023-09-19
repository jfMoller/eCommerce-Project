<template>
    <div class="absolute pt-2">
        <div class="flex flex-col justify-center items-center flex-nowrap bg-white p-4 shadow-md rounded-md">
            <button
                class="w-full mb-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none transition duration-300">
                <StyledRouterLink text="My Page" path="/account" additional-class="text-md" />
            </button>
            <button @click="handleLogout"
                class="w-full bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none transition duration-300">
                <p class="text-md">Logout</p>
            </button>
        </div>
    </div>
</template>
  
<script lang="ts">
import { defineComponent } from 'vue';
import StyledRouterLink from './StyledRouterLink.vue';
import { useAccountAPI } from '@/stores/network/accountAPI';

export default defineComponent({
    name: "AccountOrLogoutPopup",
    setup() {

        function handleLogout() {
            useAccountAPI().submitLogout()
        }

        return { handleLogout }
    },

    components: { StyledRouterLink }
});



</script>