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
#span{
  width: 100%;
  position: fixed;
  bottom: 0;
}
</style>