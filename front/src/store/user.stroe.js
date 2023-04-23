import {defineStore} from "pinia";
import axios from "axios";
import {useAuthStore} from "@/store/auth.store";


export const useUserStore = defineStore("user", {

    state: () => ({
        id: "",
        username: "",
        email: "",
        roles: [],
        password: "",
    }),

    getters: {
        getName: (state) => state.username,
        getEmail: (state) => state.email,
        getPassword: (state) => state.password,
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
            console.log(axios.defaults.headers);
            await axios.post("all/updateEmail",{
                "newEmail": newMail
            }).then(response => {
                console.log(response);
                this.email = newMail;
                alert("zmieniono pomyślnie!")
            }).catch(error => {
                console.log(error);
                alert("Nie można było zaktualizować, spróbuj później!")
            })
        },

        //Updating user name
        // async saveNewUserName(newName){
        //     await axios.post("all/updateEmail",{
        //         "newUsername": newName
        //     }).then(response => {
        //         console.log(response);
        //     }).catch(error => {
        //         console.log(error);
        //     })
        // },

        //Update password
        async saveNewPassword(newPassword){
            await axios.post("all/updatePassword", {
                "newPassword": newPassword
            }).then(response => {
                console.log(response);
                this.password = newPassword;
                alert("zmieniono pomyślnie!");
            }).catch(error => {
                console.log(error);
                alert("Nie można było zaktualizować, spróbuj później!");
            })
        },

        async deleteUser(){
            const authStore = useAuthStore();
            await axios.delete("all/deleteUser")
                .then(response => {
                    console.log(response);
                    authStore.$reset();
                    alert("Usunięto pomyślnie");
                }).catch(error => {
                    console.log(error);
                    alert("Nie udało się usunąć, spróbuj pózniej!");
                })
        }
    }
});