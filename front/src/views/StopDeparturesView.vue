<template>
    <div class="chosen-stop-departures">
        <div v-if="this.apiStore.getLoadedInfo" class="loader"></div>
        <div class="stop-headsign">
            <h1>Odjazdy</h1>
            <h1 v-if="this.apiStore.getCity != ''">{{ this.apiStore.getDeparturesStop[0] }}</h1>
            <h1 v-else>{{ this.apiStore.getDeparturesStop.name }}</h1>
        </div>
        <hr>   
        <tr class="column-departures description">
            <th v-if="this.apiStore.getTransport != 'rail'" class="col1">Linia</th>
            <th v-else class="col1">Peron</th>
            <th class="col2">Kierunek</th>
            <th class="col3">Godzina</th>
        </tr>
        <table class="departures-table">    
            <tr v-for="route in this.apiStore.getDepartures" class="column-departures content" v-bind:key="route.id">
                <td v-if="this.apiStore.getCity === 'Gdańsk'" class="col1">{{ route.routeId }}</td>
                <td v-else class="col1">{{ route.tripId }}</td>
                <td class="col2">{{ route.headsign }}</td>
                <td class="col3">{{ route.estimatedTime }}
                    <p style="color: rgb(216, 54, 54);" v-if="(this.apiStore.getCity === 'Gdańsk' || this.apiStore.getUrl.includes('gdansk')) && (route.delayInSeconds/60) * (-1) >= 1">&nbsp;&nbsp;&nbsp;+{{ Math.round((route.delayInSeconds/60) * (-1)) }}min</p>
                </td>
            </tr>
        </table>
    </div>
    <Footer />
</template>

<!-- this.apiStore.getDeparturesStop.cityName.includes('Gdańsk')  -->

<script>
import { useApiStore } from '@/store/apiManagment.store';
import Footer from '@/components/Footer.vue';

export default{
    name: "StopsDepartureView",
    components: {
        Footer,
    }, 

    setup(){
        const apiStore = useApiStore();
        return { apiStore };
    },
}

</script>

<style>
.chosen-stop-departures {
    width: 60%;
    margin: 20px auto 100px auto;
    font-family: 'Teko', sans-serif;
    font-size: 22px;
    color: var(--appblue);
}

.stop-headsign {
    display: flex;
    justify-content: space-between;
}

.column-departures {
    padding: 10px 0;
    display: flex;
    justify-content: space-between;
    border-bottom: 2px solid var(--appblue);
    font-size: 28px;
}

.departures-table{
    width: 100%;
    height: 600px;
    display: block;
    overflow-y: scroll;
    background: linear-gradient( var(--themeMenu), var(--navMenuColor));
    border-radius: 0 0 20px 20px;
    color: var(--changableElements);
}

.description {
    border-radius: 20px 20px 0 0;
    background: var(--themeMenu);
}

.content {
    text-align: center;
}

.col1 {
    width: 20%;
}

.col2 {
    width: 60%;
}

.col3{
    width: 20%;
    display: flex;
}

@media only screen and (max-width: 800px) {
    .chosen-stop-departures {
        width: 95%;
    }
    
}

@media only screen and (max-width: 500px) {
    .stop-headsign {
        display: block;
    }
    
}
</style>