<template>
    <nav :class="[props.additionalClass]">
        <AdminToolsItem v-if="isAuthenticated && hasAdminRole" />
        <LoginItem v-if="!isAuthenticated" />
        <AccountItem v-else />
        <StyledRouterLink text="SHOP" path="/shop" />
        <StyledRouterLink text="ABOUT" path="/about" />
        <StyledRouterLink text="CONTACT" path="/contact" />
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
import AdminToolsItem from '../admintools/AdminToolsItem.vue';

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
        const hasAdminRole = computed(() => useAuthenticationStore().states.isAdmin)

        return { isAuthenticated, hasAdminRole, props }
    },

    components: {
    LoginItem,
    StyledRouterLink,
    ShoppingCartItem,
    AccountItem,
    AdminToolsItem
},
})
</script>