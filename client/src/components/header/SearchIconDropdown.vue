<!-- SearchIconDropdown.vue -->
<template>
    <div v-if="isOpen" 
    class="md:hidden">
      <ProductSearchInput :hasCloseSearchEnabled="true" @onClose="onClose" />
    </div>
  </template>
  
  <script lang="ts">
  import { defineComponent, onMounted, onUnmounted, ref, watch } from 'vue';
  import ProductSearchInput from '../ProductSearchInput.vue';
  
  export default defineComponent({
    name: "SearchIconDropdown",
    props: {
      isOpen: {
        type: Boolean,
        required: true
      },
      onClose: {
        type: Function as any,
        required: true
      },
    },
    setup(props) {
      const windowWidth = ref(window.innerWidth);
  
      const handleCloseOnMd = () => {
        if (window.innerWidth >= 768 && props.isOpen) {
          props.onClose();
        }
      };
  
      watch(windowWidth, handleCloseOnMd);
  
      onMounted(() => {
        window.addEventListener('resize', handleCloseOnMd);
      });
  
      onUnmounted(() => {
        window.removeEventListener('resize', handleCloseOnMd);
      });
  
      return {};
    },
    components: { ProductSearchInput }
  });
  </script>
  