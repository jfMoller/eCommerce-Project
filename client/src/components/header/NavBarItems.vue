<template>
    <nav class="space-x-6 px-4 flex items-center text-xl">
        <div class="cursor-pointer transition duration-300" @mouseover="showPopup" @mouseleave="hidePopup">
            <StyledRouterLink text="Login" path="/login" />
            <LoginOrSignupPopup v-if="isShowingPopup" />
        </div>
        <StyledRouterLink text="Shop" path="/shop" />
        <StyledRouterLink text="About" path="/about" />
        <StyledRouterLink text="Contact" path="/contact" />
        <ShoppingCartItem />
    </nav>
</template>
  
<script lang="ts">
import StyledRouterLink from '../StyledRouterLink.vue';
import ShoppingCartItem from './ShoppingCartItem.vue';
import LoginOrSignupPopup from '../LoginOrSignupPopup.vue';
import { defineComponent, ref } from 'vue';

export default defineComponent({
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
        ShoppingCartItem,
        LoginOrSignupPopup
    },
})
</script>