<template>
    <EditUsername />
</template>
  
<script lang="ts">
import { defineComponent } from 'vue';
import EditUsername from './EditUsername.vue'

export default defineComponent({
    name: 'EditInformation',
    components: { EditUsername }
});

</script>
  