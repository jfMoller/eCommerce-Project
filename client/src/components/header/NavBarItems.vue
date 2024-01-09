<template>
    <nav :class="[props.additionalClass]">
        <AdminToolsItem v-if="isAuthenticated && hasAdminRole" />
        <LoginItem v-if="!isAuthenticated" />
        <AccountItem v-else />
        <StyledRouterLink text="SHOP" path="/shop" />
        <StyledRouterLink text="ABOUT" path="/about" />
        <StyledRouterLink text="CONTACT" path="/contact" />
        <ShoppingCartItem :additionalClass="hasProductsInCart ? ' ' : 'pointer-events-none'" />
    </nav>
</template>
  
<script lang="ts">
import LoginItem from './LoginItem.vue';
import StyledRouterLink from '../StyledRouterLink.vue';
import ShoppingCartItem from './ShoppingCartItem.vue';
import { defineComponent, computed } from 'vue';
import { useAuthenticationStore } from '@/stores/authenticationStore';
import { useShoppingCartStore } from '@/stores/shoppingCartStore'
import AccountItem from './AccountItem.vue';
import AdminToolsItem from '../admintools/AdminToolsItem.vue';

export default defineComponent({
    props: {
        additionalClass: {
            Type: String,
            required: false
        },
    },
    setup(props) {
        const isAuthenticated = computed(() => useAuthenticationStore().states.isAuthenticated)
        const hasAdminRole = computed(() => useAuthenticationStore().states.isAdmin)
        const hasProductsInCart = computed(() => useShoppingCartStore().states.productAmount > 0);

        return { isAuthenticated, hasAdminRole, hasProductsInCart, props }
    },

    components: {
    LoginItem,
    StyledRouterLink,
    ShoppingCartItem,
    AccountItem,
    AdminToolsItem,
},
})
</script>