<template>
  <section>
    <AddProduct />
    <EditProduct />
    <DeleteProduct />
  </section>
</template>
  
<script lang="ts">
import AddProduct from '@/components/admintools/AddProduct.vue';
import DeleteProduct from '@/components/admintools/DeleteProduct.vue';
import EditProduct from '@/components/admintools/EditProduct.vue';

export default {
  name: 'HandleProductsView',
  components: { AddProduct, EditProduct, DeleteProduct }
}
</script>
  