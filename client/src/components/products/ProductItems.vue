<template>
  <ul class="flex flex-col justify-center items-center">
    <li v-for="(productGroup, id) in groupedProducts" :key="id"
      class="bg-white p-4 mb-4 max-w-md shadow-sm rounded-lg flex justify-between items-center">
      <div>
        <h3 class="text-xl font-semibold mb-2">{{ productGroup[0].name }}
          <span v-if="productGroup.length > 1 || productGroup.length === 1"
            class="text-gray-600 text-xl font-semibold mb-2"> x {{ productGroup.length }}</span>
        </h3>
        <p class="text-gray-700">{{ productGroup[0].description }}</p>
      </div>
      <div class="flex items-center">
        <span class="text-xl font-semibold text-blue-600 mr-4">{{ productGroup[0].price }}</span>
        <div class="flex items-center space-x-2">
          <StyledButton text="+" additionalClass="rounded-md"
            :handleClick="() => increaseItemQuantity(productGroup[0])" />
          <StyledButton text="-" additionalClass="rounded-md"
            :handleClick="() => decreaseItemQuantity(productGroup[0])" />
        </div>
      </div>
    </li>

  </ul>
</template>

<script lang="ts">
import { computed, defineComponent } from 'vue';
import StyledButton from '../StyledButton.vue';
import { useShoppingCartStore } from '../../stores/shoppingCartStore';
import type { Product } from '@/types/product'

export default defineComponent({
  name: "ProductItems",
  props: {
    products: {
      type: Array<Product>,
      required: true
    }
  },

  setup(props) {

    const groupedProducts = computed(() => {
      const grouped: Record<string, Product[]> = {};
      props.products.forEach(product => {
        if (grouped[product.id]) {
          grouped[product.id].push(product);
        } else {
          grouped[product.id] = [product];
        }
      });
      return grouped;
    });

    const increaseItemQuantity = (product: Product) => {
      useShoppingCartStore().methods.addItem(product)
    };

    const decreaseItemQuantity = (product: Product) => {
      useShoppingCartStore().methods.removeItem(product)
    };

    return {
      props,
      groupedProducts,
      increaseItemQuantity,
      decreaseItemQuantity
    };
  },
  components: { StyledButton }
});
</script>../../stores/shoppingCartStore@/types/product