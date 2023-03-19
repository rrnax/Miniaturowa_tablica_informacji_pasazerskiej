<template>
  <div class="stops">
    <h1>Wybierz przystanek: </h1>
    <select name="lister_stops" id="stops-list">
      <option v-for="(p) in stops" :key="p.name">{{p.name}}</option>
    </select>
  </div>
</template>

<script>

export default {
  name: 'HomeView',
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
