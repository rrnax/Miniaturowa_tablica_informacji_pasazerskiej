<template>
    <div id="mapContainer">
        <button id="my-location">Moja lokalizacja</button>
    </div>
</template>

<script>

// import "leaflet/dist/leaflet.css"
import L from "leaflet";
export default{
    name: "MapSection",

    data() {
        return{
            map: null,
            location: null,
            mapLoader: null,
            markers: [],
        };
    },

    // created() {
    //     if(!("geolocation" in navigator)){
    //         console.log("Geolocation is not avilable");
    //         return;
    //     }
    //     navigator.geolocation.getCurrentPosition( pos => {
    //         this.location = pos;
    //         console.log(this.location);
    //     }, err => {
    //         console.log(err);
    //     })
    // },

    mounted(){
        this.map = L.map("mapContainer").setView([51.1221,0.8], 13);
        L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
        }).addTo(this.map);
        this.setMyGeolocation();
    },

    methods: {
        setMyGeolocation(){
            if(!("geolocation" in navigator)){
                console.log("Geolocation is not avilable");
                return;
            }
            this.mapLoader.show();
            navigator.geolocation.getCurrentPosition( pos => {
                this.location = pos;
                this.markers[0] = L.marker([pos.coords.latitude, pos.coords.longitude]).addTo(this.map);
                this.markers[0].bindPopup("<b>Twoja lokalizacja</b>").openPopup();
                this.map.setView([pos.coords.latitude, pos.coords.longitude], 13);
            }, err => {
                console.log(err);
            });
        },
    }

}
</script>

<style scoped>
#mapContainer{
    width: 100%;
    height: 500px;
    z-index: 1;
}

#my-location {
  position: absolute;
  top: 20px;
  right: 20px;
  padding: 10px;
  z-index: 400;
}

</style>