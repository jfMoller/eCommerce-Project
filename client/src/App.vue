<template>
  <NavBar />
  <RouterView class="bg-gray-50 min-h-max p-10" />
  <FooterInfo />
</template>

<script setup lang="ts">
import NavBar from './components/header/NavBar.vue';
import { RouterView } from 'vue-router'
import FooterInfo from './components/footer/FooterInfo.vue'
</script>

<style>
html {
  background-color: rgb(31 41 55);
}
</style>