<template>
  <nav class="app-navigation">
    <div class="nav-title">
      <img id="logo" src="../assets/m_tip_logo.png">
      <div>
        <p class="app-name">Miniaturowa Tablica</p>
        <p class="app-name">Informacji Pasażerskiej</p>
      </div>
    </div>
    <div class="options">
      <router-link class="option-btn" @click="singalPanel('login')" v-if="!this.userStore.authStatus && actualPanel === 'regis'" to="/login">Zloguj się</router-link>
      <router-link class="option-btn" @click="singalPanel('regis')" v-if="!this.userStore.authStatus && actualPanel === 'login'" to="/registration">Zarejestruj Się</router-link>
      <router-link class="menu-btn" v-if="this.userStore.authStatus" to="/ranks">Pociągi</router-link>
      <router-link class="menu-btn" v-if="this.userStore.authStatus" to="/device">Urządzenie</router-link>
      <router-link class="menu-btn" v-if="this.userStore.authStatus" to="/">Przystanki</router-link>
      <router-link class="menu-btn" v-if="this.userStore.authStatus" to="/account">Konto</router-link>
      <a class="menu-btn" @click="logOut" v-if="this.userStore.authStatus">Wyloguj się</a>
    </div>
    <div @click="toggleMenu"
         id="toggle-menu"
         :style="{ background: active ? 'var(--appblue)' : 'var(--navMenuColor)',
          color: active ? 'var(--whiteText)' : 'var(--appblue)',}">
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
      <router-link class="menu-item" @click="singalPanel('login')" v-if="!this.userStore.authStatus && actualPanel === 'regis'" to="/login">Zloguj się</router-link>
      <router-link class="menu-item" @click="singalPanel('regis')" v-if="!this.userStore.authStatus && actualPanel === 'login'" to="/registration">Zarejestruj Się</router-link>
      <router-link class="menu-item" v-if="this.userStore.authStatus" to="/ranks">Pociągi</router-link>
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

  props: {
    actualPanel: String,
  },

  emits:["changePanel"],

  setup(){  
    const userStore = useAuthStore();
    return { userStore };
  },

  methods: {       
    //loging out with reset state
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

    //Transforming button for nav menu
    toggleMenu(){
        this.active = !this.active;
        let makeX = document.querySelector(".icon");
        makeX.classList.toggle("change");
    },

    //Changing displays options on nav bar
    singalPanel(panel){
      this.$emit('changePanel', panel);
    }
  },
}
</script>

<style scoped>

.app-navigation {
  height: 80px;
  top: 0px;
  position: sticky;
  z-index: 2;
  padding: 0px 20%;
  display: flex;
  justify-content: space-between;
  background: var(--navMenuColor);
  border-bottom: 3px solid var(--appblue);
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

.app-name {
  margin: 0;
  font-family: 'Bebas Neue', cursive;
  font-size: 22px;
  color: var(--appblue);
}

.options {
  width: 70%;
  display: flex;
  align-items: center;
  justify-content: flex-end;
}

.menu-btn {
  height: 100%;
  margin: 0;
  padding: 0 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: 'Teko', sans-serif;
  font-size: 22px;
  text-decoration: none;
  color: var(--appblue);
  cursor: pointer;
}

.menu-btn:hover {
  background: var(--appblue);
  color: var(--whiteText);
}

#toggle-menu {
  width: 80px;
  display: none;
  align-items: center;
  justify-content: center;
  font-family: 'Teko', sans-serif;
  font-size: 22px;
  color: var(--appblue);
  cursor: pointer;
}

#toggle-menu:hover {
  background: var(--appblue);
  color: var(--whiteText);
}

.bar1, .bar2, .bar3 {
  width: 35px;
  height: 5px;
  margin: 6px 0;
  background-color: var(--appblue);
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

.menu-toggle {
  top: 77px;
  position: sticky;
  z-index: 3;
  background: var(--appblue);  
  border-bottom: 3px solid var(--appblue);
}

.menu-items {
  width: 100%;
  display: block;
}

.menu-item {
  width: 100%;
  height: 70px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: 'Teko', sans-serif;
  font-size: 22px;
  text-decoration: none;
  color: var(--whiteText);
}


.menu-item:hover {
  background: var(--whiteText);
  color: var(--appblue);
}

@media screen and (max-width: 1500px){
    nav {
      padding: 0 10%;
    }

}


@media  screen and (max-width: 1000px){
  .app-navigation {
    margin: 0;
  }

  .options {
    display: none;
  }

  #toggle-menu {
    display: flex;
  }

}

@media  screen and (max-width: 600px){
  .app-navigation {
    margin: 0;
    padding: 0;
  }

}

</style>