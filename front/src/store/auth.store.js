import { defineStore } from "pinia";
import axios from "axios";

export const useAuthStore = defineStore('auth', {
    state: () => ({          //store data about user from backend, initial is null
        userData: {
            id: 0,
            email: "",
            updated_at: "",
            isAdmin: false,
        },
        serverMessage: "",
        authStatus: false,      //status told us that user is loged in(true) or looged out(false)
    }),

    getters: {
        getUser: (state) => state.userData,
    },

    actions: {
        //Registration
        async userSignUp(email, password) {
            await axios.post('api/auth/signup', {
                "email": email,
                "password": password,
            }).then(response => {
                console.log(response);
                this.userData = this.parseUserData(response.data);           //returning data from REGISTRATION backend to userData without parsing
                this.serverMessage = response.data.message;        //returning message from backend site
            }).catch(error => console.log(error));
        },

        //Login
        async userSignIn(email, password) {
            await axios.post('api/auth/signin', {
                "email": email,
                "password": password,
            }).then(response => {
                console.log(response);
                this.userData = this.parseUserData(response.data);         //returning data from LOGIN backend to userData with parsing
                this.serverMessage = response.data.message;      //returning message from backend site
                this.authStatus = true;
            }).catch(error => console.log(error));
        },

        //Logout
        async userSignOut() {
            await axios.delete('auth/singout')
                .then(response => {
                console.log(response);
            }).catch(error => console.log(error));
        },

        parseUserData(data) {            //filtr information
            let user = {
                id: data.id,
                email: data.email,
                updated_at: data.updated_at,
                isAdmin: data.isAdmin,
            };

            return user;
        },

        async getAuthenticatedUser() {              //screap information about current user
            const response = await axios.get("user")
                .catch(error => console.log(error));
            if (response.status === 200){
                this.userData = this.parseUserData(response.data);
                this.authStatus = true;
            }
        }

    },

});