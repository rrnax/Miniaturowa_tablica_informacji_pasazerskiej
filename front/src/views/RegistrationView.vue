<template>
  <div class="panel">
    <h1>Rejestracja</h1>
    <hr class="division">
    <RegistrationForm />
  </div>
  <Footer class="tothebootom"/>
</template>

<script>
import RegistrationForm from "@/components/forms/RegistrationForm.vue";
import Footer from "@/components/Footer.vue";

export default {
  name: "RegistrationView",
  components: {
    RegistrationForm,
    Footer,
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