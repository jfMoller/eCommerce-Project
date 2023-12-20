<template>
  <StyledButton text="Buy now" :disabled="isBuyButtonDisabled" :class="buttonStyles" additionalClass="mb-3"
    :handleClick="handleBuyButtonClick" />
</template>
  
<script lang="ts">
import { defineComponent, computed, ref } from 'vue';
import StyledButton from '../StyledButton.vue';
import { useShoppingCartStore } from '../../stores/shoppingCartStore';
import { useProductStore } from '@/stores/network/productStore';
import type { Product } from '@/types/product';

export default defineComponent({
  name: "BuyNowButton",
  props: {
    product: {
      type: Object as () => Product,
      required: true,
    },
  },

  setup(props) {
    const shoppingCartStore = useShoppingCartStore();
    const productStore = useProductStore();
    const isBuyButtonDisabled = ref<boolean>(props.product.quantity <= 0);

    const buttonStyles = computed(() => ({
      'bg-gray-300': isBuyButtonDisabled.value,
      'text-gray-500': isBuyButtonDisabled.value,
      'cursor-not-allowed': isBuyButtonDisabled.value,
      'hover:bg-gray-300': isBuyButtonDisabled.value,
      'hover:text-gray-500': isBuyButtonDisabled.value,
      'border-gray-400': isBuyButtonDisabled.value,
    }));

    async function handleBuyButtonClick() {
      const targetProduct: Product = await productStore.API.getProduct(props.product.id);
      const productAmount: number = shoppingCartStore.states.productIds.filter(id => id === props.product.id).length;

      if (targetProduct.quantity > productAmount) {
        shoppingCartStore.methods.addProductId(props.product.id);
      } else {
        isBuyButtonDisabled.value = true
      }
      
    }

    return {
      isBuyButtonDisabled,
      buttonStyles,
      handleBuyButtonClick,
    };
  },
  components: { StyledButton },
});
</script>
  