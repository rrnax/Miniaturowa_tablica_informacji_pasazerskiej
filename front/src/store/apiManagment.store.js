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

        //Download deparetures from stop
        // async downloadDepartures(codeList, kind){
        //     let tempList = [];
        //     let resultList = [];
        //     let url = "";
        //     if(kind === "Gdańsk [ZTM]"){
        //         url = "/api/ztm/gdansk/info/";
        //     } else if(kind === "Warszawa [ZTM]") {
        //         url = "/api/ztm/warszawa/info/";
        //     }
        //     await codeList.forEach(async element => {
        //         tempList = await this.departureRequest(url+element);
        //         tempList.forEach(departure => {
        //             // departure.estimatedTime = new Date(departure.estimatedTime).toLocaleTimeString();
        //             resultList.push(departure);
        //         })
        //     });
        //     this.departureList = resultList;
        // },

        // async departureRequest(url){
        //     let resultList = [];
        //     await axios.get(url)
        //     .then(response => {
        //         resultList = response.data.departures;
        //     }).catch(error => {
        //         console.log(error);
        //     })
        //     return resultList;
        // },

    }
})
