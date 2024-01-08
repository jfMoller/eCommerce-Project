<template>
  <div class="flex w-full sm:space-x-5">
    <ConfirmDialogue :isPasswordRequired="false" header="Confirm add product"
      text="Are you sure you want to add this product?" v-if="isConfirmationVisible" :onConfirm="addNewProduct"
      :onCancel="closeConfirmation" />

    <ProductPreview :product="product" />
    <div class="p-4 bg-white rounded shadow w-full sm:max-w-[50%] sm:min-w-[50%]">
      <SmallViewTitle text="Add Product" class="mb-2"/>
      <form @submit.prevent="openConfirmation">
        <div class="mb-4">
          <label for="productName" class="block text-gray-700 font-bold mb-2">Name</label>
          <input v-model="product.name" type="text" class="border w-full p-2 rounded" />
        </div>
        <div class="mb-4">
          <label for="productDescription" class="block text-gray-700 font-bold mb-2">Description</label>
          <textarea v-model="product.description" class="border w-full p-2 rounded"></textarea>
        </div>
        <div class="mb-4">
          <label for="productImageUrl" class="block text-gray-700 font-bold mb-2">Image URL</label>
          <input v-model="product.imageUrl" type="text" class="border w-full p-2 rounded" />
        </div>
        <div class="mb-4">
          <label for="productPrice" class="block text-gray-700 font-bold mb-2">Price</label>
          <input v-model="product.price" type="number" step="0.01" class="border w-full p-2 rounded" />
        </div>
        <div class="mb-4">
          <label for="productQuantity" class="block text-gray-700 font-bold mb-2">Quantity</label>
          <input v-model="product.quantity" type="number" class="border w-full p-2 rounded" />
        </div>
        <SubmitButton text="Add Product" />
      </form>
    </div>
  </div>
</template>
  
<script lang="ts">
import { defineComponent, ref } from 'vue';
import { useAdminToolsStore } from '@/stores/network/adminToolsStore';
import type { CreateProductDto } from '@/types/product';
import ConfirmDialogue from '../ConfirmDialogue.vue';
import SmallViewTitle from '../SmallViewTitle.vue';
import ProductPreview from '../ProductPreview.vue';
import SubmitButton from '../SubmitButton.vue';

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
    const isConfirmationVisible = ref<boolean>(false);

    function openConfirmation() {
      isConfirmationVisible.value = true;
    }

    function closeConfirmation() {
      isConfirmationVisible.value = false;
    }

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
    return { product, addNewProduct, isConfirmationVisible, openConfirmation, closeConfirmation };
  },
  components: { ConfirmDialogue, SmallViewTitle, ProductPreview, SubmitButton }
});
</script>
  