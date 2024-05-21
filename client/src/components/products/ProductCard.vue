<template>
    <div v-if="isForProductView" class="flex justify-center">
        <div class="bg-white p-4 w-full flex flex-col justify-center items-center md:flex-row md:items-start">
            <img :src="product.imageUrl" :alt="product.name" class="h-[15rem] inline-blockr">
            <div class="flex flex-col justify-start items-start space-y-2">
                <h3 class="text-xl text-center font-semibold mb-3 whitespace-nowrap">{{ product.name }}</h3>
                <p>{{ product.description }}</p>
                <span class="text-xl font-semibold text-black">{{ product.price }} :-</span>
                <AddToCartButton :product="product" />
            </div>
        </div>
    </div>
    <div v-else class="bg-white p-4 flex flex-col justify-between min-w-max">
        <img :src="product.imageUrl" :alt="product.name"
            class="mb-4 h-[12rem] inline-block object-scale-down cursor-pointer" @click="() => showProductView(product.id)">
        <h3 class="text-l text-center font-semibold mb-3 whitespace-nowrap">{{ product.name }}</h3>
        <div class="flex flex-col justify-between items-center">
            <AddToCartButton :product="product" />
            <span class="text-xl font-semibold text-black">{{ product.price }} :-</span>
        </div>
    </div>
</template>
  
<script lang="ts">
import { defineComponent, type PropType } from 'vue';
import AddToCartButton from './AddToCartButton.vue';
import type { Product } from '@/types/product';
import { useRouter } from 'vue-router';

export default defineComponent({
    name: "ProductCard",

    props: {
        product: {
            type: Object as PropType<Product>,
            required: true,
        },

        isForProductView: {
            type: Boolean,
            required: false,
            default: false,
        }
    },

    setup() {
        const router = useRouter()

        function showProductView(productId: string) {
            router.push({ name: 'productView', params: { productId: productId } });
        }

        return { showProductView };

    },
    components: { AddToCartButton }
})

</script>