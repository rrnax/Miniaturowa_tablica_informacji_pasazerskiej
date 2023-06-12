<template>
    <tr class="subscribed-rows header">
        <th class="col-name">Nazwa Przystanku</th>
        <th class="col-city">Miasto/Rodzaj</th>
        <th class="col-stat">Status</th>
        <th class="col-actions">Akcje</th>
    </tr>
    <table class="subscribed-table">
        <tr v-if="!isSubscribedStops" class="warning-message">Nie ma dodanych przystanków!</tr>
        <tr v-for="stop in this.userStore.getFavorites" v-bind:key="stop.stopName" class="subscribed-rows">
            <td class="col-name">{{ stop.stopName }}</td>
            <td class="col-city">{{ stop.cityName }}</td>
            <td v-if="stop.status" class="col-stat positive">Wyświetlane</td>
            <td v-if="!stop.status" class="col-stat negative">Niewyświetlane</td>
            <td class="col-actions">
                <a v-if="stop.status" class="actions" @click="finishDisplay(stop)">Zakończ</a>
                <a v-if="!stop.status" class="actions" @click="displayStop(stop)">Wyświetl</a>
                <p class="actions"> / </p>
                <a class="actions" @click="deleteStop(stop)">Usuń</a>
            </td>
        </tr>
    </table>
</template>

<script>
import { useApiStore } from '@/store/apiManagment.store';
import { useUserStore } from '@/store/user.stroe';

export default{
    name: "SubscribedStopsList",

    setup(){
        const apiStore = useApiStore();
        const userStore = useUserStore();
        return { apiStore, userStore };
    },

    data(){
        return{
            isSubscribedStops: false,
        }
    },

    //Download subscribed stops befor Component is mounted
    async created(){
        await this.userStore.downloadFavoriteStops();
        if(this.userStore.getFavorites.length > 0){
            this.isSubscribedStops = true;
        }
    },

    methods: {
        showStopDepartures(stop){
            console.log(stop);
            // this.apiStore.setDeparturesStop(stop);
            // this.apiStore.makeDepartureList(stop);
            // this.$router.push("/departures");
        },

        displayStop(stop) {
            this.userStore.activeNewStop(stop);
            setTimeout(this.apiStore.converActiveStop, 500);
            setTimeout(this.apiStore.updateDepartureList, 700);
            this.$emit("change");
        },

        finishDisplay(stop) {
            this.userStore.turnOffActiveStop(stop);
            setTimeout(this.apiStore.converActiveStop, 500);
            setTimeout(this.apiStore.updateDepartureList, 700);
            this.$emit("change");

        },

        deleteStop(stop){
            this.userStore.deleteFavoriteStop(stop);
            this.$emit("change");

        }
    }

}

</script>

<style>
.subscribed-table {
    width: 100%;
    height: 400px;
    margin: auto;
    display: block;
    overflow: scroll;
    background-color: var(--navMenuColor);
    border-radius: 0 0 20px 20px;
}

.subscribed-rows {
    width: 100%;
    height: 70px;
    display: flex;
    justify-content: space-around;
    align-items: center;
    color: var(--changableElements);
    border-bottom: 2px solid var(--appblue);
}

.header {
    color: var(--appblue);
    background-color: var(--navMenuColor);
    border-radius: 20px 20px 0 0;
}

.col-name {
    width: 40%;
    margin: 0 0 0 20px;
    padding: 0;
}

.col-city {
    width: 20%;
    margin: 0;
    padding: 0;
    text-align: center;
}

.col-stat {
    width: 20%;
    margin: 0;
    padding: 0;
    text-align: center;
}

.col-actions {
    width: 20%;
    margin: 0;
    padding: 0;
    text-align: center;
}

.actions {
    display: inline;
    color: var(--halfView);
    text-decoration: underline;
    cursor: pointer;
}

@media screen and (max-width: 600px) {
    .col-stat {
        display: none;
    }
}

@media screen and (max-width: 400px) {
    .col-city {
        display: none;
    }
}
</style>