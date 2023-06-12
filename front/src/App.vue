<template>
  <div class="app-container">
    <Navigation :actual-panel="actualPanel" @changePanel="getPanel($event)"/>
    <router-view  @changePanel="getPanel($event)" />
  </div>
</template>

<script>
import Navigation from "@/components/Navigation.vue";

export default {
  name: 'App',

  data(){
    return {
      actualPanel: "login",
    }
  },


  components: {
    Navigation,
  },

  methods: {
    getPanel(panel){
      this.actualPanel = panel;
    },

  },

}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Teko:wght@500&display=swap');
@import url('https://fonts.googleapis.com/css2?family=PT+Sans:wght@700&display=swap');
@import url('https://db.onlinewebfonts.com/c/84cb021d5f9af287ffff84b61beef6dc?family=clockicons');

:root {
  --appblue: rgb(59, 100, 197);
  --whiteText: #ffffff;
  --navMenuColor: #d9e7fc;
  --changableElements: #1c232e;
  --footerColor: #e8e8e8;
  --halfView: gray;
}

html, body {
  height: 100%;
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
  background: var(--navMenuColor);
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

footer {
  width: 100%;
  height: 70px;
  margin-top: 50px;
  position: sticky;
  bottom: 0;
  background-color: var(--navMenuColor);
  display: grid;
  justify-content: center;
  font-family: 'Teko', sans-serif;
  color: var(--appblue);
  font-size: 14px;
  align-items: end;
  row-gap: 0;
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
