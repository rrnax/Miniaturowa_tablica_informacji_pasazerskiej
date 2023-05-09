<template>
    <div class="searcher">
        <div class="check-field">
            <label for="trasnport-combobox">Rodzaj transportu</label>
            <select v-model="transport" name="transports" id="transport-combobox">
                <option v-if="this.noneAllowedTransport" value="none"></option>
                <option value="rail">Kolej</option>
                <option value="traffic">Transport Miejski</option>
            </select>
        </div>
        <div class="check-field">
            <label for="city-combobox">Miasto</label>
            <select v-model="city" name="cities" id="city-combobox">
                <option v-if="noneAllowedCity" value="none"></option>
                <option value="gdansk">Gdańsk</option>
                <option value="warsaw">Warszawa</option>
            </select>
        </div>
    </div>
</template>

<script>
import { useApiStore } from '@/store/apiManagment.store';

export default{
    // eslint-disable-next-line vue/multi-word-component-names
    name: "Searcher",
    setup(){
        const apiStore = useApiStore();
        return { apiStore };
    },

    mounted(){
        if (this.apiStore.getCity !== "") {
            let $select = document.querySelector("#city-combobox");
            $select.value = this.apiStore.getCity;
        }

        if (this.apiStore.getTransport !== "") {
            let $select = document.querySelector("#transport-combobox");
            $select.value = this.apiStore.getTransport;
        }

        

    },

    data(){
        return {
            transport: "",
            city: "",
            noneAllowedTransport: true,
            noneAllowedCity: true,
        }
    },

    watch: {
        // eslint-disable-next-line no-unused-vars
        transport(newTransport, oldTransport) {         //change state about kind of transport in store
            this.noneAllowedTransport = false;
            this.resetApiState(2);
            this.apiStore.setTransport(newTransport);
            this.urlCreator();
        },

        // eslint-disable-next-line no-unused-vars
        city(newCity, oldCity){                 //change state of city in store
            this.noneAllowedCity = false;
            this.resetApiState(1);
            this.apiStore.setCity(newCity);
            this.urlCreator();
        }
    },

    methods: {
        async urlCreator(){        //selection for corect api download
            switch(this.apiStore.getCity){
                case 'warsaw':
                    switch(this.apiStore.getTransport){
                        case 'rail':
                            console.log("warsaw rail");
                            break;
                        case 'traffic':
                            console.log("warsaw traffic");
                            break;
                        default:
                            console.log("wwa deafult");
                            break;
                    }
                    break;
                case 'gdansk':
                    switch(this.apiStore.getTransport){
                            case 'rail':
                                console.log("gdans rail");
                                break;
                            case 'traffic':
                                this.apiStore.useCorrectApi("ztm", "gdansk");
                                await this.apiStore.downloadStops();
                                this.newListSignal();
                                break;
                            default:
                                console.log("gdansk deafult");
                                break;
                        }
                        break;
                default:
                    console.log("defult");
                    break;
            }
        },

        newListSignal(){
            this.$emit('changeStopsList');
        },

        resetApiState(option){          //Option means that which city or transport were changed
            let cityValue = document.querySelector("#city-combobox").value;
            let transportValue = document.querySelector("#transport-combobox").value;
            this.apiStore.$reset();
            if(option === 1){           //City was changed
                this.apiStore.setTransport(transportValue);
            } else {            //Transport was changed
                this.apiStore.setCity(cityValue);
            }
        }
    
    },
}

</script>

<style>
.searcher {
    width: 100%;
    margin: 0;
    padding: 20px 0 40px 0;
    font-size: 22px;
}

.check-field {
    width: 70%;
    margin: auto;
    display: grid;
}

</style>