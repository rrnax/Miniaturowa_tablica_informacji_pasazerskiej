import axios from "axios";
import { defineStore } from "pinia";

export const useApiStore = defineStore("api", {

    state: () => ({
        apiUrl: "",
        transport: "",
        city: "",
        stopsList: [],
        parsedStopsList: [],
        styleDevice: "",
        activeStop: [],
    }),

    getters: {
        getTransport: (state) => state.transport,
        getCity: (state) => state.city,
        getDeviceStyle: (state) => state.styleDevice,
        getStopsList: (state) => state.parsedStopsList,
        getActiveStop: (state) => state.activeStop,
    },

    actions: {
        //Seters
        setTransport(kind){
            this.transport = kind;
        },

        setCity(city){
            this.city = city;
        },
        
        setStyle(theme){
            this.styleDevice = theme;
        },

        //Creat correct api
        useCorrectApi(transporKind, city){
            let stringUrl = "api/"+transporKind+"/"+city;
            this.apiUrl = stringUrl;
        },

        //Download allow cities for rail and transport separatly
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
        
        //Download Configuration device
        downloadConfiguration(){
            this.styleDevice = String("retro");
        },

        //Stops in initial JSON to parse
        async downloadStops(){
            await axios.get(this.apiUrl+'/displays')
            .then(response => {
                this.stopsList = response.data;
                this.parseList(response.data);
                // eslint-disable-next-line no-unused-vars
            }).catch(error => {
                // console.log(error);
            });
        },

        //Download deparetures from stop
        async downloadDepartures(displayCode){
            await axios.get(this.apiUrl+'/info/'+displayCode)
            .then(response => {
                this.activeStop = response.data;
            }).catch(error => {
                console.log(error);
            })
        },

        //Stops parse to intrested array
        async parseList(list){
            await list.map(stop => {
                let exist = false;
                let tempObj = {"name":stop.name,"displayCodes":[stop.displayCode],"status":false};
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
        },
    }
})
