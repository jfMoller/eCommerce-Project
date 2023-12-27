<template>
  <div class="w-full flex justify-center items-center">
    <input type="text" v-model="searchInput"
      class="max-w-md px-4 py-2 mb-6 rounded-md border border-gray-300 focus:outline-none w-full"
      :placeholder="placeholder" />
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, watch } from 'vue';

export default defineComponent({
  name: "SearchInput",
  props: {
    placeholder: {
      type: String,
      default: "Search...",
    },
  },

  setup(props, { emit }) {
    const searchInput = ref<string>('');

    watch(searchInput, () => {
      emit('search', searchInput.value);
    });

    return { searchInput };
  }
});
</script>
