<template>
  <div class="p-4 bg-white rounded shadow">
    <h2 class="text-xl font-semibold mb-4">Edit Product</h2>

    <div class="mb-4">
      <label for="products" class="block text-gray-700 font-bold mb-2">Product:</label>
      <select v-model="selectedProductId" class="border w-full p-2 rounded" @change="loadProduct(selectedProductId)">
        <option v-for="product in products" :key="product.id" :value="product.id">
          {{ product.name }}
        </option>
      </select>
    </div>
    <form @submit.prevent="editProduct">
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
      <button type="submit" class="bg-blue-500 text-white py-2 px-4 rounded">Edit Product</button>
    </form>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import { useAdminToolsStore } from '@/stores/network/adminToolsStore';
import type { EditProductDto, Product } from '@/types/product';
import { useProductStore } from '@/stores/network/productStore';

export default defineComponent({
  name: 'EditProduct',


  setup() {
    const adminToolsStore = useAdminToolsStore();
    const productStore = useProductStore();

    const products = ref<Product[]>([]);

    onMounted(async () => {
      products.value = await productStore.API.getAllProducts();

        if (products.value.length > 0) {
        selectedProductId.value = products.value[0].id;

        loadProduct(selectedProductId.value);
      }
    });

    const selectedProductId = ref<string>('');

    const product = ref<EditProductDto>({
      name: '',
      description: '',
      imageUrl: '',
      price: 0,
      quantity: 0,
    });

    async function loadProduct(productId: string) {
      console.log(productId)
      selectedProductId.value = productId;

      
      const response = await productStore.API.getProduct(productId);

      selectedProductId.value = productId;
      product.value.name = response.name;
      product.value.description = response.description;
      product.value.imageUrl = response.imageUrl;
      product.value.price = response.price;
      product.value.quantity = response.quantity;

    }

    async function editProduct() {
      const editedProduct = { ...product.value };
      console.log(editedProduct);
      const response = await adminToolsStore.API.editProduct(selectedProductId.value, editedProduct);
      console.log(response);
    }

    return { products, selectedProductId, product, loadProduct, editProduct };
  },
});
</script>
