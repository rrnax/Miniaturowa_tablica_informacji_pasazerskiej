import axios from "axios";
import { defineStore } from "pinia";
// import axios from "axios";

export const useApiStore = defineStore("api", {

    state: () => ({
        transport: "",
        city: "",
        stopsList: [],
        parsedStopsList: [],
        apiUrl: "",
    }),

    getters: {
        getTransport: (state) => state.transport,
        getCity: (state) => state.city,
        getStopsList: (state => JSON.parse(JSON.stringify(state.parsedStopsList))),
    },

    actions: {

        //Seters
        setTransport(kind){
            this.transport = kind;
        },

        setCity(city){
            this.city = city;
        },

        //Download allow cities
        async downloadCitiesRail(){
            let list = [];
            await axios.get('api/displays/all/trains')
            .then(response => {
                list = response.data;
            }).catch(error => {
                console.log(error);
            })
            return list;
        },

        async downloadCitiesTransport(){
            let list = [];
            await axios.get('api/displays/all/publicTransport')
            .then(response => {
                list = response.data;
            }).catch(error => {
                console.log(error);
            })
            return list;
        },

        //Creat correct api
        useCorrectApi(transporKind, city){
            let strinUrl = "api/"+transporKind+"/"+city;
            this.apiUrl = strinUrl;
        },


        //Stops in initial JSON to parse
        async downloadStops(){
            await axios.get(this.apiUrl+'/displays')
            .then(response => {
                this.stopsList = response.data;
                // console.log(Date.now());
                this.parseList(response.data);
                // console.log(Date.now());
                // console.log(JSON.parse(JSON.stringify(this.stopsList)));
                // eslint-disable-next-line no-unused-vars
            }).catch(error => {
                // console.log(error);
            });
        },

        //Stops parse to intrested array
        async parseList(list){
            await list.map(stop => {
                let exist = false;
                let tempObj = {"name":stop.name,"displayCodes":[stop.displayCode],"subscribed":false,"status":false};
                for(const item of this.parsedStopsList){
                    if(stop.name === item.name){
                        item.displayCodes.push(stop.displayCode);
                        exist = true;
                        break;
                    }
                }
                if(!exist){
                    this.parsedStopsList.push(tempObj);
                }
            })
        }
        
    }
})
