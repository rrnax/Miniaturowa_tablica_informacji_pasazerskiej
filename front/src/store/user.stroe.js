import {defineStore} from "pinia";
import axios from "axios";
import {useAuthStore} from "@/store/auth.store";
import { useApiStore } from "./apiManagment.store";


export const useUserStore = defineStore("user", {

    state: () => ({
        id: "",
        username: "",
        email: "",
        roles: [],
        password: "",
        favoriteStops: null,
        isSomeActive: false,
        isLoaded: true,
        deviceStyle: "",
    }),

    getters: {
        getName: (state) => state.username,
        getEmail: (state) => state.email,
        getPassword: (state) => state.password,
        getFavorites: (state) => state.favoriteStops,
        getDeviceStyle: (state) => state.deviceStyle,
        getIsActive: (state) => state.isSomeActive,
    },

    actions: {
        //Seting state in loging
        setUserState(userData, userPass){
          this.id = userData.id;
          this.username = userData.username;
          this.email = userData.email;
          this.roles = userData.roles.map((x) => x);
          this.password = userPass;
        },

        //Updating email
        async saveNewEmail(newMail){
            await axios.put("api/user/all/update/email",{
                "newEmail": newMail
                // eslint-disable-next-line no-unused-vars
            }).then(response => {
                // console.log(response);
                this.email = newMail;
                alert("zmieniono pomyślnie!")
                // eslint-disable-next-line no-unused-vars
            }).catch(error => {
                // console.log(error);
                alert("Nie można było zaktualizować, spróbuj później!")
            })
        },

        //Updating user name
        async saveNewUserName(newName){
            await axios.put("api/user/all/update/username",{
                "newUsername": newName
                // eslint-disable-next-line no-unused-vars
            }).then(response => {
                // console.log(response);
                this.username = newName;
                alert("Zmieniono pomyślnie!");
                // eslint-disable-next-line no-unused-vars
            }).catch(error => {
                // console.log(error);
                alert("Nie można było zaktualizować, spróbuj później!")
            })
        },

        //Update password
        async saveNewPassword(newPassword){
            await axios.put("api/user/all/update/password", {
                "newPassword": newPassword
                // eslint-disable-next-line no-unused-vars
            }).then(response => {
                // console.log(response);
                this.password = newPassword;
                alert("Zmieniono pomyślnie!");
                // eslint-disable-next-line no-unused-vars
            }).catch(error => {
                // console.log(error);
                alert("Nie można było zaktualizować, spróbuj później!");
            })
        },


        //Remove current user account
        async deleteUser(){
            const authStore = useAuthStore();
            await axios.delete("api/user/all/delete/user")
                .then(response => {
                    console.log(response);
                    authStore.$reset();
                    alert("Usunięto pomyślnie");
                }).catch(error => {
                    console.log(error);
                    alert("Nie udało się usunąć, spróbuj pózniej!");
                })
        },

        //Adding new stop form ztm to favorit list
        async addFavoriteStopInZtm(stop){
            const apiStore = useApiStore();
            await axios.post("api/favorite/stop/add",{
                "cityName": apiStore.getCity+" [ZTM]",
                "stopName": stop[0],
                "stopIds": stop[1],
                "status": false,
            // eslint-disable-next-line no-unused-vars
            }).then(response => {
                // console.log(response);
            }).catch(error => {
                console.log(error);
            })
            await this.downloadFavoriteStops();
        },

        //Adding new stop from rail to favorit list
        async addFavoriteStopInRail(stop){
            await axios.post("api/favorite/stop/add",{
                "cityName": "Kolej",
                "stopName": stop.name,
                "stopIds": [ stop.stop_id ],
                "status": false,
            // eslint-disable-next-line no-unused-vars
            }).then(response => {
                // console.log(response);
            }).catch(error => {
                console.log(error);
            })
            await this.downloadFavoriteStops();
        },

        //Download list of favorite stops for that user and set that one wich is active also sort them
        async downloadFavoriteStops(){
            await axios.get("api/favorite/stop/getAll/by/user")
            .then( response => {
                this.favoriteStops = response.data;
            }).catch(error => {
                console.log(error);
            })
            this.favoriteStops.sort(this.sortStopsByName);
            this.findActiveStop();
        },

        //Find active stop
        findActiveStop(){
            let exist = false;
            const apiStore = useApiStore();
            this.favoriteStops.map((stop) => {
                if(stop.status === true){
                    apiStore.setActiveStop(stop);
                    this.isSomeActive = true;
                    exist =  true;
                }
            });
            if(!exist){
                this.isSomeActive = false;
                apiStore.setActiveStop({
                    stopName: "Nie wybrano przystanku"
                });
            }
        },

        //Delete selected it from favorites
        async deleteFavoriteStop(stop){
            await axios.delete("api/favorite/stop/delete/"+stop.id)
            // eslint-disable-next-line no-unused-vars
            .then(response => {
                // console.log(response);
            }).catch(error => {
                console.log(error);
            })
            setTimeout(this.downloadFavoriteStops, 200);
        },

        //Set new stop to active status
        async activeNewStop(stop){
                await this.changeStatus(true, stop.id);
                this.downloadFavoriteStops();
        },

        //Sending correct statuses
        // async setCorrectStatuses(stop){
        //     this.favoriteStops.forEach(async (element) => {
        //         if(element.status === true){
        //             await this.changeStatus(false, element.id);
        //         } else if(element.id === stop.id){
        //             await this.changeStatus(true, element.id);
        //         }
        //     });
        // },

        //Desactive stop
        async turnOffActiveStop(stop){
            const apiStore = useApiStore();
            await this.changeStatus(true, stop.id);
            this.downloadFavoriteStops();
            apiStore.setActiveStop({
                stopName: "Nie wybrano przystanku"
            });
        },


        //Change status of selected favorite stop
        async changeStatus(status, id){
            await axios.put("api/favorite/stop/update/"+id, {
                "status": status,
            // eslint-disable-next-line no-unused-vars
            }).then(response => {
                // console.log(response);
            }).catch(error => {
                console.log(error);
            })
        },

        //Download Configuration device
        async downloadStyle(){
            await axios.get("api/user/all/get/app/style")
            .then(response => {
                this.deviceStyle = response.data.appStyle;
                this.setStyle(this.deviceStyle);
            }).catch(error =>{
                console.log(error);
            })
        },

        //Download Configuration device
        async updateStyle(style){
            await axios.put("api/user/all/update/app/style", {
                "newAppStyle": style
            })
            // eslint-disable-next-line no-unused-vars
            .then(response => {
                // console.log(response.data);
            }).catch(error =>{
                console.log(error);
            })
            await this.downloadStyle();
        },

        setStyle(style){
            switch(style){
              case 'retro':
                document.documentElement.style.setProperty('--backcolor', '#070606');
                document.documentElement.style.setProperty('--fontcolor', '#ff6701');
                document.documentElement.style.setProperty('--containercolor',"#3f3f3f");
                document.documentElement.style.setProperty('--bordercolor',"#ffffff");
                break;
              case 'basic':
                document.documentElement.style.setProperty('--backcolor', '#3b4bfd');
                document.documentElement.style.setProperty('--fontcolor', '#ffffff');
                document.documentElement.style.setProperty('--containercolor',"#3b4bfd");
                document.documentElement.style.setProperty('--bordercolor',"#ffffff");
                break;
              case 'contrast':
                document.documentElement.style.setProperty('--backcolor', '#000000');
                document.documentElement.style.setProperty('--fontcolor', '#6ef500');
                document.documentElement.style.setProperty('--containercolor',"#000000");
                document.documentElement.style.setProperty('--bordercolor',"#ffffff");
                break;
              case 'dracula':
                document.documentElement.style.setProperty('--backcolor', '#242424');
                document.documentElement.style.setProperty('--fontcolor', '#ebebeb');
                document.documentElement.style.setProperty('--containercolor',"#4e4949");
                document.documentElement.style.setProperty('--bordercolor',"#242424");
                break;
              default:
                break;
            }
        },

        //Compare function to sort by name of stops
        sortStopsByName( a, b ) {
            if ( a.stopName < b.stopName ){
                return -1;
            }
            if ( a.stopName > b.stopName ){
                return 1;
            }
            return 0;
        },
    }
});