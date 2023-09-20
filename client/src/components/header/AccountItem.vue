<template>
    <div class="cursor-pointer transition duration-300" @mouseover="showPopup" @mouseleave="hidePopup">
        <IconRouterLink iconClass="fas fa-user" path="/account" />
        <AccountOrLogoutPopup v-if="isShowingPopup" />
    </div>
</template>
  
<script lang="ts">
import AccountOrLogoutPopup from '../AccountOrLogoutPopup.vue';

import { defineComponent, ref } from 'vue';
import IconRouterLink from '../IconRouterLink.vue';

export default defineComponent({
    name: "AccountItem",
    setup() {
        const isShowingPopup = ref(false);

        function showPopup() {
            isShowingPopup.value = true;
        }

        function hidePopup() {
            isShowingPopup.value = false;
        }

        return { isShowingPopup, showPopup, hidePopup }

    },
    components: {
        AccountOrLogoutPopup,
        IconRouterLink
    },
})
</script>