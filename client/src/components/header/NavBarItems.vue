<template>
    <nav class="space-x-6 px-4 flex items-center text-xl">
        <LoginItem v-if="!isAuthenticated" />
        <AccountItem v-else />
        <StyledRouterLink text="Shop" path="/shop" />
        <StyledRouterLink text="About" path="/about" />
        <StyledRouterLink text="Contact" path="/contact" />
        <ShoppingCartItem />
    </nav>
</template>
  
<script lang="ts">
import LoginItem from './LoginItem.vue';
import StyledRouterLink from '../StyledRouterLink.vue';
import ShoppingCartItem from './ShoppingCartItem.vue';
import { defineComponent, computed } from 'vue';
import { useAuthenticationProvider } from '@/stores/authenticationProvider';
import AccountItem from './AccountItem.vue';

export default defineComponent({
    setup() {
        const isAuthenticated = computed(() => useAuthenticationProvider().isAuthenticated)

        return { isAuthenticated }
    },

    components: {
        LoginItem,
        StyledRouterLink,
        ShoppingCartItem,
        AccountItem
    },
})
</script>