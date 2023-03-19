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
    <button type="submit" class="submited-btn">Zarejestruj</button>
  </form>
</template>

<script>
export default {
  name: "RegistrationForm"
}
</script>

<style scoped>
.data-register{
  margin: 20px;
}
</style>