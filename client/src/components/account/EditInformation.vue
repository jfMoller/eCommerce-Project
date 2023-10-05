<template>
    <ChangeUsername />
    <ChangeEmail />
</template>
  
<script lang="ts">
import { defineComponent } from 'vue';
import ChangeUsername from './ChangeUsername.vue'
import ChangeEmail from './ChangeEmail.vue';

export default defineComponent({
    name: 'EditInformation',
    components: { ChangeUsername, ChangeEmail }
});

</script>
  