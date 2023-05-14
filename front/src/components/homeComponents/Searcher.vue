<template>
    <div class="searcher">
        <div class="check-field">
            <label for="trasnport-combobox">Rodzaj transportu</label>
            <select v-model="transport" name="transports" id="transport-combobox">
                <option hidden disabled selected value>-- Wybierz rodzaj transportu --</option>
                <option value="rail">Kolej</option>
                <option value="ztm">Transport Miejski</option>
            </select>
        </div>
        <div v-if="trasnportChecked" class="check-field">
            <label for="city-combobox">Miasto</label>
            <select v-model="city" name="cities" id="city-combobox">
                <option hidden disabled value="">-- Wybierz miasto --</option>
                <option value="gdansk">Gdańsk</option>
                <option value="warsaw">Warszawa</option>
            </select>
        </div>
        <div v-if="isLoaded" class="loader"></div>
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
        
    },

    data(){
        return {
            transport: "",
            city: "",
            cities: [],
            trasnportChecked: false,
            isLoaded: false,
        }
    },

    watch: {
        // eslint-disable-next-line no-unused-vars
        async transport(newTransport, oldTransport) {         //change state about kind of transport in store
            if(this.trasnportChecked){
                this.city = "";
            }
            this.trasnportChecked = true;
            this.apiStore.$reset();
            this.apiStore.setTransport(newTransport);
            this.urlCreator(newTransport);
        },

        // eslint-disable-next-line no-unused-vars
        city(newCity, oldCity){                 //change state of city in store
            let transportValue = document.querySelector("#transport-combobox").value;
            this.apiStore.$reset();
            this.apiStore.setTransport(transportValue);
            this.apiStore.setCity(newCity);
            this.urlCreator(this.apiStore.getTransport);
        },
    
    
    },

    methods: {
        async urlCreator(option){        //selection for corect api download option means that was a rail or public transport
            this.isLoaded = true;
            switch(this.apiStore.getCity){
                case 'warsaw':
                    this.apiStore.useCorrectApi(option,"warszawa");
                    await this.apiStore.downloadStops();
                    this.newListSignal();
                    break;
                case 'gdansk':
                    this.apiStore.useCorrectApi(option, "gdansk");
                    await this.apiStore.downloadStops();
                    this.newListSignal();
                    break;
                case 'Bydgoszcz':
                    console.log(option, "bydgoszcz");
                    break;
                default:
                    console.log("defult");
                    break;
            }
            this.isLoaded = false;
        },

        newListSignal(){
            this.$emit('changeStopsList');
        },

    
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