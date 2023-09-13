<template>
  <article class="mt-10">
    <h2 class="text-3xl font-semibold mb-6">Featured Products</h2>
    <ProductCards :products="featuredProducts" />
  </article>
</template>
  
<script lang="ts">
import { defineComponent, ref } from 'vue';
import ProductCards from './ProductCards.vue';

export default defineComponent({
  name: "FeaturedProducts",
  setup() {
    const featuredProducts = ref([
      {
        _id: "1",
        name: 'Product 1',
        description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
        imageUrl: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRKuC4UGRdxb8YkthbSfLsE4QyVLzI7SjmUng&usqp=CAU',
        price: 199.99,
      },
      {
        _id: "2",
        name: 'Product 2',
        description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
        imageUrl: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLwB8LurJQi58rA_gv_8Uv2QW4JkJWvuwIcg&usqp=CAU',
        price: 299.99,
      },
      {
        _id: "3",
        name: 'Product 2',
        description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
        imageUrl: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLwB8LurJQi58rA_gv_8Uv2QW4JkJWvuwIcg&usqp=CAU',
        price: 299.99,
      },
      {
        _id: "4",
        name: 'Product 2',
        description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
        imageUrl: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLwB8LurJQi58rA_gv_8Uv2QW4JkJWvuwIcg&usqp=CAU',
        price: 299.99,
      },
    ]);

    return {
      featuredProducts,
    };
  },
  components: {
    ProductCards
  }

});
</script>