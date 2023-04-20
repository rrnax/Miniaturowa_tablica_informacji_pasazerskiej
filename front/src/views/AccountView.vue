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
           <p class="changable">rrnax</p>
         </div>
        <a class="edit-btn">Edytuj</a>
       </li>
       <li class="row">
         <div class="edit-field">
          <p>Email</p>
          <p class="changable">wp@wp.pl</p>
         </div>
         <a class="edit-btn">Edytuj</a>
       </li>
     </ul>
    </div>
    <div class="section-title">
      <h1 class="section-header">Bezpieczeństwo</h1>
      <hr>
    </div>
    <div class="security-data">
      <label for="old-password">Stare hasło</label>
      <input id="old-password"/>
      <label for="new-password">Nowe hasło</label>
      <input id="new-password"/>
      <a class="save-pass">Zmień hasło</a>
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
      <button class="danger-btn">Usuń konto</button>
    </div>
  </div>
</template>

<script>
import {useAuthStore} from "@/store/auth.store";

export default {
  name: "AccountView",


  setup(){
    const userStore = useAuthStore();
    return userStore;
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
  width: 300px;
  display: flex;
  justify-content: space-between;
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