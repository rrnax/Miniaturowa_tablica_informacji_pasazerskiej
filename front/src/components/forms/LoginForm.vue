<template>
  <form>
    <div class="data-input">
      <label for="username">Nazwa użytkownika</label>
      <input @keyup.enter="submit" v-model="username" type="text" id="username" placeholder="Podaj nazwe użytkownika"/>
    </div>
    <div class="data-input">
      <label for="password">Hasło</label>
      <input @keyup.enter="submit" v-model="password" type="password" id="password" placeholder="Podaj hasło"/>
    </div>
  </form>
  <button @click="submit" class="option-btn sumbit">Zaloguj</button>
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
        this.$router.push('/stations');
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
  margin: 20px;
  display: grid;
  text-align: left;
  font-size: 20px;
}

#response-warning {
  font-size: 20px;
  color: red;
}


</style>