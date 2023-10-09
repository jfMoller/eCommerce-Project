<template>
    <RouterLink :to="path" :class="[
        textClass, 
        'transition', 'duration-300', 
        'ease-in-out', 
        additionalClass]">{{ text }}</RouterLink>
  </template>
    
  <script lang="ts">
  import { RouterLink } from 'vue-router'
  
  export default {
    name: "StyledRouterLink",
    props: {
      text: {
        type: String,
        required: true
      },
      path: {
        type: String || Object,
        required: true
      },

      textClass: {
        type: String,
        default: "hover:text-gray-400 custom-font text-l",
        required: false,
      },

      additionalClass: {
        type: String,
        required: false
      }
    },
    components: {
      RouterLink,
    },
  }
  </script>

<style scoped>
.custom-font {
    font-family: Verdana, Geneva, Tahoma, sans-serif;
}
</style>