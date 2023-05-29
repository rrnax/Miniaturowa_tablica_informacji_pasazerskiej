<template>
    <div id="mapContainer">
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
        };
    },

    props: ['stopsList'],

    watch: {
        stopsList(){
            if(this.apiStore.transport === 'rail'){
                this.drawStops();
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
                for(let i = 0; i < this.stopsList.length; i++){
                    this.markers[i+1] = L.marker([this.stopsList[i].stop_lat, this.stopsList[i].stop_lon]);
                    this.map.addLayer(this.markers[i+1]);
                    this.markers[i+1].bindPopup(this.stopsList[i].name);
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
        }

    
    }

}
</script>

<style scoped>
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