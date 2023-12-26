<template>
    <div :class="[additionalClass]">
        <IconRouterLink iconClass="fa-solid fa-cart-shopping" additionalClass="relative" path="/checkout" />
        <div v-if="itemsCount > 0" class="absolute top-5 right-9 sm:top-5 sm:right-5"
            >
            <p class="p-3 w-3 h-3 bg-green-500 text-xs rounded-full text-white font-semibold flex justify-center items-center">
                {{ itemsCount }}
            </p>
        </div>
    </div>
</template>

<script lang="ts">
import { computed, defineComponent } from 'vue';
import IconRouterLink from '../IconRouterLink.vue';
import { useShoppingCartStore } from '@/stores/shoppingCartStore';

export default defineComponent({
    name: "ShoppingCartItem",

    props: {
        additionalClass: {
            type: String,
            required: false
        },
    },

    setup() {
        const itemsCount = computed(() => useShoppingCartStore().methods.getTotalItemsCount());

        return { itemsCount }

    },

    components: { IconRouterLink }
});
</script>