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
        favoriteStops: [],
    }),

    getters: {
        getName: (state) => state.username,
        getEmail: (state) => state.email,
        getPassword: (state) => state.password,
        getFavorites: (state) => state.favoriteStops,
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

        //Operations on favorit list
        async downloadFavoriteStops(){
            await axios.get("api/favorite/stop/getAll/by/user")
            .then( response => {
                this.favoriteStops = response.data;
            }).catch(error => {
                console.log(error);
            })
        },

        async deleteFavoriteStop(id){
            await axios.delete("api/favorite/stop/delete/"+id)
            // eslint-disable-next-line no-unused-vars
            .then(response => {
                // console.log(response);
            }).catch(error => {
                console.log(error);
            })
        },

        async changeStatus(status, id){
            await axios.put("api/favorite/stop/update/"+id, {
                "status": status,
            // eslint-disable-next-line no-unused-vars
            }).then(response => {
                // console.log(response);
            }).catch(error => {
                console.log(error);
            })
        }
    }
});