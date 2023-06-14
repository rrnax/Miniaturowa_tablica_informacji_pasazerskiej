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
           <a v-if="nameEditon" class="edit-btn" @click="this.updateUsername">Zapisz</a>
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
      <p  v-if="validationEmail" class="validation-warning">Nie poprawny email!</p>
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
        <p v-if="validationPassword" class="validation-warning" id="pass-info">Za krótkie hasło, przynajmniej 8 znaków!</p>
    </div>
    <div class="section-title">
      <h1 class="section-header">Zarządzanie kontem</h1>
      <hr>
    </div>
    <div class="delete-btn">
      <button @click="deleteAccount" class="danger-btn">Usuń konto</button>
    </div>
  </div>
  <Footer/>
</template>

<script>
import {useUserStore} from "@/store/user.stroe";
import Footer from "@/components/Footer.vue";

export default {
    name: "AccountView",

    components: {
      Footer,
    },

    data() {
        return {
            emailEditon: false,
            nameEditon: false,
            emailInput: "",
            nameInput: "",
            oldPassword: "",
            newPassword: "",
            validationPassword: false,
            validationEmail: false,
        };
    },
    setup() {
        const userStore = useUserStore();
        return { userStore };
    },
    methods: {
        makeInputEmail() {
            this.emailEditon = !this.emailEditon;
            this.emailInput = this.userStore.getEmail;
        },
        makeInputName() {
            this.nameEditon = !this.nameEditon;
            this.nameInput = this.userStore.getName;
        },
        async updateUsername() {
            await this.userStore.saveNewUserName(this.nameInput);
            this.nameEditon = false;
        },
        async updateEmail() {
            if (this.emailInput.includes("@")) {
                await this.userStore.saveNewEmail(this.emailInput);
                this.emailEditon = false;
                this.validationEmail = false;
            }
            else {
                this.validationEmail = true;
            }
        },
        async updatePassword() {
            if (this.oldPassword === this.userStore.getPassword) {
                if (this.newPassword.length > 8) {
                    await this.userStore.saveNewPassword(this.newPassword);
                    alert("Udało się");
                    this.validationPassword = false;
                }
                else {
                    this.validationPassword = true;
                }
            }
            else {
                alert("Podano nieprawidłowe hasło");
            }
        },
        async deleteAccount() {
            if (confirm("Na pewno chcesz usunąć konto?") === true) {
                await this.userStore.deleteUser();
                this.userStore.$reset();
                this.$router.push("/login");
            }
        },
    },
}
</script>

<style scoped>

.account {
  width: 60%;
  font-family: 'Teko', sans-serif;
  color: var(--appblue);
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
  background: linear-gradient( var(--themeMenu), var(--navMenuColor));
  width: 100%;
  display: flex;
  justify-content: center;
  font-size: 22px;
  border-radius: 20px;
}

.column {
  background: linear-gradient( var(--themeMenu), var(--navMenuColor));
  width: 100%;
  display: block;
  margin: 0;
  padding: 10px;
  border-radius: 20px;
}

.row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 40px;
}

.edit-field{
  width: 400px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.editable-inputs {
    height: 22px;
    width: 200px;
    margin: 0;
    padding: 10px;
}

.device-table {
  width: 100%;
  margin: 40px auto;
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

.validation-warning {
    color: red;
    width: 100%;
    text-align: center;
    font-size: 22px;
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

@media screen and (max-width: 600px){
    .edit-field{
        width: 65%;
        display: block;
    }

    .editable-inputs{
     width: 100%;
     margin: auto;
    }

    #old-password{
        width: 100%;
    }

    #new-password{
        width: 100%;
    }
}

</style>