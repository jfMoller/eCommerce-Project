<template>
  <div class="w-full flex flex-col justify-center items-center relative">
    <div class="flex justify-center items-center bg-white border border-gray-300 rounded px-3 w-[28rem]">
      <i class="fas fa-search text-gray-500"></i>
    <input type="text" v-model="searchInput" @keyup.enter="handleSearch"
      class="max-w-md px-4 py-2 w-full focus:aria-none"
      :placeholder="placeholder" />
    </div>
    <div
      class=" bg-white w-[28rem] rounded-sm min-h-max shadow-md border border-gray-300 absolute top-[2.68rem] flex flex-col p-4 space-y-2">
      <h3 class="text-xl font-semibold">Filter</h3>
      <div class="w-full border bordet-t-gray-300"></div>
      <label>
        <input type="checkbox" v-model="filters.lowestPrice" @change="handleFilterChange" />
        Lowest Price
      </label>
      <label>
        <input type="checkbox" v-model="filters.highestPrice" @change="handleFilterChange" />
        Highest price
      </label>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, reactive } from 'vue';

export default defineComponent({
  name: 'SearchInput',
  props: {
    placeholder: {
      type: String,
      default: 'Search our product',
    },
  },

  setup(props, { emit }) {
    const searchInput = ref<string>('');
    const isOpenDropdown = ref<boolean>(false);
    const filters = reactive({
      lowestPrice: false,
      highestPrice: false,
    });

    function handleSearch() {
      emit('search', searchInput.value);
    }

    function handleFilterChange() {
      emit('search', { query: searchInput.value, filters });
    }

    return { searchInput, isOpenDropdown, filters, handleSearch, handleFilterChange };
  },
});
</script>
