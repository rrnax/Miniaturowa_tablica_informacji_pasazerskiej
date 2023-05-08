<template>
  <div class="panel">
    <h1>Rejestracja</h1>
    <hr class="division">
    <RegistrationForm />
  </div>
</template>

<script>
import RegistrationForm from "@/components/forms/RegistrationForm.vue";

export default {
  name: "RegistrationView",
  components: {
    RegistrationForm,
  },
}
</script>

<style scoped>

</style>