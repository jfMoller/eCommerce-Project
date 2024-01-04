<template>
    <div class="cursor-pointer transition duration-300" @mouseover="showPopup" @mouseleave="hidePopup">
        <IconRouterLink iconClass="fas fa-sliders" path="/admin_tools" />
        <p></p>
        <AdminToolsPopup v-if="isShowingPopup" />
    </div>
</template>
  
<script lang="ts">
import AdminToolsPopup from './AdminToolsPopup.vue';

import { defineComponent, ref } from 'vue';
import IconRouterLink from '../IconRouterLink.vue';

export default defineComponent({
    name: "AdminToolsItem",
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
        AdminToolsPopup,
        IconRouterLink
    },
})
</script>