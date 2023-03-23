<template>
  <nav>
    <h1 class="nav-title">
      <router-link to="/">Home</router-link>
    </h1>
    <div class="options">
      <router-link v-if="!this.userStore.authStatus" to="/login">Zaloguj się</router-link>
      <a v-if="this.userStore.authStatus" @click="logOut">Wyloguj się</a>
    </div>
  </nav>
</template>

<script>
import {useAuthStore} from "@/store/auth.store";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Navigation',

  setup(){      //declaring store we used to correct nav bar for loged or not
    const userStore = useAuthStore();
    return { userStore };
  },

  methods: {       //loging out with reset state
    async logOut(){
      await this.userStore.userSignOut();
      this.userStore.$reset();
      this.$router.push('/');
    },
  },
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