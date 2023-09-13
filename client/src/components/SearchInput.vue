<template>
  <div class="w-full flex justify-center items-center">
    <input type="text" class=" max-w-md px-4 py-2 mb-6 rounded-md border border-gray-300 focus:outline-none w-full"
      :placeholder="placeholder" />
  </div>
</template>
  
<script lang="ts">
import { defineComponent } from 'vue';

export default defineComponent({
  name: "SearchInput",
  props: {
    placeholder: {
      type: String,
      default: "Search...",
    },
  },

});
</script>
  