import axios from "axios";
import { defineStore } from "pinia";

export const useApiStore = defineStore("api", {

    state: () => ({
        transport: "",
        city: "",
        apiUrl: "",
        stopsList: [],
        departuresStop: null,
        departureList: [],
        isDataLoaded: false,
        activeStop: null,
    }),

    getters: {
        getTransport: (state) => state.transport,
        getCity: (state) => state.city,
        getStopsList: (state) => state.stopsList,
        getDepartures: (state) => state.departureList,
        getDeparturesStop: (state) => state.departuresStop,
        getLoadedInfo: (state) => state.isDataLoaded,
        getActiveStop: (state) => state.activeStop,
    },

    actions: {
        //Seters for the state
        setTransport(kind){
            this.transport = kind;
        },

        setCity(city){
            this.city = city;
        },

        setActiveStop(stop){
            this.activeStop = stop;
        },

        setDeparturesStop(stop){
            this.departuresStop = stop;
        },

        async setDepartureList(list){
            this.departureList = list;
        },

        //Creat correct url for api data upload
        useCorrectZtmApi(){
            this.apiUrl = "api/ztm/displays/"+this.city;
        },

        usePkpApi(){
            this.apiUrl = "api/pkp/stops";
        },

        useDeparturesApiZtm(city){
            this.apiUrl = "api/ztm/"+city+"/info/";
        },

        useDeparturesApiPkp(){
            this.apiUrl = "api/pkp/stops/";
        },



        //Crate api url for downloading stops
        urlCreatorForStops(){
            if(this.transport === 'rail'){
                this.usePkpApi();
                return true;
            } else if(this.city !== '') {
                this.useCorrectZtmApi();
                return true;
            } else {
                return false;
            }
        },

        //Download stops for given data of kind and city      
        async downloadStops(){
            this.isDataLoaded = true;
            await axios.get(this.apiUrl)
            .then(response => {
                this.stopsList = response.data;
                // eslint-disable-next-line no-unused-vars
            }).catch(error => {
                // console.log(error);
            });
            this.isDataLoaded = false;
        },

        //Create and save departure list for specific stop
        async makeDepartureList(stop){
            this.isDataLoaded = true;
            let result = [];
            await this.urlCreatorForDepatrures();
            if(this.transport === "ztm"){
                result = await this.downloadAllZtm(stop);
            } else {
                result = await this.downloadAllRail(stop);
            }
            this.departureList = [...result];
            this.isDataLoaded = false;
        },

        //Create and save Api url for download departures
        async urlCreatorForDepatrures(){
            if(this.city === "Warszawa"){
                this.useDeparturesApiZtm("warszawa");
            } else if( this.city === "Gdańsk"){
                this.useDeparturesApiZtm("gdansk");
            } else {
                this.useDeparturesApiPkp();
            }
        },

        //Download departures from ztm's apis 
        async downloadAllZtm(stop){
            let temp = []; 
            let result = [];
            for(let i = 0; i < stop[1].length; i++){
                temp = await this.downloadDeparturesByDisplayCode(stop[1][i]);
                for(let j = 0; j < temp.length; j++){
                    result.push(temp[j]);
                }
            }
            result = this.makeSortedAndFiltred(result);
            return result;
        },

        //Download departures from pkp's apis
        async downloadAllRail(stop){
            let result = [];
            result =  await this.downloadDeparturesByDisplayCode(stop.stop_id);
            result = this.makeSortedAndFiltred(result);
            return result;
        },

        //Download list of departurse using special display code, somtimes one stop has many display codes that we need methods above
        async downloadDeparturesByDisplayCode(code){
            let list;
            await axios.get(this.apiUrl+code)
            .then(response => {
                list = response.data.departures;
            // eslint-disable-next-line no-unused-vars
            }).catch(error => {
                // console.log(error);
            })
            return list;
        },

        //Sort gdansk departures by estimated times and change format of estimated times for all kinds of departures
        makeSortedAndFiltred(list){
            if(this.city === "Gdańsk"){
                list.sort(this.compareTimes);
                for(let i = 0; i < list.length; i++){
                    let temp = new Date(list[i].estimatedTime); 
                    list[i].estimatedTime = temp.getHours() + ":" + (temp.getMinutes()<10?'0':'') + temp.getMinutes();
                }
            } else {
                for(let i = 0; i < list.length; i++){
                    list[i].estimatedTime = list[i].estimatedTime.substring(0,5);
                }
            }
            return list;
        },

        //Convert active stop to departure stop
        converActiveStop(){
            if(this.activeStop.cityName === "Warszawa [ZTM]"){
                this.transport = 'ztm';
                this.city = 'Warszawa';
            }else if(this.activeStop.cityName === "Gdańsk [ZTM]"){
                this.transport = 'ztm';
                this.city = 'Gdańsk';
            } else {
                this.transport = 'rail';
                this.city = '';
            }
            let tempName = this.activeStop.stopName;
            let tempDisplayArray = JSON.parse(JSON.stringify(this.activeStop.stopIds));
            const tempStop = [ tempName, tempDisplayArray];
            this.setDeparturesStop(tempStop);
        },

        updateDepartureList(){
            let tempStop = JSON.parse(JSON.stringify(this.departuresStop));
            this.makeDepartureList(tempStop);
        },

        //Compare method to sort by estimated time of departure 
        compareTimes(a,b){
            let date1 = new Date(a.estimatedTime);
            let date2 = new Date(b.estimatedTime);
            if ( date1 < date2 ){
            return -1;
            }
            if ( date1 > date2 ){
            return 1;
            }
            return 0;
        },

    }
})
