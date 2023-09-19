<template>
    <div class="cursor-pointer transition duration-300" @mouseover="showPopup" @mouseleave="hidePopup">
        <StyledRouterLink text="Login" path="/login" />
        <LoginOrSignupPopup v-if="isShowingPopup" />
    </div>
</template>
  
<script lang="ts">
import StyledRouterLink from '../StyledRouterLink.vue';
import LoginOrSignupPopup from '../LoginOrSignupPopup.vue';
import { defineComponent, ref } from 'vue';

export default defineComponent({
    name: "LoginItem",
    setup() {
        const isShowingPopup = ref(false);

        function showPopup() {
            isShowingPopup.value = true;
        }

        function hidePopup() {
            isShowingPopup.value = false;
        }

        return { isShowingPopup, showPopup, hidePopup }

    },
    components: {
        StyledRouterLink,
        LoginOrSignupPopup
    },
})
</script>