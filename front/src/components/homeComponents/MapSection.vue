<template>
    <div id="mapContainer">
        <button @click="findNearest" id="near"> Wyszukaj najbliższy przystanek</button>
        <button @click="setMyGeolocation" id="my-location">
            <img alt="Moja lokalizacja" src="../../assets/destination.png" id="destination"/>
        </button>
        <div v-if="isLoading" class="loader" id="visable"></div>
    </div>
</template>

<script>
// import { toRaw } from "vue";
import { useApiStore } from "@/store/apiManagment.store";
import L from "leaflet";
import router from "@/router";
import axios from "axios";

export default{
    name: "MapSection",

    setup(){
        const apiStore = useApiStore();
        return { apiStore };
    },

    data() {
        return{
            map: null,
            location: null,
            isLoading: false,
            markers: [],
            groupMarkers: null,
            userIcon: null,
            ztmGeoList: [],
        };
    },

    props: ['stopsList'],

    watch: {
        stopsList(){
            this.clearMap();
            if(this.apiStore.getTransport === 'rail' && this.apiStore.getCity === ''){
                this.drawStops();
            } else if(this.apiStore.getTransport === 'ztm' && this.apiStore.getCity === 'Gdańsk'){
                axios.get("api/ztm/gdansk/displays/geo")
                .then(response => {
                    this.isLoading = true;
                    this.ztmGeoList = response.data;
                    this.drawStopsZtm();
                    this.isLoading = false;
                })
            } else {
                this.clearMap();
            }
        }
    },

    mounted(){
        this.map = L.map("mapContainer").setView([53.1115299,18.0236991], 13);
        L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
        }).addTo(this.map);
        this.userIcon = new L.Icon({
                    iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-green.png',
                    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',

                    iconSize: [25, 41],
                    iconAnchor: [12, 41],
                    popupAnchor: [1, -34],
                    shadowSize: [41, 41]
                });
        
        this.setMyGeolocation();

    },

    methods: {
        setMyGeolocation(){
            
            this.isLoading = true;
            if(!("geolocation" in navigator)){
                console.log("Geolocation is not avilable");
                return;
            }
            navigator.geolocation.getCurrentPosition( pos => {
                this.location = pos;
                this.markers[0] = L.marker([pos.coords.latitude, pos.coords.longitude], {icon: this.userIcon}).addTo(this.map);
                this.markers[0].bindPopup("<b>Twoja lokalizacja</b>").openPopup();
                this.map.setView([pos.coords.latitude, pos.coords.longitude], 13);
                this.isLoading = false;
            }, err => {
                console.log(err);
            });
        },

        takeStopList(){
            this.stopList = this.apiStore.getStopsList;
            this.drawStops();
        },

        drawStops(){
            if(this.stopsList === null){
                return;
            } else {
                var contents = [];
                var popups = [];
                for(let i = 0; i < this.stopsList.length; i++){
                    contents[i] = L.DomUtil.create('b', 'markers');
                    popups[i] = L.popup().setContent(contents[i]);
                    contents[i].innerHTML = this.stopsList[i].name;
                    this.markers[i+1] = L.marker([this.stopsList[i].stop_lat, this.stopsList[i].stop_lon]);
                    this.map.addLayer(this.markers[i+1]);
                    this.markers[i+1].bindPopup(popups[i]);
                    L.DomEvent.addListener(contents[i], 'click', function(event){
                        const apiStore = useApiStore();
                        let list = apiStore.getStopsList;
                        let nameStop = event.target.innerHTML;
                        for(let j = 0; j < list.length; j++){
                            if(nameStop === list[j].name){
                                apiStore.setDeparturesStop(list[j]);
                                apiStore.makeDepartureList(list[j]);
                                router.push("/departures")
                            }
                        }
                    })
                }
            }
            
        },

        drawStopsZtm(){
            if(this.ztmGeoList === null){
                return;
            } else {
                var contents = [];
                var popups = [];
                for(let i = 0; i < this.ztmGeoList.length; i++){
                    contents[i] = L.DomUtil.create('b', 'markers');
                    popups[i] = L.popup().setContent(contents[i]);
                    contents[i].innerHTML = this.ztmGeoList[i].name;
                    this.markers[i+1] = L.marker([this.ztmGeoList[i].stopLat, this.ztmGeoList[i].stopLon]);
                    this.map.addLayer(this.markers[i+1]);
                    this.markers[i+1].bindPopup(popups[i]);
                    L.DomEvent.addListener(contents[i], 'click', function(event){
                        const apiStore = useApiStore();
                        let list = Object.entries(apiStore.getStopsList);
                        let nameStop = event.target.innerHTML;
                        for(let j = 0; j < list.length; j++){
                            if(nameStop === list[j][0]){
                                apiStore.setDeparturesStop(list[j]);
                                apiStore.makeDepartureList(list[j]);
                                router.push("/departures")
                            }
                        }
                    })
                }
            }
            
        },

        clearMap(){
            this.map.remove();
            this.map = L.map("mapContainer").setView([53.1115299,18.0236991], 13);
            L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 19,
            attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
            }).addTo(this.map);
            this.setMyGeolocation();
        },

        showStopDepartures(stop){
            this.apiStore.setDeparturesStop(stop);
            this.apiStore.makeDepartureList(stop);
            this.$router.push("/departures");
        },

        findNearest(){
            if(this.apiStore.getTransport === 'rail' && this.apiStore.getCity === ''){
              let latlangs = Array();
              latlangs.push(this.markers[0].getLatLng());
              let min = Math.sqrt(Math.pow((this.stopsList[0].stop_lat - this.location.coords.latitude),2) + Math.pow((this.stopsList[0].stop_lon - this.location.coords.longitude),2));
              let nearStop = this.stopsList[0];
              let index = 0;
              for(let i = 1 ; i < this.stopsList.length ; i++){
                let temp = Math.sqrt(Math.pow((this.stopsList[i].stop_lat - this.location.coords.latitude),2) + Math.pow((this.stopsList[i].stop_lon - this.location.coords.longitude),2));
                if(temp < min){
                    min = temp;
                    nearStop = this.stopsList[i];
                    index = i;
                }
              }
              console.log(nearStop);
              latlangs.push(this.markers[index+1].getLatLng());
              let polyline = L.polyline(latlangs, {color: 'blue'}).addTo(this.map);
              this.map.fitBounds(polyline.getBounds());
            } else if(this.apiStore.getTransport === 'ztm' && this.apiStore.getCity === 'Gdańsk'){
              let latlangs = Array();
              latlangs.push(this.markers[0].getLatLng());
              let min = Math.sqrt(Math.pow((this.ztmGeoList[0].stopLat - this.location.coords.latitude),2) + Math.pow((this.ztmGeoList[0].stopLon - this.location.coords.longitude),2));
              let nearStop = this.ztmGeoList[0];
              let index = 0;
                for(let i = 1 ; i < this.ztmGeoList.length ; i++){
                    let temp = Math.sqrt(Math.pow((this.ztmGeoList[i].stopLat - this.location.coords.latitude),2) + Math.pow((this.ztmGeoList[i].stopLon - this.location.coords.longitude),2));
                    if(temp < min){
                        min = temp;
                        nearStop = this.ztmGeoList[i];
                        index = i;
                    }
                }
              console.log(nearStop);
              latlangs.push(this.markers[index+1].getLatLng());
              let polyline = L.polyline(latlangs, {color: 'blue'}).addTo(this.map);
              this.map.fitBounds(polyline.getBounds());
            } else {
                alert("Najpierw wybierz rodzaj komunikacji i miejsce");
            }  
        }

    },
}
</script>

<style scoped>
#near {
  position: absolute;
  top: 11px;
  left: 45px;
  z-index: 500;
  padding-top: 3px;
  font-size: 18px;
  font-family: 'Teko', sans-serif;
  background-color: white;
  border: 2px solid #a5a1a1;
  border-radius: 5px;
  cursor: pointer;
}

#near:hover {
    color: var(--appblue);
}

#mapContainer{
    width: 100%;
    height: 500px;
    z-index: 0;
}


#my-location {
  position: absolute;
  top: 80px;
  left: 10px;
  z-index: 500;
  padding-top: 3px;
  background-color: white;
  border: 2px solid #a5a1a1;
  border-radius: 5px;
  cursor: pointer;
}

#destination {
  width: 22px;
  height: 24px;
}

#visable {
    position: absolute;
    left: 48%;
    top: 38%;
    z-index: 501 !important;
}

</style>