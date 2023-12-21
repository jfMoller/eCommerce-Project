<template>
  <div class="p-4 bg-white rounded shadow">
    <h2 class="text-xl font-semibold mb-4">Delete Product</h2>

    <div class="mb-4">
      <label for="products" class="block text-gray-700 font-bold mb-2">Product:</label>
      <select v-model="selectedProductId" class="border w-full p-2 rounded" @change="loadProduct(selectedProductId)">
        <option v-for="product in products" :key="product.id" :value="product.id">
          {{ product.name }}
        </option>
      </select>
    </div>
    <form @submit.prevent="deleteProduct">
      <div v-if="product != null" :key="product.id" class="bg-white p-4 flex justify-start">
        <img :src="product.imageUrl" :alt="product.name"
          class="mb-4 h-[12rem] inline-block object-scale-down cursor-pointer">
        <div class="flex justify-start items-start flex-col">
          <h3 class="text-l text-center font-semibold mb-3"><span class="font-bold">Name:</span> {{ product.name }}</h3>
          <h3 class="text-l text-center font-semibold mb-3"><span class="font-bold">Description:</span> {{
            product.description }}</h3>
          <h3 class="text-l text-center font-semibold mb-3"><span class="font-bold">Price:</span> {{ product.price }}</h3>
          <h3 class="text-l text-center font-semibold mb-3"><span class="font-bold">Quantity:</span> {{ product.quantity
          }}</h3>
        </div>
      </div>
      <button type="submit" class="bg-red-500 text-white py-2 px-4 rounded">Delete Product</button>
    </form>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import { useAdminToolsStore } from '@/stores/network/adminToolsStore';
import type { Product } from '@/types/product';
import { useProductStore } from '@/stores/network/productStore';

export default defineComponent({
  name: 'EditProduct',


  setup() {
    const adminToolsStore = useAdminToolsStore();
    const productStore = useProductStore();

    const products = ref<Product[]>([]);

    async function updateProducts() {
      products.value = await productStore.API.getAllProducts();

      if (products.value.length > 0) {
        selectedProductId.value = products.value[0].id;
        loadProduct(selectedProductId.value);
      }
    }
    onMounted(async () => {
      updateProducts();
    });

    const product = ref<Product | null>(null);

    const selectedProductId = ref<string>('');

    async function loadProduct(productId: string) {
      selectedProductId.value = productId;
      product.value = await productStore.API.getProduct(productId);

    }

    async function deleteProduct() {
      const response = await adminToolsStore.API.deleteProduct(selectedProductId.value)
      updateProducts()
    }

    return { products, product, selectedProductId, loadProduct, deleteProduct };
  },
});
</script>
