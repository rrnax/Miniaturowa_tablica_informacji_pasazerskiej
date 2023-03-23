<template>
  <div class="stops">
    <h1>Wybierz przystanek: </h1>
    <select name="lister_stops" id="stops-list">
      <option v-for="(p) in stops" :key="p.name">{{p.name}}</option>
    </select>
<!--    <button @click="userStore.downloadAllUsers()">Wyswietl</button>-->
<!--    <p v-for="user in userStore.getAllUsers" :key="user.id">{{user}}</p>-->
  </div>
</template>

<script>
import {useAuthStore} from "@/store/auth.store";

export default {
  name: 'HomeView',

  setup(){
    const userStore = useAuthStore();
    return { userStore };
  },

  data() {
    return {
      stops: [],
    }
  },

  created(){
    let stop = {
      'name': 'Dworzec pkp',
    };
    this.stops.push(stop);
  }
}
</script>

<style>
.stops{
  margin: auto;
  display: flex;
  justify-content: center;
}

</style>
