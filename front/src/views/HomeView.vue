<template>
  <div class="stops-view">
    <h1>Wyszukaj Stacje</h1>
    <hr>
    <div class="action-section">
      <Searcher :is-combo-box-fill="isComboBoxFill" @changeStopsList="changeStopsList($event)"/>
    </div>
    <h1 v-if="isComboBoxFill">Stacje</h1>
    <hr v-if="isComboBoxFill">
    <div v-if="isComboBoxFill" class="action-section">
      <StopsLister/>
    </div>
    <h1>Mapa</h1>
    <hr>
    <div class="maps-section">
      <MapSection/>
    </div>
  </div>

</template>

<script>
import MapSection from '@/components/homeComponents/MapSection.vue';
import Searcher from '@/components/homeComponents/Searcher.vue';
import StopsLister from '@/components/homeComponents/StopsLister.vue';
import { useApiStore } from '@/store/apiManagment.store';
import { useUserStore } from '@/store/user.stroe';

export default {
  name: 'HomeView',

  components: {
    Searcher,
    StopsLister,
    MapSection
  },

  data(){
    return{
      isComboBoxFill: false,
    }
  },

  setup(){
    const apiStore = useApiStore();
    const userStore = useUserStore();
    return { apiStore, userStore };
  },

  //Download favorite stops to know wich stops are subsribed
  async created(){
    await this.userStore.downloadStyle();
    this.apiStore.$reset();
    await this.userStore.downloadFavoriteStops();
    let tempList = this.userStore.getFavorites;
    tempList.map(stop => {
      if(stop.status === true){
        this.apiStore.setActiveStop(stop);
      }
    })
  },

  //Change display of stops list box
  methods: {
    changeStopsList(event){
      this.isComboBoxFill = event;
    },
    
  },
}
</script>

<style>
.stops-view {
  width: 60%;
  margin: 30px auto 100px auto;
  display: block;
  font-family: 'Teko', sans-serif;
  font-size: 22px;
  color: var(--appblue);
}

.action-section {
  width: 100%;
  background-color: var(--navMenuColor);
  border-radius: 20px;
}



@media only screen and ( max-width: 600px) {
  .stops-view {
    width: 95%;
  
  }
}

</style>
