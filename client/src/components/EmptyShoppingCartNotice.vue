<template>
    <div class=" m-auto flex justify-center items-center flex-nowrap bg-white max-w-max shadow-md rounded-md">
        <ImageFrame additionalClass="shadow-none" additionalImageClass="min-h-20 max-h-20"
            imageLink="https://cdn3.iconfinder.com/data/icons/shopping-and-ecommerce-29/90/empty_cart-512.png" />
        <TextFrame additionalClass="text-left flex flex-col justify-center shadow-none" header="Your cart is empty"
            text="Visit the shop to explore some of our excellent products." />
    </div>
</template>
  
<script lang="ts">
import TextFrame from '@/components/TextFrame.vue';
import ImageFrame from '@/components/ImageFrame.vue';
import { defineComponent } from 'vue';

export default defineComponent({
    name: "EmptyShoppingCartNotice",
    components: {
        TextFrame,
        ImageFrame
    },
})
</script>