<template>
  <div class="flex w-full">
    <ConfirmDialogue :isPasswordRequired="false" header="Confirm delete product"
      text="Are you sure you want to delete this product?" v-if="isConfirmationVisible" :onConfirm="deleteProduct"
      :onCancel="closeConfirmation" />

    <ProductPreview v-if="product" :product="product" />
    <div class="p-4 bg-white rounded shadow w-full sm:max-w-[50%] sm:min-w-[50%] sm:ml-5">
      <SmallViewTitle text="Delete product" class="mb-2" />
      <div class="mb-4">
        <label for="products" class="block text-gray-700 font-bold mb-2">Product</label>
        <select v-model="selectedProductId" class="border w-full p-2 rounded" @change="loadProduct(selectedProductId)">
          <option v-for="product in products" :key="product.id" :value="product.id">
            {{ product.name }}
          </option>
        </select>
      </div>
      <form @submit.prevent="openConfirmation">
        <SubmitButton text="Delete product" additionalClass="bg-red-500 hover:bg-red-600" />
      </form>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import { useAdminToolsStore } from '@/stores/network/adminToolsStore';
import type { Product } from '@/types/product';
import { useProductStore } from '@/stores/network/productStore';
import SmallViewTitle from '../SmallViewTitle.vue';
import ConfirmDialogue from '../ConfirmDialogue.vue';
import SubmitButton from '../SubmitButton.vue';
import ProductPreview from '../ProductPreview.vue';


export default defineComponent({
  name: 'EditProduct',
  setup() {
    const adminToolsStore = useAdminToolsStore();
    const productStore = useProductStore();
    const products = ref<Product[]>([]);
    const product = ref<Product>({
      id: '',
      name: '',
      description: '',
      imageUrl: '',
      price: 0,
      quantity: 0,
    });
    const selectedProductId = ref<string>('');

    const isConfirmationVisible = ref<boolean>(false);

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

    function openConfirmation() {
      isConfirmationVisible.value = true;
    }

    function closeConfirmation() {
      isConfirmationVisible.value = false;
    }

    async function loadProduct(productId: string) {
      selectedProductId.value = productId;
      product.value = await productStore.API.getProduct(productId);
    }

    async function deleteProduct() {
      await adminToolsStore.API.deleteProduct(selectedProductId.value);
      updateProducts();
    }

    return { products, product, selectedProductId, isConfirmationVisible, openConfirmation, closeConfirmation, loadProduct, deleteProduct };
  },
  components: { SmallViewTitle, ConfirmDialogue, SubmitButton, ProductPreview }
});
</script>
