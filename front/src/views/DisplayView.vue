<template>
    <div class="display-view">
      <div class="general-info">
        <div class="theme" id="station-name">
          <h1 class="display-info" id="station">{{ publishRoutes }}</h1>
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
      <table class="departure-table">
        <tr v-for="route in publishDisplays()" class="theme t-row" v-bind:key="route.id">
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
      setInterval(this.updateData, 60000);
    },

    mounted(){
      this.updateData();
      let station = document.querySelector("#station");
      if(station.offsetWidth < station.scrollWidth){
          let marquee = document.createElement('marquee');
          let content = station.innerText;
          marquee.innerText = content;
          station.innerHTML = "";
          station.appendChild(marquee);
      }

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

      updateData(){
        this.updateActiveStop();
        this.urlCreator();
        this.createDepartureList();
      },

      async createDepartureList(){
        let tempStop = {};
        let resultList = [];
        tempStop = JSON.parse(JSON.stringify(this.apiStore.getActiveStop));
          tempStop.stopIds.forEach(async (code) => {
            await this.apiStore.downloadDeparturesByDisplayCode(code);
            let tempDepartures = {};
            tempDepartures = JSON.parse(JSON.stringify(this.apiStore.getTempDeparture));
            if(tempDepartures !== "Brak Odjazdow"){
              tempDepartures = tempDepartures.departures;
              tempDepartures.map((departure) => {
                resultList.push(departure);
              })
            }
         })   
        this.apiStore.setDepartureList(resultList);
      },

      async urlCreator(){
        let tempStop = {};
        tempStop = JSON.parse(JSON.stringify(this.apiStore.getActiveStop));
        if(tempStop.cityName === "Kolej"){
            this.apiStore.useDepartureApiPkp();
        } else if (tempStop.cityName === "Gdańsk [ZTM]"){
            this.apiStore.useDepartureApiZtm("gdansk");
        } else if (tempStop.cityName === "Warszawa [ZTM]"){
            this.apiStore.useDepartureApiZtm("warszawa");
        }
      },
      
      async updateActiveStop(){
        let resultList = [];
        await this.userStore.downloadFavoriteStops();
        resultList = JSON.parse(JSON.stringify(this.userStore.getFavorites));
        resultList.forEach(stop => {
          if(stop.status === true){
            this.apiStore.setActiveStop(stop);
          }
        })
      },

      publishDisplays(){
        let resultList = [];
        resultList = JSON.parse(JSON.stringify(this.apiStore.getDepartures));
        if(this.apiStore.getActiveStop.cityName === "Gdańsk [ZTM]"){
          resultList.sort(this.compareTimes);
          resultList.map((departure) => {
            let temp = new Date(departure.estimatedTime); 
            departure.estimatedTime = temp.getHours() + ":" + (temp.getMinutes()<10?'0':'') + temp.getMinutes();
          })
        } else {
          resultList.map((departure) => {
            departure.estimatedTime = departure.estimatedTime.substring(0,5);
          })
        }
        return resultList;
      },

      compareTimes(a,b){
        let date1 = new Date(a.estimatedTime);
        let date2 = new Date(b.estimatedTime);
        if ( date1 < date2 ){
          return -1;
        }
        if ( date1 > date2 ){
          return 1;
        }
        return 0;
      },

      

    },
  
    computed: {
      publishDate(){
        return this.date;
      },

      publishRoutes(){
        let stop = {};
        stop = JSON.parse(JSON.stringify(this.apiStore.getActiveStop));
        return stop.stopName;
      }
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
  display: grid;
  background: var(--backcolor);
  font-family: 'PT Sans', sans-serif;
  color: var(--fontcolor);
  border-radius: 10px;
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



</style>