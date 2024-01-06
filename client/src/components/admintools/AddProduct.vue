<template>
  <div class="flex w-full space-x-5">
    <div class="p-4 bg-white rounded shadow w-full sm:max-w-[50%] sm:min-w-[50%]">
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
        <div class="flex justify-between">
          <div />
          <button type="submit" class="bg-blue-500 text-white py-2 px-4 rounded">Add Product</button>
        </div>
      </form>
    </div>
    <div class="bg-white p-4 shadow flex-col w-full hidden sm:flex">
      <h2 class="text-xl font-semibold mb-4">Product Preview</h2>
      <img :src="product.imageUrl" class="mb-4 h-[12rem] inline-block object-scale-down cursor-pointer border p-4 w-full">
      <div class="space-y-5">
        <h3 class="text-l font-semibold">{{ product.name === '' ? "Title" : product.name }}</h3>
        <p>{{ product.description === '' ? "Description" : product.description }}</p>
        <div class="text-l font-semibold text-black">Price: {{ product.price }} :-</div>
      </div>
    </div>
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
      await adminToolsStore.API.addProduct(newProduct);
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
  