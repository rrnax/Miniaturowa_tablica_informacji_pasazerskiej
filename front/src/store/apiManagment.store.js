import axios from "axios";
import { defineStore } from "pinia";

export const useApiStore = defineStore("api", {

    state: () => ({
        apiUrl: "",
        transport: "",
        city: "",
        stopsList: [],
        styleDevice: "",
        departureList: [],
        tempDeparture: null,
        activeStop: null,
    }),

    getters: {
        getTransport: (state) => state.transport,
        getCity: (state) => state.city,
        getDeviceStyle: (state) => state.styleDevice,
        getStopsList: (state) => state.stopsList,
        getActiveStop: (state) => state.activeStop,
        getDepartures: (state) => state.departureList,
        getTempDeparture: (state) => state.tempDeparture,
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

        setActiveStop(stop){
            this.activeStop = stop;
            this.activeStop.status = true;
        },

        setDepartureList(list){
            this.departureList = list;
        },

        //Creat correct api
        useCorrectApi(transporKind, city){
            let stringUrl = "api/"+transporKind+"/displays/"+city;
            this.apiUrl = stringUrl;
        },

        usePkpApi(){
            this.apiUrl = "api/pkp/stops";
        },

        useDepartureApiZtm(city){
            let stringUrl = "api/ztm/"+city+"/info/";
            this.apiUrl = stringUrl;
        },

        useDepartureApiPkp(){
            let stringUrl = "api/pkp/stops/";
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
        downloadConfiguration(str){
            this.styleDevice = str;
        },

        //Stops in initial JSON to parse
        async downloadStops(){
            await axios.get(this.apiUrl)
            .then(response => {
                this.stopsList = response.data;
                // eslint-disable-next-line no-unused-vars
            }).catch(error => {
                // console.log(error);
            });
        },

        async downloadDeparturesByDisplayCode(code){
            await axios.get(this.apiUrl+code)
            .then(response => {
                this.tempDeparture = response.data;
            // eslint-disable-next-line no-unused-vars
            }).catch(error => {
                console.log(error);
            })
        }

    }
})
