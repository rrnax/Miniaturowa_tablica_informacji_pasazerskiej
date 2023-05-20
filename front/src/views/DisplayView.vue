<template>
    <div class="display-view">
      <div class="general-info">
        <div class="theme" id="station-name">
          <h1 class="display-info" id="station">{{ this.apiStore.getActiveStop.stopName }}</h1>
        </div>
        <div class="theme" id="date">
            <h1 class="display-info">{{ publishDate }}</h1>
        </div>
      </div>
      <tr class="table-header">
          <th v-if="this.apiStore.getActiveStop.cityName === 'Kolej'">Peron</th>
          <th v-else>Linia</th>
          <th>Kierunek</th>
          <th>Godzina</th>
        </tr>
      <div v-if="this.apiStore.getLoadedInfo" class="loader"></div>
      <table class="departure-table">
        <tr v-for="route in this.apiStore.getDepartures" class="theme t-row" v-bind:key="route.id">
          <td v-if="this.apiStore.getActiveStop.cityName === 'Gdańsk [ZTM]'" class="line">{{ route.routeId }}</td>
          <td v-else class="line">{{ route.tripId }}</td>
          <td class="destination">
            <marquee v-if="route.headsign.length > 9">{{ route.headsign }}</marquee>
            <p v-else>{{ route.headsign }}</p>
          </td>
          <td class="time">{{ route.estimatedTime }}</td>
        </tr>
      </table>
    </div>
  </template>
  
  <script>
  import { useApiStore } from '@/store/apiManagment.store';
import { useUserStore } from '@/store/user.stroe';

  export default {
    name: "DisplayView",

    setup(){
      const apiStore = useApiStore();
      const userStore = useUserStore();
      return { apiStore, userStore };
    },

    data(){
      return {
        style: "",
        departureList: [],
        date: "",
      }
    },

    created(){
      setInterval(this.actualDate, 1000);
      setInterval(this.updateDepartureList, 60000);
    },

    mounted(){
      this.userStore.downloadFavoriteStops();
      setTimeout(this.apiStore.converActiveStop, 200);
      setTimeout(this.apiStore.updateDepartureList, 500);

      switch(this.apiStore.getDeviceStyle){
        case 'retro':
          document.documentElement.style.setProperty('--backcolor', '#070606');
          document.documentElement.style.setProperty('--fontcolor', '#ff6701');
          document.documentElement.style.setProperty('--containercolor',"#3f3f3f");
          document.documentElement.style.setProperty('--bordercolor',"#ffffff");
          break;
        case 'basic':
          document.documentElement.style.setProperty('--backcolor', '#3b4bfd');
          document.documentElement.style.setProperty('--fontcolor', '#ffffff');
          document.documentElement.style.setProperty('--containercolor',"#3b4bfd");
          document.documentElement.style.setProperty('--bordercolor',"#ffffff");
          break;
        case 'contrast':
          document.documentElement.style.setProperty('--backcolor', '#000000');
          document.documentElement.style.setProperty('--fontcolor', '#6ef500');
          document.documentElement.style.setProperty('--containercolor',"#000000");
          document.documentElement.style.setProperty('--bordercolor',"#ffffff");
          break;
        case 'dracula':
          document.documentElement.style.setProperty('--backcolor', '#242424');
          document.documentElement.style.setProperty('--fontcolor', '#ebebeb');
          document.documentElement.style.setProperty('--containercolor',"#4e4949");
          document.documentElement.style.setProperty('--bordercolor',"#242424");
          break;
        default:
          break;
      }
    },

    methods: {
      actualDate(){
        let miliDate = new Date();
        this.date = miliDate.toLocaleTimeString();
      },
    },

    computed: {
      publishDate(){
        return this.date;
      },

    }
    
  }
</script>

<style>
:root {
  --backcolor: #4e4949;
  --fontcolor: #ff7519;
  --containercolor: #363232;
  --bordercolor: #ebebeb;
}

.display-view {
  width: 480px;
  height: 320px;
  margin: 50px auto;
  margin-bottom: 260px;
  display: grid;
  border-radius: 10px;
  background: var(--backcolor);
  font-family: 'PT Sans', sans-serif;
  color: var(--fontcolor);
  white-space: nowrap;
}

.general-info {
  width: 100%;
  margin: 0;
  padding: 0;
  display: flex;
  justify-content: center;
}

.theme {
  margin: 5px;
  padding: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  background: var(--containercolor);
  border: 2px solid var(--bordercolor);
  border-radius: 10px;
}

.table-header {
  width: 90%;
  margin: auto;
  display: flex;
  justify-content: space-between;
  font-size: 22px;
}

.departure-table{
  width: 100%;
  display: grid;
  overflow: scroll;
}

.t-row {
  width: 95%;
  max-width: 480px;
  height: 80px;
  margin: 10px auto;
  display: flex;
  justify-content: space-between;
  font-size: 40px;
  text-transform: uppercase;
  color: var(--fontcolor);
  overflow: hidden;
}

.line {
  width: 15%;
  margin-left: 10px;
  color: var(--fontcolor);
}

.destination {
  width: 60%;
  max-width: 60%;
  margin: 0 20px;
  color: var(--fontcolor);
  overflow: hidden;
  white-space: nowrap;
}

.time {
  width: 20%;
  margin-right: 15px;
  color: var(--fontcolor);
}

#station-name {
  width: 60%;
  max-width: 280px;
  height: 70px;
  overflow: hidden;
}

#date {
  width: 35%;
  height: 70px;
}

.display-info {
  margin: 0;
  padding: 0;
  overflow: hidden;
  font-size: 40px;
  text-transform: uppercase;
}

@media only screen and (max-width: 500px) {
  .display-view {
    width: 98%;
    margin: auto;
  }
  
}



</style>