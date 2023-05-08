import axios from "axios";
import { defineStore } from "pinia";
// import axios from "axios";

export const useApiStore = defineStore("api", {

    state: () => ({
        transport: "",
        city: "",
        stopsList: null,
        apiUrl: "",
    }),

    getters: {
        getTransport: (state) => state.transport,
        getCity: (state) => state.city,
        getStopsList: (state => JSON.parse(JSON.stringify(state.stopsList))),
    },

    actions: {

        //Seters
        setTransport(kind){
            this.transport = kind;
        },

        setCity(city){
            this.city = city;
        },

        //Creat correct api
        useCorrectApi(transporKind, city){
            let strinUrl = "api/"+transporKind+"/"+city;
            this.apiUrl = strinUrl;
        },

        async downloadStops(){
            await axios.get(this.apiUrl+'/displays')
            .then(response => {
                this.stopsList = response.data;
                // console.log(JSON.parse(JSON.stringify(this.stopsList)));
                // eslint-disable-next-line no-unused-vars
            }).catch(error => {
                // console.log(error);
            });
        },

        
    }
})
