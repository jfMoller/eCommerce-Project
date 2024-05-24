<template>
  <StyledButton :text="isButtonDisabled ? 'OUT OF STOCK' : 'ADD TO CART'" :disabled="isButtonDisabled" :class="[buttonStyles, 'font-extrabold text-sm']"
    :handleClick="handleBuyButtonClick" />
</template>
  
<script lang="ts">
import { defineComponent, computed, ref } from 'vue';
import StyledButton from '../StyledButton.vue';
import { useShoppingCartStore } from '../../stores/shoppingCartStore';
import { useProductStore } from '@/stores/network/productStore';
import type { Product } from '@/types/product';

export default defineComponent({
  name: "AddToCartButton",
  props: {
    product: {
      type: Object as () => Product,
      required: true,
    },
  },

  setup(props) {
    const shoppingCartStore = useShoppingCartStore();
    const productStore = useProductStore();
    const isButtonDisabled = ref<boolean>(props.product.quantity <= 0);

    const buttonStyles = computed(() => ({
      'bg-gray-300': isButtonDisabled.value,
      'text-gray-500': isButtonDisabled.value,
      'cursor-not-allowed': isButtonDisabled.value,
      'hover:bg-gray-300': isButtonDisabled.value,
      'hover:text-gray-500': isButtonDisabled.value,
      'border-gray-400': isButtonDisabled.value,
    }));

    async function handleBuyButtonClick() {
      const targetProduct: Product = await productStore.API.getProduct(props.product.id);
      const productAmount: number = shoppingCartStore.states.productIds.filter(id => id === props.product.id).length;

      if (targetProduct.quantity > productAmount) {
        shoppingCartStore.methods.addProductId(props.product.id);
      } else {
        isButtonDisabled.value = true
      }
      
    }

    return {
      isButtonDisabled,
      buttonStyles,
      handleBuyButtonClick,
    };
  },
  components: { StyledButton },
});
</script>
  