<template>
  <form>
    <div class="data-register">
      <label for="username">Nazwa użtkownika</label>
      <input @keyup.enter="submit" v-model="username" type="text" id="username" placeholder="Podaj login">
    </div>
    <div class="data-register">
      <label for="email">E-mail</label>
      <input @keyup.enter="submit" v-model="email" type="email" id="email" placeholder="Podaj email"/>
    </div>
    <div class="data-register">
      <label for="password">Hasło</label>
      <input @keyup.enter="submit" type="password" id="password" placeholder="Podaj hasło">
    </div>
    <div class="data-register">
      <label for="confirm">Potwierdź hasło</label>
      <input @keyup.enter="submit" type="password" id="confirm" placeholder="Powtórz hasło">
    </div>
  </form>
  <p id="validation_warning"></p>
  <button @click="submit" class="option-btn">Zarejestruj</button>
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
      username: "",
      password: "",
    }
  },

  methods: {
    async submit() {        //passing data for registration action, it must be async bc we must wait for  response
      let validationWarning = document.querySelector('#validation_warning');
      if (this.validation(validationWarning)) {
        await this.userStore.userSignUp(this.username, this.email, this.password);
        if(this.userStore.getHttpCode === 400) {
          validationWarning.innerText = "Taki mail lub nazwa jest już zajęta!";
        }else if (this.userStore.getHttpCode === 200){
          validationWarning.innerText = "Zarejestrowano! Wysłano maila wryfikacyjneg na poczte."
        }else{
          validationWarning.innerText="Nie można połączyć się z serwerem!";
        }
      }
    },

    validation(validationWarning) {        //Validation of user data during registration
      if(!this.email.includes("@")){
        validationWarning.innerText = "Nie poprawny mail!"
        return false;
      }
      let passField = document.querySelector("#password").value;
      let confirmPassField = document.querySelector("#confirm").value;
      if(passField.length < 8){
        validationWarning.innerText = "Za krótkie hasło, przynajmniej 8 znaków!"
        return false;
      }
      if(passField !== confirmPassField){
        validationWarning.innerText = "Hasła nie są takie same!"
        return false;
      }
      this.password = passField;
      validationWarning.innerText = "";
      return true;
    }
  }
}
</script>

<style scoped>
.data-register{
  margin: 20px;
  display: grid;
  text-align: left;
  font-size: 20px;
}
#validation_warning {
  font-size: 20px;
  color: red;
}
</style>