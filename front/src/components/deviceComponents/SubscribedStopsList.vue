<template>
    <tr class="subscribed-rows header">
        <th class="col-name">Nazwa Przystanku</th>
        <th class="col-city">Miasto</th>
        <th class="col-stat">Status</th>
        <th class="col-actions">Akcje</th>
    </tr>
    <table class="subscribed-table">
        <tr v-if="!isSubscribedStops" class="warning-message">Nie można pobrać przystanków, spróbuj później!</tr>
        <tr v-for="stop in getSubscribedStops" v-bind:key="stop.name" class="subscribed-rows">
            <td class="col-name">{{ stop.name }}</td>
            <td class="col-city">Miasto</td>
            <td v-if="stop.status" class="col-stat positive">Wyświetlane</td>
            <td v-if="!stop.status" class="col-stat negative">Niewyświetlane</td>
            <td class="col-actions">
                <a v-if="stop.status" class="actions" @click="desactiveStop(stop)">Zakończ</a>
                <a v-if="!stop.status" class="actions" @click="activateStop(stop)">Wyświetl</a>
                <p class="actions"> / </p>
                <a class="actions" @click="deleteSubscribtion(stop)">Usuń</a>
            </td>
        </tr>
    </table>
</template>

<script>
import { useApiStore } from '@/store/apiManagment.store';

export default{
    name: "SubscribedStopsList",

    setup(){
        const apiStore = useApiStore();
        return { apiStore };
    },

    data(){
        return{
            isSubscribedStops: false,
        }
    },

    //Download subscribed stops befor Component is mounted
    created(){
        this.apiStore.downloadStops();
        let tempList = this.apiStore.getStopsList;
        if(tempList.length > 0){
            this.isSubscribedStops = true;
        }
    },

    computed: {
        //List with subscribed stops
        getSubscribedStops(){
            return JSON.parse(JSON.stringify(this.apiStore.getStopsList));
        }
    },

    methods: {
        async deleteSubscribtion(stop){
            await console.log(stop);
        },

        async activateStop(stop){
            await console.log(stop);
        },

        async desactiveStop(stop){
            await console.log(stop);
        }
    }

}

</script>

<style>
.subscribed-table {
    width: 100%;
    height: 400px;
    margin: auto;
    display: grid;
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
    border-bottom: 2px solid var(--appblue);
}

.header {
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

</style>