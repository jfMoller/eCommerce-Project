<template>
  <div :class="['flex flex-col justify-center items-center relative', additionalClass]" @mouseover="showDropdown"
    @mouseleave="hideDropdown" @keyup.enter="handleSearch">
    <div
      class="flex justify-center items-center bg-white border border-white md:border-gray-600 rounded py-[0.5rem] md:py-0 px-6 sm:px-3 w-screen sm:w-full lg:w-[28rem]">
      <i class="fas fa-search text-black"></i>
      <input type="text" v-model="searchInput" class="px-4 py-2 w-full focus:aria-black focus:outline-none"
        :placeholder="props.placeholder" />
      <i v-if="hasCloseSearchEnabled" class="fas fa-close cursor-pointer" @click="handleCloseSearch" />
    </div>
    <div v-if="isOpenDropdown"
      class=" bg-white w-full lg:w-[28rem] transition duration-400 rounded-sm min-h-max shadow-md border border-gray-300 absolute top-[3.73rem] md:top-[2.6rem] lg:top-[2.63rem] flex flex-col p-4 space-y-2">
      <h3 class="text-base font-semibold">SORT BY</h3>
      <div class="w-full border bordet-t-gray-300"></div>
      <div class="flex flex-col text-base items-start justify-center space-y-1">
        <label>
          <input type="checkbox" :checked="filters.lowestPrice" @change="() => handleFilterChange('lowest_price')"
            class="form-checkbox h-4 w-4" />
          Lowest price
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

    additionalClass: {
      type: String,
      required: false
    },

    placeholder: {
      type: String,
      default: 'Search our products',
    },

    hasCloseSearchEnabled: {
      type: Boolean,
      default: false,
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

    function handleCloseSearch() {
      emit('onClose', true)
    }

    return { props, showDropdown, hideDropdown, searchInput, isOpenDropdown, filters, handleSearch, handleFilterChange, handleCloseSearch };
  },
});
</script>

