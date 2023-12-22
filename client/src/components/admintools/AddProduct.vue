<template>
  <div class="p-4 bg-white rounded shadow">
    <h2 class="text-xl font-semibold mb-4">Add New Product</h2>
    <form @submit.prevent="addNewProduct">
      <div class="mb-4">
        <label for="productName" class="block text-gray-700 font-bold mb-2">Name:</label>
        <input v-model="product.name" type="text" class="border w-full p-2 rounded" />
      </div>
      <div class="mb-4">
        <label for="productDescription" class="block text-gray-700 font-bold mb-2">Description:</label>
        <textarea v-model="product.description" class="border w-full p-2 rounded"></textarea>
      </div>
      <div class="mb-4">
        <label for="productImageUrl" class="block text-gray-700 font-bold mb-2">Image URL:</label>
        <input v-model="product.imageUrl" type="text" class="border w-full p-2 rounded" />
      </div>
      <div class="mb-4">
        <label for="productPrice" class="block text-gray-700 font-bold mb-2">Price:</label>
        <input v-model="product.price" type="number" step="0.01" class="border w-full p-2 rounded" />
      </div>
      <div class="mb-4">
        <label for="productQuantity" class="block text-gray-700 font-bold mb-2">Quantity:</label>
        <input v-model="product.quantity" type="number" class="border w-full p-2 rounded" />
      </div>
      <button type="submit" class="bg-blue-500 text-white py-2 px-4 rounded">Add Product</button>
    </form>
  </div>
</template>
  
<script lang="ts">
import { defineComponent, ref } from 'vue';
import { useAdminToolsStore } from '@/stores/network/adminToolsStore';
import type { CreateProductDto } from '@/types/product';

export default defineComponent({
  name: 'AddProduct',
  setup() {
    const adminToolsStore = useAdminToolsStore();

    const product = ref<CreateProductDto>({
      name: '',
      description: '',
      imageUrl: '',
      price: 0,
      quantity: 0,
    });

    async function addNewProduct() {
      const newProduct = { ...product.value };
      console.log(product)
      const response = await adminToolsStore.API.addProduct(newProduct);
      console.log(response)
      resetProductValues();
    }

    function resetProductValues() {
      product.value = {
        name: '',
        description: '',
        imageUrl: '',
        price: 0,
        quantity: 0,
      };
    }

    return { product, addNewProduct };
  },
});
</script>
  