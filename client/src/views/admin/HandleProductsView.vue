<template>
  <section>
    <AddProduct />
    <EditProduct />
  </section>
</template>
  
<script lang="ts">
import AddProduct from '@/components/admintools/AddProduct.vue';
import EditProduct from '@/components/admintools/EditProduct.vue';



export default {
  name: 'HandleProductsView',
  components: { AddProduct, EditProduct }
}
</script>
  