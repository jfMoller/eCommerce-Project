<template>
  <section class="p-4">
    <UsersOrdersTable />
  </section>
</template>

<script lang="ts">
import UsersOrdersTable from '@/components/admintools/UsersOrdersTable.vue';

export default {
  name: 'HandleProductsView',
  components: { UsersOrdersTable }
};
</script>
