<template>
  <div class="panel">
    <h1>Logowanie</h1>
    <hr class="division">
    <LoginForm />
    <p>lub</p>
    <div id="question">
      <p>Nie masz konta?</p>
      <router-link to="/registration"> Zarejestruj się!</router-link>
    </div>
  </div>
</template>

<script>
import LoginForm from "@/components/forms/LoginForm.vue";

export default {
  name: "LoginView",
  components: {
    LoginForm
  }
}
</script>

<style scoped>

#question {
  display: flex;
  justify-content: space-around;
}

</style>