<template>
  <article class="hero-background h-96 flex items-center justify-center text-white">
    <div class="text-center">
      <h1 class="text-3xl sm:text-5xl font-extrabold mb-4">Welcome to Our Store</h1>
      <p class="text-md sm:text-lg mb-6">Discover the finest products curated just for you.</p>
      <router-link to="/shop"
        class="text-white bg-blue-600 hover:bg-blue-700 py-2 px-6 rounded-full text-lg font-semibold transition duration-300 ease-in-out inline-block">Shop
        Now</router-link>
    </div>
  </article>
</template>

<script lang="ts">
import { RouterLink } from 'vue-router'

export default {
  name: "HeroSection",
  components: {
    RouterLink,
  },
}
</script>

<style scoped>
.hero-background {
  background-image: url('https://images.prismic.io/vast-sg/be4ee1fc-11a8-489f-ad2f-0a9a6705f894_wareever-background-2.jpg?auto=compress,format&w=1536&h=1024&fm=webp');
  background-size: cover;
  background-position: center;
}
</style>