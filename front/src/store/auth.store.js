import { defineStore } from "pinia";
import axios from "axios";


export const useAuthStore = defineStore('auth', {

    state: () => ({          //store data about authorization and user
        userData: {},           //include id, usernam, email
        userJwt: "",
        serverMessage: "",
        authStatus: false,      //status told us that user is loged in(true) or looged out(false)
        lastHttpCode: "",
        session: null
    }),

    getters: {
        getUser: (state) => state.userData,
        getAuthStatus: (state) => state.authStatus,
        getHttpCode: (state) => state.lastHttpCode,
        getJwt: (state) => state.userJwt,
    },

    actions: {
        //Registration
        async userSignUp(username, email, password) {
            await axios.post('api/auth/signup', {
                "username": username,
                "email": email,
                "password": password,
                "roles": []
            }).then(response => {
                this.lastHttpCode = response.status;
                console.log(response);
            }).catch(error => {
                if(!(error.code === "ERR_NETWORK")){
                    this.lastHttpCode = error.response.status;
                }else {
                    this.lastHttpCode = 500;
                }console.log(error)
            });
        },

        //Login
        async userSignIn(email, password) {
            await axios.post('api/auth/signin', {
                "username": email,
                "password": password,
            }).then(response => {
                this.authStatus = true;
                this.lastHttpCode = response.status;
                this.userData = response.data;         //returning data from LOGIN backend to userData without parsing
                this.userJwt = response.headers.authorization;
                axios.defaults.headers.common['Authorization'] = response.headers.authorization;
            }).catch(error => {
                if(!(error.code === "ERR_NETWORK")){
                    this.lastHttpCode = error.response.status;
                }else {
                    this.lastHttpCode = 500;
                }console.log(error);
            });
        },

        //Logout
        async userSignOut() {
            await axios.post('api/auth/logout')
                .then(response => {
                    delete axios.defaults.headers.common['Authorization'];
                    this.lastHttpCode = response.status;
                    console.log(response);
                })
                .catch(error => {
                    if(!(error.code === "ERR_NETWORK")){
                        this.lastHttpCode = error.response.status;
                    }else {
                        this.lastHttpCode = 500;
                    } console.log(error)
                });
        },

        //Session
        sessionIntervalStart() {
            this.session = setInterval(() => {
                console.log("int");
            }, 4000);
        },

        sessionIntervalStop() {
            clearInterval(this.session);
        },

    },

});