<template>
  <nav>
    <router-link class="nav-title" to="/display">
      <img id="logo" src="../assets/m_tip_logo.png">
      <div>
        <p class="title">Miniaturowa Tablica</p>
        <p class="title">Informacji Pasażerskiej</p>
      </div>
    </router-link>
    <div class="options">
      <router-link class="option-btn" v-if="!this.userStore.authStatus" to="/login">Zloguj się</router-link>
      <router-link class="option-btn" v-if="!this.userStore.authStatus" to="/registration">Zarejestruj Się</router-link>
      <router-link class="menu-btn" v-if="this.userStore.authStatus" to="/device">Urządzenie</router-link>
      <router-link class="menu-btn" v-if="this.userStore.authStatus" to="/">Przystanki</router-link>
      <router-link class="menu-btn" v-if="this.userStore.authStatus" to="/account">Konto</router-link>
      <a class="menu-btn" v-if="this.userStore.authStatus" @click="logOut">Wyloguj się</a>
    </div>
    <div @click="toggleMenu"
         id="toggle-menu"
         :style="{ background: active ? 'var(--appblue)' : 'var(--navMenuColor)',
                   color: active ? 'var(--whiteText)' : 'var(--appblue)',
    }">
      <div class="icon">
        <div class="bar1"></div>
        <div class="bar2"></div>
        <div class="bar3"></div>
      </div>
    </div>
  </nav>
  <div class="menu-toggle"
    :style="{ display: active ? 'flex' : 'none' }">
    <div class="menu-items">
      <router-link class="menu-item" v-if="!this.userStore.authStatus" to="/login">Zloguj się</router-link>
      <router-link class="menu-item" v-if="!this.userStore.authStatus" to="/registration">Zarejestruj Się</router-link>
      <router-link class="menu-item" v-if="this.userStore.authStatus" to="/device">Urządzenie</router-link>
      <router-link class="menu-item" v-if="this.userStore.authStatus" to="/">Przystnaki</router-link>
      <router-link class="menu-item" v-if="this.userStore.authStatus" to="/account">Konto</router-link>
      <a class="menu-item" v-if="this.userStore.authStatus" @click="logOut">Wyloguj się</a>
    </div>
  </div>
</template>

<script>
import {useAuthStore} from "@/store/auth.store";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'Navigation',

  data() {
    return {
      active: false,
    }
  },

  setup(){      //declaring store we used to correct nav bar for loged or not
    const userStore = useAuthStore();
    return { userStore };
  },

  methods: {       //loging out with reset state
    async logOut(){
      localStorage.removeItem("auth");
      await this.userStore.userSignOut();
      if(this.userStore.getHttpCode === 200){
        this.userStore.sessionIntervalStop();
        this.userStore.$reset();
        this.$router.push('/login');
        alert("Wylogowano pomyślnie!");
      }else{
        alert("Brakl połączenia z serwerem, nie można było wylogować!");
      }
    },

    toggleMenu(){
        this.active = !this.active;
        let makeX = document.querySelector(".icon");
        makeX.classList.toggle("change");
    }
  },
}
</script>

<style scoped>

nav {
  background: var(--navMenuColor);
  padding: 0px 20%;
  height: 80px;
  z-index: 2;
  position: sticky;
  top: 0px;
  display: flex;
  justify-content: space-between;
  border-bottom: 3px solid var(--appblue);
}

.menu-toggle {
  background: var(--appblue);
  position: sticky;
  z-index: 3;
  top: 77px;
  border-bottom: 3px solid var(--appblue);
}

.menu-items {
  display: grid;
  width: 100%;
}

.menu-item {
  width: 100%;
  height: 70px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 22px;
  text-decoration: none;
  font-family: 'Teko', sans-serif;
  color: var(--whiteText);
}

.menu-btn {
  width: 100px;
  height: 100%;
  margin: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  text-decoration: none;
  color: var(--appblue);
  font-family: 'Teko', sans-serif;
  font-size: 22px;
  cursor: pointer;
}

.nav-title {
  display: flex;
  align-items: center;
  text-decoration: none;
}

#logo {
  width: 70px;
  height: 70px;
}

.title {
  font-family: 'Bebas Neue', cursive;
  color: var(--appblue);
  font-size: 22px;
  margin: 0;
}

#toggle-menu {
  display: none;
  width: 80px;
  align-items: center;
  justify-content: center;
  color: var(--appblue);
  font-family: 'Teko', sans-serif;
  font-size: 22px;
  cursor: pointer;
}

.options {
  column-gap: 5px;
  width: 40%;
  display: flex;
  align-items: center;
  justify-content: flex-end;
}


.menu-item:hover {
  background: var(--whiteText);
  color: var(--appblue);
}

#toggle-menu:hover {
  background: var(--appblue);
  color: var(--whiteText);
}

.menu-btn:hover {
  background: var(--appblue);
  color: var(--whiteText);
}

.bar1, .bar2, .bar3 {
  width: 35px;
  height: 5px;
  background-color: var(--appblue);
  margin: 6px 0;
  transition: 0.4s;
}

.change .bar1 {
  background-color: var(--whiteText);
  transform: translate(0, 11px) rotate(-45deg);
}

.change .bar2 {opacity: 0;}

.change .bar3 {
  background-color: var(--whiteText);
  transform: translate(0, -11px) rotate(45deg);
}

@media screen and (max-width: 1500px){
    nav {
      padding: 0 15%;
    }

}

@media screen and (max-width: 1000px) {
    nav {
      padding: 0 5%;
    }
}

@media  screen and (max-width: 700px){
  nav {
    height: 100px;
  }

  .options {
    display: none;
  }

  #toggle-menu {
    display: flex;
  }

}

</style>