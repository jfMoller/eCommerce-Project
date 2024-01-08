<template>
    <div class="bg-white p-4 shadow flex-col w-full hidden sm:flex">
        <SmallViewTitle text="Product Preview" class="mb-4" />

        <img :src="product.imageUrl" class="mb-4 h-[12rem] inline-block object-scale-down cursor-pointer border p-4 w-full">
        <div class="space-y-5">
            <h3 class="text-l font-semibold">{{ product.name === '' ? "Title" : product.name }}</h3>
            <p>{{ product.description === '' ? "Description" : product.description }}</p>
            <div class="text-l font-semibold text-black">Price: {{ product.price }} :-</div>
        </div>

    </div>
</template>

<script lang ="ts">
import SmallViewTitle from './SmallViewTitle.vue';
import { defineComponent } from 'vue';

export default defineComponent({
    name: "ProductPreview",
    props: {
        product: {
            type: Object,
            required: true
        }
    },
    components: { SmallViewTitle }
})
</script>