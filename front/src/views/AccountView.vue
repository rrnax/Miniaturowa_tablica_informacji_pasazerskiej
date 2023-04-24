<template>
  <div class="account">
    <div class="section-title">
      <h1 class="section-header">Dane logowania</h1>
      <hr>
    </div>
    <div class="login-data">
     <ul class="column">
       <li class="row">
         <div class="edit-field">
           <p>Nazwa użytkownika</p>
           <input v-model="nameInput" class="editable-inputs" v-if="nameEditon" id="name">
           <p v-if="!nameEditon" class="changable">{{ userStore.getName }}</p>
         </div>
         <div>
           <a v-if="!nameEditon" class="edit-btn" @click="makeInputName()">Edytuj</a>
           <a v-if="nameEditon" class="edit-btn" >Zapisz</a>
           <p v-if="nameEditon" style="display: inline"> | </p>
           <a v-if="nameEditon" class="edit-btn" @click="this.nameEditon = !this.nameEditon">Cofnij</a>
         </div>
       </li>
       <li class="row">
         <div class="edit-field">
           <p>Email</p>
           <input v-model="emailInput" class="editable-inputs" v-if="emailEditon" id="email"/>
           <p v-if="!emailEditon" class="changable">{{ userStore.getEmail }}</p>
         </div>
         <div>
           <a v-if="!emailEditon" class="edit-btn" @click="this.makeInputEmail()">Edytuj</a>
           <a v-if="emailEditon" class="edit-btn" @click="this.updateEmail">Zapisz</a>
           <p v-if="emailEditon" style="display: inline"> | </p>
           <a v-if="emailEditon" class="edit-btn" @click="this.emailEditon = !this.emailEditon">Cofnij</a>
         </div>
       </li>
     </ul>
    </div>
    <div class="section-title">
      <h1 class="section-header">Bezpieczeństwo</h1>
      <hr>
    </div>
    <div class="security-data">
      <label for="old-password">Stare hasło</label>
      <input v-model="oldPassword" type="password" id="old-password"/>
      <label for="new-password">Nowe hasło</label>
      <input v-model="newPassword" type="password" id="new-password"/>
      <a @click="this.updatePassword" class="save-pass">Zmień hasło</a>
    </div>
    <div class="section-title">
      <h1 class="section-header">Urządzenia powiązane z kontem</h1>
      <hr>
    </div>
    <div class="user-devices">
      <table>
        <tr>
          <th>Urządzenie</th>
          <th>Status</th>
          <th>Akcja</th>
        </tr>
        <tr>
          <td>p1</td>
          <td>aktywne</td>
          <td><a class="edit-btn">resetuj</a></td>
        </tr>
        <tr>
          <td>p2</td>
          <td>wylaczone</td>
          <td><a class="edit-btn">resetuj</a></td>
        </tr>
      </table>
    </div>
    <div class="section-title">
      <h1 class="section-header">Zarządzanie kontem</h1>
      <hr>
    </div>
    <div class="delete-btn">
      <button @click="deleteAccount" class="danger-btn">Usuń konto</button>
    </div>
  </div>
</template>

<script>
import {useUserStore} from "@/store/user.stroe";

export default {
  name: "AccountView",

  data(){
    return{
      emailEditon: false,
      nameEditon: false,
      emailInput: "",
      nameInput: "",
      oldPassword: "",
      newPassword: ""
    }
  },

  setup(){
    const userStore = useUserStore();
    return { userStore };
  },

  methods: {
    makeInputEmail(){
      this.emailEditon = !this.emailEditon;
      this.emailInput = this.userStore.getEmail;
    },

    makeInputName(){
      this.nameEditon = !this.nameEditon;
      this.nameInput = this.userStore.getName;
    },

    async updateEmail(){
      await this.userStore.saveNewEmail(this.emailInput);
    },

    async updatePassword(){
      if(this.oldPassword === this.userStore.getPassword){
        await this.userStore.saveNewPassword(this.newPassword);
      } else {
        alert("Podano nieprawidłowe hasło");
      }
    },

    async deleteAccount(){
      if(confirm("Na pewno chcesz usunąć konto?") === true){
        await this.userStore.deleteUser();
        this.userStore.$reset();
        this.$router.push("/login");
      }
    }
  }
}
</script>

<style scoped>

.account {
  font-family: 'Teko', sans-serif;
  color: var(--appblue);
  width: 60%;
  display: grid;
  margin: auto;
}

hr {
  border: 1px solid var(--appblue);
}

.section-header {
  margin: 30px 0 0 0;
}

.login-data {
  font-size: 22px;
  display: flex;
  justify-content: flex-start;
}

.security-data {
  width: 60%;
  font-size: 22px;
  display: grid;
  margin: auto;
}

.user-devices {
  background: var(--navMenuColor);
  width: 100%;
  display: flex;
  justify-content: center;
  font-size: 22px;
}

.column {
  background: var(--navMenuColor);
  width: 100%;
  margin: 0;
  padding: 10px;
}

.row {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.edit-field{
  width: 400px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.editable-inputs {
  height: 22px;
}


.changable {
  color: var(--changableElements);
}

.edit-btn {
  text-decoration: underline;
  cursor: pointer;
}

.save-pass {
  width: 130px;
  text-align: center;
  background: var(--appblue);
  margin: 20px auto;
  cursor: pointer;
  color: var(--whiteText);
  border-radius: 10px;
}

table {
}

th, td {
  padding: 20px;
}

.delete-btn {
  display: flex;
  justify-content: center;
  margin: 50px;
}

.danger-btn {
  font-family: 'Teko', sans-serif;
  font-size: 22px;
  width: 200px;
  height: 80px;
  background: red;
  color: var(--whiteText);
  border: none;
  border-radius: 10px;
  cursor: pointer;
}

@media screen and (max-width: 1500px){
  .account {
    width: 70%;
  }

}

@media screen and (max-width: 1000px){
  .account{
    width: 90%;
  }
}

</style>