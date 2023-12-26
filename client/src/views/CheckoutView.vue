<template>
  <section>
    <OngoingOrder />
    <FeaturedProducts />
  </section>
</template>

<script lang="ts">
import OngoingOrder from '@/components/products/OngoingOrder.vue';
import { defineComponent, computed } from 'vue';
import { useShoppingCartStore } from '@/stores/shoppingCartStore';
import FeaturedProducts from '@/components/products/FeaturedProducts.vue';


export default defineComponent({
  setup() {
    const shoppingCartStore = useShoppingCartStore();

    const amountOfCartItems = computed(() => shoppingCartStore.methods.getTotalItemsCount())

    return { amountOfCartItems }
  },

  components: {
    OngoingOrder,
    FeaturedProducts,

  },
})
</script>