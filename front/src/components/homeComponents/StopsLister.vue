<template>
    <div class="stops-list">
        <input v-model="searchStop" placeholder="Szukaj"/>
        <table v-if="!isPresentInput">
            <tr>
                <th class="left-column">Przystanek</th>
                <th class="right-column">Status</th>
            </tr>
            <tr v-for="stop in publishStopList" v-bind:key="stop">
                <td class="left-column">{{ stop }}</td>
                <td class="right-column off">Obserwuj</td>
            </tr>
        </table>
        <table v-if="isPresentInput">
            <tr>
                <th class="left-column">Przystanek</th>
                <th class="right-column">Status</th>
            </tr>
            <tr v-for="stop in currentList" v-bind:key="stop">
                <td class="left-column">{{ stop }}</td>
                <td class="right-column off">Obserwuj</td>
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
            searchStop: "",
            currentList: []
        }
    },

    props: ['stopsList'],

    setup(){
        const apiStore = useApiStore();
        return { apiStore };
    },

    watch: {
        searchStop(newText, oldText){
            if(newText === ""){
                this.isPresentInput = false;
            } else {
                this.isPresentInput = true;
                console.log(oldText);
            }
        }
    },

    computed: {
        publishStopList(){
            let tempList = JSON.parse(JSON.stringify(this.stopsList));
            let resultList = [];
            tempList.map(item => {
                if(!resultList.includes(item.name)){
                    resultList.push(item.name);
                }
            })
            resultList.sort();
            return resultList;
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
}


</style>