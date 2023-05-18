<template>
    <tr class="subscribed-rows header">
        <th class="col-name">Nazwa Przystanku</th>
        <th class="col-city">Miasto/Rodzaj</th>
        <th class="col-stat">Status</th>
        <th class="col-actions">Akcje</th>
    </tr>
    <table class="subscribed-table">
        <tr v-if="!isSubscribedStops" class="warning-message">Nie można pobrać przystanków, spróbuj później!</tr>
        <tr v-for="stop in getSubscribedStops" v-bind:key="stop.stopName" class="subscribed-rows">
            <td class="col-name">{{ stop.stopName }}</td>
            <td class="col-city">{{ stop.cityName }}</td>
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
            subscribedList: [],
            isSubscribedStops: false,
        }
    },

    //Download subscribed stops befor Component is mounted
    async created(){
        await this.userStore.downloadFavoriteStops();
        this.subscribedList = JSON.parse(JSON.stringify(this.userStore.getFavorites));
        if(this.subscribedList.length > 0){
            this.isSubscribedStops = true;
        }
        this.subscribedList.map(stop => {
            if(stop.status === true){
                this.apiStore.setActiveStop(stop);
            }
        })
    },

    computed: {
        //List with subscribed stops
        getSubscribedStops(){
            let tempList =  JSON.parse(JSON.stringify(this.subscribedList));
            return tempList.sort(this.sortStopsByName);
        }
    },

    methods: {
        async deleteSubscribtion(stop){
            await this.userStore.deleteFavoriteStop(stop.id);
            let objectToRemove = this.subscribedList.find(element => element.id === stop.id);
            let indexObejctToRemove = this.subscribedList.indexOf(objectToRemove);
            this.subscribedList.splice(indexObejctToRemove,1);
        },

        async activateStop(stop){
            await this.subscribedList.forEach(async item => {
                if(item.status === true){
                    await this.userStore.changeStatus(false, item.id);
                    item.status = false;
                }
                if(item.stopName === stop.stopName){
                    await this.userStore.changeStatus(true, item.id);
                    item.status = true;
                }
            })
            this.apiStore.setActiveStop(stop);
        },

        async desactiveStop(stop){
            await this.subscribedList.forEach(async item => {
                if(item.StopName === stop.stopName){
                    await this.userStore.changeStatus(false, item.id);
                    item.status = false;
                }
            })
        },

        updateAllStopsStatus(){
            this.subscribedList.forEach(item => {
                console.log(item.status);
            })
        },

        sortStopsByName( a, b ) {
            if ( a.stopName < b.stopName ){
                return -1;
            }
            if ( a.stopName > b.stopName ){
                return 1;
            }
            return 0;
        },
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