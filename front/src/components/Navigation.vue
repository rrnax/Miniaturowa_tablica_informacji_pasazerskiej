<template>
  <nav>
    <h1 class="nav-title">Home</h1>
    <div class="options">
      <router-link to="/login">Zaloguj się</router-link>
    </div>
  </nav>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Navigation',
}
</script>

<style scoped>

nav {
  padding: 10px 10%;
  z-index: 2;
  position: sticky;
  overflow: hidden;
  display: flex;
  justify-content: space-between;
  border-bottom: 2px solid black;
}

.nav-title {
  margin: 0;
}

</style>