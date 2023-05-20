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
                <option value="Gdańsk">Gdańsk</option>
                <option value="Warszawa">Warszawa</option>
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

    props: {
        isComboBoxFill: Boolean,
    },

    data(){
        return {
            transport: "",
            city: "",
            trasnportChecked: false,
            isLoaded: false,
        }
    },

    watch: {
        //Control tansport checkbox changes
        // eslint-disable-next-line no-unused-vars
        async transport(newTransport, oldTransport) {        
            if(this.trasnportChecked){
                this.city = "";
            }
            if(newTransport === "ztm"){
                this.trasnportChecked = true;
                if(this.isComboBoxFill === true){
                    this.displayListSignal(false);
                }
            } else {
                this.trasnportChecked = false;
            }
            this.apiStore.$reset();
            this.apiStore.setTransport(newTransport);
            this.allStopSearch();
        },

        //Control city checkbox changes
        // eslint-disable-next-line no-unused-vars
        city(newCity, oldCity){                
            let transportValue = document.querySelector("#transport-combobox").value;
            this.apiStore.$reset();
            this.apiStore.setTransport(transportValue);
            this.apiStore.setCity(newCity);
            this.allStopSearch();
        },
    
    
    },

    methods: {
        //Search all stops for specific kind of transport and city for this tansport
        async allStopSearch(){   
            if(this.apiStore.urlCreatorForStops()){
                await this.apiStore.downloadStops();
                this.displayListSignal(true);
            }
        },

        //Signal for displaing list of stops
        displayListSignal(option){
            this.$emit('changeStopsList', option);
        },

    
    },
}

</script>

<style>
.searcher {
    width: 100%;
    height: 150px;
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