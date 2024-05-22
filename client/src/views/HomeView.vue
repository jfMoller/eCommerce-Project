<template>
  <section class="">
    <HeroSection />
    <FeaturedProducts />
  </section>
</template>

<script lang="ts">
import HeroSection from '@/components/HeroSection.vue';
import FeaturedProducts from '@/components/products/FeaturedProducts.vue';


export default {
  components: {
    HeroSection,
    FeaturedProducts
  },
}
</script>