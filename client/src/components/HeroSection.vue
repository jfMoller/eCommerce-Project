<template>
  <article class="hero-background h-96 flex items-center justify-center text-white">
    <div class="text-center">
      <h1 class="text-3xl sm:text-5xl font-extrabold mb-4">The finest beans</h1>
      <p class="text-md sm:text-lg mb-6">Discover the finest products curated just for you.</p>
      <router-link to="/shop"
        class="text-white py-2 px-6 rounded-md outline text-lg font-semibold transition duration-300 ease-in-out inline-block">Shop
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
  background-image: url('../resources/coffee-beans.jpg');
  background-size: cover;
  background-repeat: no-repeat;
}
</style>