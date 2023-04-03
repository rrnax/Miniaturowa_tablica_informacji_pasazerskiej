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
.panel {
  width: 40%;
  margin: 20px auto;
  display: block;
  text-align: center;
  border: 2px solid black;
}

.division {
  width: 90%;
}

.submited-btn{
  margin: 20px;
}
</style>
