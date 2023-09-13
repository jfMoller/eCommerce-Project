<template>
    <div class="absolute pt-2">
        <div class="flex flex-col flex-nowrap bg-white p-4 shadow-md rounded-md">
            <button class="w-full bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none transition duration-300">
                <StyledRouterLink text="Login" path="/login" additional-class="text-md" />
            </button>
            <div class="flex justify-center items-center mt-4">
                <p class="mr-1 text-sm">New customer? </p>
                <StyledRouterLink text="Start here" path="/signup" textClass="text-blue-700 hover:text-blue-600"
                    additional-class="text-blue-700 hover:text-blue-400 text-sm" />
            </div>
        </div>
    </div>
</template>
  
<script lang="ts">
import { defineComponent } from 'vue';
import StyledRouterLink from './StyledRouterLink.vue';

export default defineComponent({
    name: "loginOrSignupPopup",
    components: { StyledRouterLink }
});
</script>