<template>
  <NavBar />
  <RouterView class="min-h-max p-10 bg-gray-50" />
  <FooterInfo />
</template>

<script setup lang="ts">
import NavBar from './components/header/NavBar.vue';
import { RouterView } from 'vue-router'
import FooterInfo from './components/footer/FooterInfo.vue'
</script>


<style>
html {
  background-color: white;
}</style>