<template>
    <nav :class="['text-xl', props.additionalClass]">
        <LoginItem v-if="!isAuthenticated" />
        <AccountItem v-else />
        <StyledRouterLink text="Shop" path="/shop" />
        <StyledRouterLink text="About" path="/about" />
        <StyledRouterLink text="Contact" path="/contact" />
        <ShoppingCartItem v-if="props.isShoppingCartIncluded" />
    </nav>
</template>
  
<script lang="ts">
import LoginItem from './LoginItem.vue';
import StyledRouterLink from '../StyledRouterLink.vue';
import ShoppingCartItem from './ShoppingCartItem.vue';
import { defineComponent, computed } from 'vue';
import { useAuthenticationStore } from '@/stores/authenticationStore';
import AccountItem from './AccountItem.vue';

export default defineComponent({
    props: {
        additionalClass: {
            Type: String,
            required: false
        },
        isShoppingCartIncluded: {
            Type: Boolean,
            required: false,
            default: true
        },
    },
    setup(props) {
        const isAuthenticated = computed(() => useAuthenticationStore().states.isAuthenticated)

        return { isAuthenticated, props }
    },

    components: {
        LoginItem,
        StyledRouterLink,
        ShoppingCartItem,
        AccountItem
    },
})
</script>