<template>
  <Navigation />
  <router-view/>
</template>

<script>
import Navigation from "@/components/Navigation.vue";
export default {
  name: 'App',

  components: {
    Navigation,
  }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Bebas+Neue&family=Teko:wght@500&display=swap');

:root {
  --appblue: #3b64c5;
  --whiteText: #ffffff;
  --navMenuColor: #d9e7fc;
  --changableElements: #1c232e;
}

.panel {
  background: var(--navMenuColor);
  width: 360px;
  margin: 20px auto;
  display: block;
  color: var(--appblue);
  text-align: center;
  font-family: 'Teko', sans-serif;
  border: 2px solid var(--appblue);
  border-radius: 30px;
}

.option-btn {
  background: var(--appblue);
  margin: 20px auto;
  width: 130px;
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: 'Teko', sans-serif;
  color: var(--whiteText);
  text-decoration: none;
  font-size: 22px;
  border: 1px solid var(--appblue);
  border-radius: 20px;
}

.option-btn:hover {
  background: var(--whiteText);
  color: var(--appblue);
}

.division {
  width: 90%;
  border: 1px solid var(--appblue);
}

input {
  padding: 10px;
  font-size: 17px;
  border-radius: 10px;
  border: 2px solid var(--appblue);
}

</style>
