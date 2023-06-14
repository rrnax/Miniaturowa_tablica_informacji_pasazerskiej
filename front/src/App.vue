<template>
  <div class="app-container">
    <Navigation :actual-panel="actualPanel" :dark-mode="darkMode" @changePanel="getPanel($event)" @changeMode="setMode"/>
    <router-view :dark-mode="darkMode"  @changePanel="getPanel($event)" />
  </div>
</template>

<script>
import Navigation from "@/components/Navigation.vue";

export default {
  name: 'App',

  data(){
    return {
      actualPanel: "login",
      darkMode: false,
    }
  },


  components: {
    Navigation,
  },

  methods: {
    getPanel(panel){
      this.actualPanel = panel;
    },

    setMode(){
      this.darkMode = !this.darkMode;
    }

  },

}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Teko:wght@500&display=swap');
@import url('https://fonts.googleapis.com/css2?family=PT+Sans:wght@700&display=swap');
@font-face {
    font-family:clockicons;
    src:url(./assets/font/clockicons/clockicons.eot#iefix) format('embedded-opentype'),
    url(./assets/font/clockicons/clockicons.woff2) format('woff2'),
    url(./assets/font/clockicons/clockicons.woff) format('woff'),
    url(./assets/font/clockicons/clockicons.ttf) format('truetype');
    font-weight:400;
    font-style:normal
}

:root {
  --appblue: #2e5bc5;
  --whiteText: #ffffff;
  --themeMenu:  #d6ebff;
  --navMenuColor: #88b8ff;
  --changableElements: #1c232e;
  --halfView: gray;
  --firstbck: #dfdddd;
  --secondbck: #ffffff;
  --linked: #ae00ff;

}

html, body {
  height: 100%;
  background: linear-gradient(90deg,var(--firstbck), var(--secondbck));
}

#app {
  display: flex;
  flex-direction: column;
  margin: 0;
}

.app-container {
  width: 100%;
  min-height: 100vh;
  margin: 0 0 0 0;
}

h1 {
  margin: 20px 0 0 0 ;
}

hr {
  width: 100%;
  border: 2px solid var(--appblue);
}

.option-btn {
  width: 130px;
  height: 40px;
  margin: 20px auto;
  display: flex;
  justify-content: center;
  align-items: center;
  background: var(--appblue);
  border: 1px solid var(--appblue);
  border-radius: 20px;
  font-family: 'Teko', sans-serif;
  font-size: 22px;
  color: var(--whiteText);
  text-decoration: none;
  cursor: pointer;
}

.option-btn:hover {
  background: var(--whiteText);
  color: var(--appblue);
}

.negative {
  color: red;
  margin: auto;
}

.positive {
  color: green;
}

.loader {
  width: 20px;
  height: 20px;
  margin: 40px auto 0 auto;
  border: 5px solid #f3f3f3;
  border-radius: 50%;
  border-top: 5px solid var(--appblue);
  border-bottom: 5px solid var(--appblue);
  -webkit-animation: spin 2s linear infinite;
  animation: spin 2s linear infinite;
}

.panel {
  background: linear-gradient( var(--themeMenu), var(--navMenuColor));
  width: 360px;
  margin: 20px auto 270px auto;
  display: block;
  color: var(--appblue);
  text-align: center;
  font-family: 'Teko', sans-serif;
  border: 2px solid var(--appblue);
  border-radius: 30px;
}

.division {
  width: 90%;
  border: 1px solid var(--appblue);
}

input {
  padding: 10px;
  margin: 0;
  font-size: 17px;
  border-radius: 10px;
  border: 2px solid var(--appblue);
}

p {
  margin: 0;
  text-align: center;
}

.warning-message {
  width: 100%;
  margin-top: 20px;
  display: flex;
  justify-content: center;
  font-size: 22px;
  color: red;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}
</style>
