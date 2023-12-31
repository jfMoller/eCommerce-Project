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
      <div class="flex flex-col text-lg items-start justify-center space-y-1">
        <label>
          <input type="checkbox" :checked="filters.lowestPrice" @change="() => handleFilterChange('lowest_price')"
            class="form-checkbox h-4 w-4" />
          Lowest Price
        </label>
        <label>
          <input type="checkbox" :checked="filters.highestPrice" @change="() => handleFilterChange('highest_price')"
            class="form-checkbox h-4 w-4" />
          Highest price
        </label>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, reactive, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';


export default defineComponent({
  name: 'SearchInput',
  props: {
    placeholder: {
      type: String,
      default: 'Search our products...',
    },
    query: String,
    filter: String
  },

  setup(props, { emit }) {
    const searchInput = ref<string>('');
    const isOpenDropdown = ref<boolean>(false);
    const filters = reactive({
      lowestPrice: false,
      highestPrice: false,
    });
    const router = useRouter();
    const route = useRoute();

    onMounted(async () => {
      const query = route.query.query as string;
      const filter = route.query.filter as string;

      searchInput.value = query;

      if (filter == "lowest_price") {
        filters.lowestPrice = true;

      } else if (filter == "highest_price") {
        filters.highestPrice = true;
      }

    });

    function showDropdown() {
      isOpenDropdown.value = true;
    }

    function hideDropdown() {
      isOpenDropdown.value = false;
    }

    function handleSearch() {
      emit('search',
        searchInput.value ? searchInput.value : '',
        filters.lowestPrice ? "lowest_price" : filters.highestPrice ? "highest_price" : null
      );

      const queryParameters = {
        query: searchInput.value,
        filter: filters.lowestPrice ? 'lowest_price' : filters.highestPrice ? 'highest_price' : null,
      };
      router.push({ name: 'shop', query: queryParameters });
    }

    function handleFilterChange(targetFilter: string) {
      if (targetFilter === "lowest_price") {
        filters.lowestPrice = !filters.lowestPrice;
        filters.highestPrice = false;
      }

      if (targetFilter === "highest_price") {
        filters.highestPrice = !filters.highestPrice;
        filters.lowestPrice = false;
      }

    }

    return { props, showDropdown, hideDropdown, searchInput, isOpenDropdown, filters, handleSearch, handleFilterChange };
  },
});
</script>
