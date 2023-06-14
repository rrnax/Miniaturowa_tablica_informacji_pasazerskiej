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
      <router-link class="option-btn user-actions" @click="singalPanel('login')" v-if="!this.userStore.authStatus && actualPanel === 'regis'" to="/login">Zaloguj się</router-link>
      <router-link class="option-btn user-actions" @click="singalPanel('regis')" v-if="!this.userStore.authStatus && actualPanel === 'login'" to="/registration">Zarejestruj Się</router-link>
      <router-link class="menu-btn" v-if="this.userStore.authStatus" to="/ranks">Statystyki</router-link>
      <router-link class="menu-btn" v-if="this.userStore.authStatus" to="/device">Urządzenie</router-link>
      <router-link class="menu-btn" v-if="this.userStore.authStatus" to="/">Stacje</router-link>
      <router-link class="menu-btn" v-if="this.userStore.authStatus" to="/account">Konto</router-link>
      <a class="menu-btn" @click="logOut" v-if="this.userStore.authStatus">Wyloguj się</a>
    </div>
    <img v-if="!darkMode" @click="changeTheme" class="moon" src="../assets/moon-icon-light.png" />
    <img v-if="darkMode" @click="changeTheme" class="moon" src="../assets/moon-icon.png" />
    <div @click="toggleMenu"
         id="toggle-menu"
         :style="{ background: active ? 'var(--appblue)' : 'linear-gradient(var(--navMenuColor), var(--themeMenu))',
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
      <router-link class="menu-item" @click="singalPanel('login')" v-if="!this.userStore.authStatus && actualPanel === 'regis'" to="/login">Zaloguj się</router-link>
      <router-link class="menu-item" @click="singalPanel('regis')" v-if="!this.userStore.authStatus && actualPanel === 'login'" to="/registration">Zarejestruj Się</router-link>
      <router-link class="menu-item" v-if="this.userStore.authStatus" to="/ranks">Statystyki</router-link>
      <router-link class="menu-item" v-if="this.userStore.authStatus" to="/device">Urządzenie</router-link>
      <router-link class="menu-item" v-if="this.userStore.authStatus" to="/">Stacje</router-link>
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
    darkMode: Boolean,
  },

  emits:["changePanel", "changeMode"],

  setup(){  
    const userStore = useAuthStore();
    return { userStore };
  },

  mounted(){
    this.setCurrent();
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
    },
    
    setCurrent(){
      const bookmarks = document.querySelectorAll('.menu-btn');
      if (bookmarks.length) {
      bookmarks.forEach((link) => {
        link.addEventListener('click', (e) => {
          bookmarks.forEach((link) => {
              link.classList.remove('current');
          });
          e.preventDefault();
          link.classList.add('current');
        });
      }); 
      }
    },

    //Change theme of Site
    async changeTheme(){
      await this.$emit('changeMode');
      if(this.darkMode){
        document.documentElement.style.setProperty('--appblue', '#f3f3f3');
        document.documentElement.style.setProperty('--firstbck', '#727272');
        document.documentElement.style.setProperty('--secondbck', '#525252');
        document.documentElement.style.setProperty('--themeMenu', '#292929');
        document.documentElement.style.setProperty('--navMenuColor', '#424242');
        document.documentElement.style.setProperty('--changableElements','#ffffff');
        document.documentElement.style.setProperty('--halfView', '#ffa3a3');
        document.documentElement.style.setProperty('--whiteText', '#292929');
        document.documentElement.style.setProperty('--linked', 'black');
      } else {
        document.documentElement.style.setProperty('--appblue', '#2e5bc5');
        document.documentElement.style.setProperty('--secondbck', '#ffffff');
        document.documentElement.style.setProperty('--firstbck', '#dfdddd');
        document.documentElement.style.setProperty('--themeMenu', '#d6ebff');
        document.documentElement.style.setProperty('--navMenuColor', '#88b8ff');
        document.documentElement.style.setProperty('--changableElements','#1c232e');
        document.documentElement.style.setProperty('--halfView', 'gray');
        document.documentElement.style.setProperty('--whiteText', '#ffffff');
        document.documentElement.style.setProperty('--linked', '#99009e');
      }
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
  background: linear-gradient(var(--navMenuColor), var(--themeMenu));
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
  background: linear-gradient(var(--appblue), var(--appblue));
}

.bar1, .bar2, .bar3 {
  width: 35px;
  height: 5px;
  margin: 6px 0;
  background: var(--appblue);
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

.user-actions {
  margin-right: 20px !important;
}


.menu-item:hover {
  background: var(--whiteText);
  color: var(--appblue);
}

.moon {
  width: 20px;
  height: 20px;
  margin-top: 27px;
  margin-left: 10px;
  cursor: pointer;
}

.current {
  background: var(--appblue);
  color: var(--whiteText);
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