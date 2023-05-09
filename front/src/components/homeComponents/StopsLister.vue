<template>
    <div class="stops-list">
        <input v-model="searchStop" placeholder="Szukaj"/>
        <table v-if="!isPresentInput">
            <tr>
                <th class="left-column">Przystanek</th>
                <th class="right-column">Status</th>
            </tr>
            <tr v-for="stop in publishStopList" v-bind:key="stop.name">
                <td class="left-column">{{ stop.name }}</td>
                <td v-if="!stop.subscribed" @click="addTosubscribed(stop)" class="right-column off">Obserwuj</td>
                <td v-if="stop.subscribed" class="right-column on">Obserwujesz</td>
            </tr>
        </table>
        <table v-if="isPresentInput">
            <tr>
                <th class="left-column">Przystanek</th>
                <th class="right-column">Status</th>
            </tr>
            <tr v-for="stop in currentList" v-bind:key="stop">
                <td class="left-column">{{ stop.name }}</td>
                <td class="right-column off">Obserwuj</td>
            </tr>
            <tr v-if="!isStopExist">
                <td style="color: red; margin: auto;">Nie znaleźiono takiego przystanku!</td>
            </tr>
        </table>
    </div>
</template>

<script>
import { useApiStore } from '@/store/apiManagment.store';

export default{
    name: "StopsLister",

    data() {
        return {
            isPresentInput: false,
            isStopExist: true,
            searchStop: "",
            currentList: []
        }
    },

    setup(){
        const apiStore = useApiStore();
        return { apiStore };
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
                tempList.map(item => {
                    if(item.name.includes(this.searchStop) || item.name.toLowerCase().includes(this.searchStop)){
                        this.currentList.push(item);
                    }
                })
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
            let resultList = JSON.parse(JSON.stringify(this.apiStore.getStopsList));
            resultList.sort(this.sortStops);
            return resultList;
        },
    },

    methods: {
        sortStops( a, b ) {
            if ( a.name < b.name ){
                return -1;
            }
            if ( a.name > b.name ){
                return 1;
            }
            return 0;
        },

        addTosubscribed(item){
            console.log(item);
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

table {
    display: grid;
    width: 100%;
}

tr {
    width: 100%;
    display: flex;
    text-align: center;
    border-bottom: 1px solid var(--appblue);
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