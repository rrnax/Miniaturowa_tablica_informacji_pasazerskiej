<template>
    <div class="stops-list">
        <input v-model="searchStop" placeholder="Szukaj"/>
        <table class="stops-table" v-if="!isPresentInput">
            <tr class="stop-row">
                <th class="left-column">Przystanek</th>
                <th class="right-column">Status</th>
            </tr>
            <tr class="stop-row" v-for="stop in publishStopList" v-bind:key="stop.id">
                <td v-if="this.apiStore.getTransport === 'ztm'" class="left-column">{{ stop[0] }}</td>
                <td v-if="this.apiStore.getTransport === 'rail'" class="left-column">{{ stop.stop_name }}</td>
                <td v-if="checkSubscribeList(stop) === '2'" @click="addTosubscribed(stop)" class="right-column off">Obserwuj</td>
                <td v-if="checkSubscribeList(stop) === '1'" @click="observed" class="right-column on">Obserwujesz</td>
            </tr>
            
        </table>
        <table class="stops-table" v-if="isPresentInput">
            <tr class="stop-row">
                <th class="left-column">Przystanek</th>
                <th class="right-column"></th>
            </tr>
            <tr class="stop-row" v-for="stop in currentList" v-bind:key="stop.id">
                <td v-if="this.apiStore.getTransport === 'ztm'" class="left-column">{{ stop[0] }}</td>
                <td v-if="this.apiStore.getTransport === 'rail'" class="left-column">{{ stop.stop_name }}</td>
                <td v-if="checkSubscribeList(stop) === '2'" @click="addTosubscribed(stop)" class="right-column off">Obserwuj</td>
                <td v-if="checkSubscribeList(stop) === '1'" @click="observed" class="right-column on">Obserwujesz</td>
            </tr>
            <tr class="stop-row" v-if="!isStopExist">
                <td style="color: red; margin: auto;">Nie znaleźiono takiego przystanku!</td>
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

    setup(){
        const apiStore = useApiStore();
        const userStore = useUserStore();
        return { apiStore, userStore };
    },

    mounted(){
        this.userStore.downloadFavoriteStops();
    },


    watch: {
        // eslint-disable-next-line no-unused-vars
        searchStop(newText, oldText){
            if(newText === ""){
                this.isPresentInput = false;
            } else {
                this.isPresentInput = true;
                this.currentList = [];
                let tempList = this.publishStopList;
                if(this.apiStore.getTransport === "ztm"){
                    tempList.map(item => {
                    if(item[0].includes(this.searchStop) || item[0].toLowerCase().includes(this.searchStop)){
                        this.currentList.push(item);
                    }
                })   
                } else if( this.apiStore.getTransport === "rail"){
                    tempList.map(item => {
                    if(item.stop_name.includes(this.searchStop) || item.stop_name.toLowerCase().includes(this.searchStop)){
                        this.currentList.push(item);
                    }
                })
                }
                if(this.currentList.length === 0){
                    this.isStopExist = false;
                } else {
                    this.isStopExist = true;
                }
            }
        }
    },

    computed: {
        publishStopList(){
            let resultList = [];
            if(this.apiStore.getTransport === "ztm"){
                let temp = JSON.parse(JSON.stringify(this.apiStore.getStopsList));
                resultList = Object.entries(temp);
                resultList.sort(this.sortStops);
            } else if( this.apiStore.getTransport === "rail"){
                resultList = JSON.parse(JSON.stringify(this.apiStore.getStopsList));
                resultList.sort(this.sortStopsByName);
                console.log(resultList);
            }
            return resultList;
        },

        
    },

    methods: {
        sortStops( a, b ) {
            if ( a[0] < b[0] ){
                return -1;
            }
            if ( a[0] > b[0] ){
                return 1;
            }
            return 0;
        },

        sortStopsByName( a, b ) {
            if ( a.stop_name < b.stop_name ){
                return -1;
            }
            if ( a.stop_name > b.stop_name ){
                return 1;
            }
            return 0;
        },

        addTosubscribed(item){
            if(this.apiStore.getTransport === 'ztm'){
                this.userStore.addFavoriteStopInZtm(item);
                this.$router.push("/device");
            } else if (this.apiStore.getTransport === 'rail'){
                this.userStore.addFavoriteStopInRail(item);
                this.$router.push("/device");
            }
        },

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
                if(stop.stopName === item.stop_name){
                    result = "1";
                }
                });
            }
            return result;
        },

        observed(){
            this.$router.push("/device");
        }
    }

}

</script>

<style>
.stops-list {
    width: 100%;
    font-size: 22px;
    text-align: center;
    margin: auto;
}

input{
    width: 90%;
    margin: 30px auto;
}

.stops-table {
    display: block;
    width: 100%;
    height: 800px;
    overflow: scroll;
}

.stop-row {
    width: 100%;
    height: 50px;
    display: flex;
    align-items: center;
    border-top: 1px solid var(--appblue);
}

td {
    color: var(--changableElements);
}

.off {
 color: gray;
}

.on {
 color: green;
}

.left-column {
    width: 70%;
}

.right-column {
    width: 30%;
    cursor: pointer; 

}


</style>