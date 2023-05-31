<template>
  <div class="panel">
    <h1>Rejestracja</h1>
    <hr class="division">
    <RegistrationForm />
  </div>
  <Vuefooter/>
</template>

<script>
import RegistrationForm from "@/components/forms/RegistrationForm.vue";
import Vuefooter from './Vuefooter.vue';

export default {
  name: "RegistrationView",
  components: {
    RegistrationForm,
    Vuefooter,
  },
}
</script>

<style scoped>

</style>