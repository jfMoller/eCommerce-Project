<template>
  <header
    class="bg-white py-4 px-6 lg:py-6 sticky top-0 z-999 border-b-4 border-white custom-box-shadow flex items-center justify-between md:justify-center space-x-6">
    <HeaderLogo />
    <!--     Desktop view -->
    <ProductSearchInput class="hidden md:flex" />
    <NavBarItems additionalClass="hidden md:flex space-x-6 justify-center items-center" />

    <!-- Phone view -->
    <div class="md:hidden cursor-pointer space-x-6 text-l flex justify-center items-center">
      <SearchItem @toggleSearchInput="toggleSearchInput" />
      <HamburgerIcon :handleOnClick="toggleAsideVisibility" />
      <ShoppingCartItem />
    </div>
    <ProductSearchDropdown :isOpen="isSearchInputOpen" :onClose="closeSearchInput"
      :onClickOutside="handleClickOutsideSearchInput" />
  </header>
  <HamburgerDropdown :isOpen="isAsideOpen" :onClose="closeAside" :onClickOutside="handleClickOutsideAside" />
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import HeaderLogo from './HeaderLogo.vue'
import ProductSearchInput from '../ProductSearchInput.vue';
import HamburgerIcon from './HamburgerIcon.vue'
import SearchItem from './SearchItem.vue';
import NavBarItems from './NavBarItems.vue';
import ShoppingCartItem from './ShoppingCartItem.vue';
import HamburgerDropdown from './HamburgerDropdown.vue';
import ProductSearchDropdown from '../products/ProductSearchDropdown.vue';

export default defineComponent({
  name: "NavBar",

  setup() {
    const isAsideOpen = ref<boolean>(false);
    const isSearchInputOpen = ref(false);

    function toggleAsideVisibility() {
      isAsideOpen.value = !isAsideOpen.value
    }

    function closeAside() {
      isAsideOpen.value = false
    }

    function handleClickOutsideAside(event: any) {
      if (event.target.className.includes("outside-aside-components")) {
        closeAside()
      }
    }

    function toggleSearchInput(isShowingSearchInput: boolean) {
      isSearchInputOpen.value = isShowingSearchInput;
    }

    function handleClickOutsideSearchInput(event: any) {
      if (event.target.className.includes("outside-search-components")) {
        closeSearchInput()
      }
    }

    function closeSearchInput() {
      isSearchInputOpen.value = false;
    }

    return {
      isAsideOpen,
      toggleAsideVisibility,
      closeAside,
      handleClickOutsideAside,
      isSearchInputOpen,
      toggleSearchInput,
      handleClickOutsideSearchInput,
      closeSearchInput
    }

  },
  components: {
    HeaderLogo, ProductSearchInput, SearchItem, HamburgerIcon, NavBarItems, ShoppingCartItem, HamburgerDropdown,
    ProductSearchDropdown
  },
})
</script>

<style scoped>
.custom-box-shadow {
  box-shadow: rgba(0, 0, 0, 0.05) 0px 6px 24px 0px, rgba(0, 0, 0, 0.08) 0px 0px 0px 1px;
}
</style>