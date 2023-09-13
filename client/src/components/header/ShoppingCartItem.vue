<template>
    <IconRouterLink iconClass="fa-solid fa-cart-shopping" additionalClass="relative" path="/checkout" />
    <div v-if="itemsCount > 0" class="absolute top-3 right-10">
        <p class="p-1 h-4 bg-green-200 text-xs rounded-full text-center flex justify-center items-center">{{ itemsCount }}
        </p>
    </div>
</template>

<script lang="ts">
import { computed, defineComponent } from 'vue';
import IconRouterLink from '../IconRouterLink.vue';
import { useShoppingCart } from '@/stores/shoppingCart';

export default defineComponent({
    name: "ShoppingCartItem",

    setup() {
        const shoppingCart = useShoppingCart();

        const itemsCount = computed(() => shoppingCart.getTotalItemsCount());

        return { itemsCount }

    },

    components: { IconRouterLink }
});
</script>