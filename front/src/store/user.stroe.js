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
        }
    }
});