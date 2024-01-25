<template>
  <div v-if="props.isOpen" @click="props.onClickOutside"
    class="md:hidden fixed top-0 right-0 shadow-md bg-transparent outside-search-components h-screen">
    <ProductSearchInput additionalClass="w-screen"
     :hasCloseSearchEnabled="true" @onClose="props.onClose" />
  </div>
</template>
  
<script lang="ts">
import { defineComponent, type PropType } from 'vue';
import ProductSearchInput from '../ProductSearchInput.vue';

export default defineComponent({
  name: "ProductSearchDropdown",
  props: {
    isOpen: {
      type: Boolean,
      required: true
    },
    onClose: {
      type: Function as any,
      required: true
    },
    onClickOutside: {
            type: Function as PropType<(event: MouseEvent) => void>,
            required: true
        },
  },
  setup(props) {

    return { props };
  },
  components: { ProductSearchInput }
});
</script>
  