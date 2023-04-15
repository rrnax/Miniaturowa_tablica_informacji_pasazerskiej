<template>
  <form>
    <div class="data-input">
      <label for="username">Nazwa użytkownika</label>
      <input v-model="username" type="text" id="username" placeholder="Podaj nazwe użytkownika"/>
    </div>
    <div class="data-input">
      <label for="password">Hasło</label>
      <input v-model="password" type="password" id="password" placeholder="Podaj hasło"/>
    </div>
  </form>
  <button @click="submit" class="submited-btn">Zaloguj</button>
  <p id="response-warning"></p>
</template>

<script>
import {useAuthStore} from "@/store/auth.store";

export default {
  name: "LoginForm",

  setup(){        //declaring store we used to login
    const userStore = useAuthStore();
    return { userStore };
  },

  data(){
    return{       //v-model controls reactivity passing to these data in html inputs
      username: "",
      password: "",
    }
  },

  methods: {
    async submit() {        //passing data for loging action, it must be async bc we must wait for response
      await this.userStore.userSignIn(this.username, this.password);
      if (this.userStore.getAuthStatus){
        this.userStore.sessionIntervalStart();
        this.$router.push('/');
      } else {      //deal with bad request or crashed backend srver
        let responseWarning = document.querySelector("#response-warning");
        if(this.userStore.getHttpCode === 400){
          responseWarning.innerText="Nieprawidłowe hasło lub nazwa!";
        }else{
          responseWarning.innerText="Nie można połączyć się z serwerem!";
        }
      }
    }
  }
}
</script>

<style scoped>

.data-input {
  margin: 10px;
}

</style>