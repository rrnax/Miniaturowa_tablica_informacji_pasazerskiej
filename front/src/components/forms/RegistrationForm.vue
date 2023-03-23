<template>
  <form>
    <div class="data-register">
      <label for="email">E-mail</label>
      <input type="text" id="email" placeholder="Podaj email"/>
    </div>
    <div class="data-register">
      <label for="password">Hasło</label>
      <input type="password" id="password" placeholder="Podaj hasło">
    </div>
    <div class="data-register">
      <label for="confirm">Potwierdź hasło</label>
      <input type="password" id="confirm" placeholder="Powtórz hasło">
    </div>
  </form>
  <button @click="submit" class="submited-btn">Zarejestruj</button>
</template>

<script>
import {useAuthStore} from "@/store/auth.store";

export default {
  name: "RegistrationForm",

  setup(){      //declaring store we used to registry
    const userStore = useAuthStore();
    return { userStore };
  },

  data() {
    return {      //v-model controls reactivity passing to these data in html inputs
      email: "",
      password: "",
    }
  },

  methods: {
    async submit() {        //passing data for registration action, it must be async bc we must wait for  response
      await this.userStore.userSignUp(this.email, this.password);
      if (this.userStore.authStatus){
        this.$router.push('/');
      } else {
        alert('Nie można było się zarejestroac, problemy z serwerm');
      }
    }
  }
}
</script>

<style scoped>
.data-register{
  margin: 20px;
}
</style>