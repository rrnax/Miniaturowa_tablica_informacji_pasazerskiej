<template>
  <form>
    <div class="data-input">
      <label for="email">E-mail</label>
      <input type="text" id="email" placeholder="Podaj email"/>
    </div>
    <div class="data-input">
      <label for="password">Hasło</label>
      <input type="password" id="password" placeholder="Podaj hasło"/>
    </div>
    <button type="submit" class="submited-btn">Zaloguj</button>
  </form>
</template>

<script>
export default {
  name: "LoginForm"
}
</script>

<style scoped>

.data-input {
  margin: 10px;
}

</style>