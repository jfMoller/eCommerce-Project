<template>
  <div class="w-full flex flex-col justify-center items-center relative mb-6" @mouseover="showDropdown"
    @mouseleave="hideDropdown" @keyup.enter="handleSearch">
    <div class="flex justify-center items-center bg-white border border-gray-300 rounded px-3 w-[28rem]">
      <i class="fas fa-search text-gray-500"></i>
      <input type="text" v-model="searchInput" class="max-w-md px-4 py-2 w-full focus:aria-black focus:outline-none"
        :placeholder="props.placeholder" />
    </div>
    <div v-if="isOpenDropdown"
      class=" bg-white w-[28rem] transition duration-400 rounded-sm min-h-max shadow-md border border-gray-300 absolute top-[2.63rem] flex flex-col p-4 space-y-2">
      <h3 class="text-xl font-semibold">Sort by</h3>
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
      default: 'Search our products...',
    },
  },

  setup(props, { emit }) {
    const searchInput = ref<string>('');
    const isOpenDropdown = ref<boolean>(false);
    const filters = reactive({
      lowestPrice: false,
      highestPrice: false,
    });

    function showDropdown() {
      isOpenDropdown.value = true;
    }

    function hideDropdown() {
      isOpenDropdown.value = false;
    }

    function handleSearch() {
      emit('search', searchInput.value, filters.lowestPrice ? "lowest_price" : filters.highestPrice ? "highest_price" : null
      );
    }

    function handleFilterChange() {
      if (filters.lowestPrice) filters.highestPrice = false;
      else if (filters.highestPrice) filters.lowestPrice = false;

    }

    return { props, showDropdown, hideDropdown, searchInput, isOpenDropdown, filters, handleSearch, handleFilterChange };
  },
});
</script>
