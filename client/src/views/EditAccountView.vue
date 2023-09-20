<template>
    <section>
      <AccountInfo />
      <EditInformation />
      <ChangePassword />
      <DeleteAccount />
    </section>
  </template>
  
  <script lang="ts">
  import AccountInfo from '@/components/account/AccountInfo.vue'
  import EditInformation from '@/components/account/EditInformation.vue'
  import ChangePassword from '@/components/account/ChangePassword.vue'
  import DeleteAccount from '@/components/account/DeleteAccount.vue'
  
  
  export default {
    name: 'EditAccountView',
    components: { AccountInfo, EditInformation, ChangePassword, DeleteAccount }
  }
  </script>
  