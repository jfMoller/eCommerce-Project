<template>
  <article class="mt-10">
    <h2 class="text-3xl font-semibold mb-6">Checkout</h2>

    <div class="flex flex-col items-center">
      <ProductItems :products="checkoutProducts" />

      <div class="flex flex-col items-center mb-4">
        <h2 class="text-xl font-bold">Total cost:</h2>
        <p class="text-lg">{{ totalPrice }}</p>
      </div>

      <StyledButton text="Go to payment" additionalClass="rounded-md bg-blue-500 text-white hover:bg-blue-700"
        :handleClick="() => goToPayment()" />
    </div>
  </article>
</template>
    
<script lang="ts">
import { defineComponent, computed } from 'vue';
import ProductItems from './ProductItems.vue';
import StyledButton from '../StyledButton.vue';
import { useShoppingCartStore } from '@/stores/shoppingCartStore';

export default defineComponent({
  name: "FeaturedProducts",
  setup() {
    const checkoutProducts = computed(() => useShoppingCartStore().methods.getAllItems())

    const totalPrice = computed(() => useShoppingCartStore().methods.getTotalPrice())


    function goToPayment() {
      const itemIds: String[] = useShoppingCartStore().methods.getItemIds()
    console.log(itemIds)
    }

    return {
      checkoutProducts, totalPrice, goToPayment
    };
  },
  components: {
    ProductItems, StyledButton
  }

});
</script>@/stores/shoppingCartStore