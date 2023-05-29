<template>
    <div class="stops-list">
        <input v-model="searchStop" placeholder="Szukaj" class="search-input"/>
        <table class="stops-table">
            <tr class="stop-row">
                <th class="l column headset">Przystanek</th>
                <th class="r column headset">Status</th>
            </tr>
            <tr class="stop-row" v-for="stop in publishStopList" v-bind:key="stop.id">
                <td v-if="this.apiStore.getTransport === 'ztm'" @click="showStopDepartures(stop)" class="left column">{{ stop[0] }}</td>
                <td v-if="this.apiStore.getTransport === 'rail'"  @click="showStopDepartures(stop)" class="left column">{{ stop.name }}</td>
                <td v-if="checkSubscribeList(stop) === '2'" @click="addTosubscribed(stop)" class="right off column">Obserwuj</td>
                <td v-if="checkSubscribeList(stop) === '1'" @click="observed" class="right on column">Obserwujesz</td>
            </tr>
            <tr class="stop-row" v-if="!isStopExist">
                <td class="negative">Nie znaleźiono takiego przystanku!</td>
            </tr>
        </table>
    </div>
</template>

<script>
import { useApiStore } from '@/store/apiManagment.store';
import { useUserStore } from '@/store/user.stroe';

export default{
    name: "StopsLister",

    data() {
        return {
            isPresentInput: false,
            isStopExist: true,
            isLoaded: false,
            searchStop: "",
            currentList: []
        }
    },

    //Seting data managment stores
    setup(){
        const apiStore = useApiStore();
        const userStore = useUserStore();
        return { apiStore, userStore };
    },

    watch: {
        //Simple seacher in table of stops
        // eslint-disable-next-line no-unused-vars
        searchStop(newText, oldText){
            if(newText === ""){
                this.isPresentInput = false;
            } else {
                this.isPresentInput = true;
                this.createListWithPattern();
                if(this.currentList.length === 0){
                    this.isStopExist = false;
                } else {
                    this.isStopExist = true;
                }
            }
        }
    },

    computed: {
        //Dynamic data in table 
        publishStopList(){
            if(this.isPresentInput){
                return this.currentList;
            } else {
                return this.createStopsList();
            }
        },
    },

    methods: {
        //Find pattern in list and create new one
        createListWithPattern(){
            this.currentList = [];
            let tempList = this.createStopsList();
            if(this.apiStore.getTransport === 'ztm'){
                tempList.map(stop => {
                    if(stop[0].includes(this.searchStop) || stop[0].toLowerCase().includes(this.searchStop)){
                        this.currentList.push(stop);
                    }
                })
            } else if ( this.apiStore.getTransport === "rail"){
                tempList.map(stop => {
                    if(stop.name.includes(this.searchStop) || stop.name.toLowerCase().includes(this.searchStop)){
                        this.currentList.push(stop);
                    }
                })
            }
        },

        //Create list of all stops wich is sorted
        createStopsList(){
            let resultList = [];
            if(this.apiStore.getTransport === "ztm"){
                resultList = Object.entries(JSON.parse(JSON.stringify(this.apiStore.getStopsList)));
                resultList.sort(this.sortStops);
            } else if( this.apiStore.getTransport === "rail"){
                resultList = JSON.parse(JSON.stringify(this.apiStore.getStopsList));
            }
            return resultList;
        },

        //Info about watching stops by user
        checkSubscribeList(item){
            let result = "2";
            if(this.apiStore.getTransport === 'ztm'){
                this.userStore.getFavorites.forEach(stop => {
                    if(stop.stopName === item[0]){
                        result = "1"
                    }
                });
            } else if (this.apiStore.getTransport === 'rail'){
                this.userStore.getFavorites.forEach(stop => {
                    if(stop.stopName === item.name){
                        result = "1";
                    }
                });
            }
            return result;
        },

        //Add specific stop to favorite
        async addTosubscribed(item){
            if(this.apiStore.getTransport === 'ztm'){
                await this.userStore.addFavoriteStopInZtm(item);
                this.$router.push("/device");
            } else if (this.apiStore.getTransport === 'rail'){
                await this.userStore.addFavoriteStopInRail(item);
                this.$router.push("/device");
            }
        },

        //Function to change the view for device
        observed(){
            this.$router.push("/device");
        },

        //Show nearest departures for chosen stop
        showStopDepartures(stop){
            this.apiStore.setDeparturesStop(stop);
            this.apiStore.makeDepartureList(stop);
            this.$router.push("/departures");
        },

        //Compare function by name
        sortStops( a, b ) {
            if ( a[0] < b[0] ){
                return -1;
            }
            if ( a[0] > b[0] ){
                return 1;
            }
            return 0;
        },
    }

}

</script>

<style>
.stops-list {
    width: 100%;
    margin: auto;
    text-align: center;
    font-size: 22px;
}

.search-input{
    width: 90%;
    margin: 30px auto;
}

.stops-table {
    width: 100%;
    height: 800px;
    display: block;
    overflow: scroll;
}

.headset {
    color: var(--appblue);
}

.l {
    width: 70%;
    height: 100%;
    cursor: auto !important;
}

.r {
    width: 30%;
    cursor: auto !important;
}

.stop-row {
    width: 100%;
    height: 50px;
    display: flex;
    align-items: center;
    border-top: 1px solid var(--appblue);
    color: var(--changableElements);
}

.off {
    color: gray;
}

.on {
    color: green;
}

.column {
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
}

.left {
    width: 70%;
    height: 100%;
    
}

.left:hover {
    background-color: var(--appblue);
    color: var(--whiteText);
}

.right {
    width: 30%;
}

.right:hover {
    color: black;
}

</style>