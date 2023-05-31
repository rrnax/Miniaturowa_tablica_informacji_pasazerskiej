<template>
    <div class="ranks">
      <h1>Informacje o Pociągach</h1>
      <div class="info" v-if="dataLoaded">
        <p><span class="info-label">Średnia prędkość </span><span class="blue">{{ (trainInfo.averageSpeed * 3.6).toFixed(2) }} km/h</span></p>
        <p><span class="info-label">Przyjazdy w tym miesiącu </span><span class="blue">{{ parseInt(trainInfo.arrivalsInMonth) + parseInt(trainInfo.arrivalsToday) }}</span></p>
        <p><span class="info-label">Przyjazdy dzisiaj </span><span class="blue">{{ trainInfo.arrivalsToday }} / {{ trainInfo.arrivalsMaxToday }}</span></p>
      </div>
      <div v-else class="loader"></div>
      <table class="ranks-table" v-if="dataLoaded">
        <thead class="ranks-thead">
          <tr>
            <th class="th-tr-ranks">Nazwa</th>
            <th class="th-tr-ranks">Status</th>
            <th class="th-tr-ranks distance-column">Dystans</th>
            <th class="th-tr-ranks">Kierunek</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="train in sortedTrains" :key="train.id">
            <td class="th-tr-ranks">{{ train.name }}</td>
            <td :style="getStatusStyle(train.status)">
              <span v-if="train.status === 'W DRODZE'">
                W DRODZE do <span class="black">{{ train.scheduleList[0].StopName }}</span>
              </span>
              <span v-else-if="train.status === 'NA STACJI'">
                NA STACJI <span class="black">{{ train.scheduleList[0].StopName }}</span>
              </span>
              <span v-else>{{ train.status }}</span>
            </td>
            <td class="distance-column th-tr-ranks">{{ train.distanceTraveled }}</td>
            <td class="th-tr-ranks">
              <span v-if="train.scheduleList.length > 0">{{ train.scheduleList[train.scheduleList.length - 1].StopName }}</span>
              <span v-else>{{ train.lastStopSchedule.StopName }}</span>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <Vuefooter/>
  </template>
  
  
  <script>
  import axios from 'axios';
  import Vuefooter from './Vuefooter.vue';

  
  export default {
    name: "RanksView",
    data() {
        return {
            trains: [],
            sortedTrains: [],
            trainInfo: {
                averageSpeed: "",
                arrivalsToday: "",
                arrivalsMaxToday: "",
                arrivalsInMonth: ""
            },
            dataLoaded: false
        };
    },
    mounted() {
        this.fetchData(); // Pobranie danych na starcie
        setInterval(this.fetchData, 1000); // Odświeżanie danych co sekundę
        setTimeout(() => {
            if (!this.dataLoaded) {
                location.reload(); // Odświeżenie strony po 3 sekundach, jeśli dane nie zostały załadowane
            }
        }, 3000);
    },
    methods: {
        fetchData() {
            const trainsEndpointUrl = "http://localhost:8080/api/pkp/trains/getBest";
            const infoEndpointUrl = "http://localhost:8080/api/pkp/trains/info";
            axios.all([
                axios.get(trainsEndpointUrl),
                axios.get(infoEndpointUrl)
            ])
                .then(axios.spread((trainsResponse, infoResponse) => {
                this.trains = trainsResponse.data;
                this.sortTrains();
                this.trainInfo = infoResponse.data;
                this.dataLoaded = true;
            }))
                .catch(error => {
                console.error("Wystąpił błąd:", error);
                this.dataLoaded = true;
            });
        },
        sortTrains() {
            this.sortedTrains = this.trains.sort((a, b) => b.distanceTraveled - a.distanceTraveled);
        },
        getStatusStyle(status) {
            switch (status) {
                case "KONIEC":
                    return "color: red;";
                case "NA STACJI":
                    return "color: orange;";
                case "W DRODZE":
                    return "color: green;";
                default:
                    return "";
            }
        }
    },
    components: { Vuefooter }
};
  </script>
  
<style scoped>
  .black {
    color: black;
  }
  .blue {
  color: #3b64c5;
}
  
  .ranks {
    width: 80%;
    margin: auto;
    font-size: x-large;
    font-family: 'Teko', sans-serif;
  }

  .ranks h1{
    font-size:5rem;
    color: #3b64c5;
  }
  
  .info {
    margin-bottom: 20px;
    font-size:3.5rem;
  }
  
  .info-label {
    font-weight: bold;
    padding-right: 2rem;
  }
  
  .info p {
    font-size: inherit;
    text-align: left;
    margin: 5px 0;
  }
  
  .ranks-table {
    width: 100%;
    border-collapse: collapse;
    background-color: var(--navMenuColor);
    border: 2px solid var(--appblue);
  }

  .ranks-thead{
    border-bottom: 2px solid var(--appblue);
  }

  .th-tr-ranks {
    padding: 8px;
    text-align: left;
    border-bottom: 1px solid #ddd;
    font-size:2rem;
    background-color: var(--navMenuColor);
  }
  
 
  
  .distance-column {
    width: 100px; /* Stała szerokość kolumny "Dystans" */
  }
  
  /* Responsywność dla telefonów */
  @media only screen and (max-width: 600px) {
    .ranks {
      width: 100%;
    }
    .th-tr-ranks {
      font-size: 1.2rem;
    }
  }
  </style>
  