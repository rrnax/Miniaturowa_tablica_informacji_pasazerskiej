<template>
  <div class="home-block">
    <h1>Wyszukaj Stacje</h1>
    <hr>
    <div class="content-block">
      <Searcher @changeStopsList="changeStopsList()"/>
    </div>
    <h1 v-if="isComboBoxFill">Stacje</h1>
    <hr v-if="isComboBoxFill">
    <div v-if="isComboBoxFill" class="content-block">
      <StopsLister/>
    </div>
  </div>

</template>

<script>
import Searcher from '@/components/homeComponents/Searcher.vue';
import StopsLister from '@/components/homeComponents/StopsLister.vue';
import { useApiStore } from '@/store/apiManagment.store';

export default {
  name: 'HomeView',

  components: {
    Searcher,
    StopsLister
  },

  data(){
    return{
      isComboBoxFill: false,
    }
  },

  setup(){
    const apiStore = useApiStore();
    return { apiStore };
  },

  methods: {

    changeStopsList(){
      this.isComboBoxFill = true;
    }
  },
}
</script>

<style>
.home-block {
  font-family: 'Teko', sans-serif;
  color: var(--appblue);
  width: 60%;
  display: grid;
  font-size: 22px;
  margin: 20px auto 100px auto;
}

h1 {
  margin: 30px 0 0 0;
}

hr {
  width: 100%;
  border: 2px solid var(--appblue);
}

.content-block {
  width: 100%;
  background-color: var(--navMenuColor);
  border-radius: 20px;
}


@media only screen and ( max-width: 600px) {
  .home-block {
    width: 95%;
  
  }
}

</style>
