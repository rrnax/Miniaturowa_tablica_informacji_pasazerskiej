import { defineStore } from "pinia";
import axios from "axios";
import {useUserStore} from "@/store/user.stroe";
import { useApiStore } from "./apiManagment.store";


export const useAuthStore = defineStore('auth', {

    state: () => ({          //store data about authorization and user
        userJwt: "",
        serverMessage: "",
        authStatus: false,      //status told us that user is loged in(true) or looged out(false)
        lastHttpCode: "",
        session: null
    }),

    getters: {
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
                "roles": ["user"]
            }).then(response => {
                this.lastHttpCode = response.status;
                // console.log(response);
            }).catch(error => {
                if(!(error.code === "ERR_NETWORK")){
                    this.lastHttpCode = error.response.status;
                }else {
                    this.lastHttpCode = 500;
                }
                // console.log(error);
            });
        },

        //Login
        async userSignIn(email, password) {
            const userStore = useUserStore();
            await axios.post('api/auth/signin', {
                "username": email,
                "password": password,
            }).then(response => {
                this.authStatus = true;
                this.lastHttpCode = response.status;
                userStore.setUserState(response.data, password);         //returning data from LOGIN backend to userData without parsing
                this.userJwt = response.headers.authorization;
                axios.defaults.headers.common['Authorization'] = response.headers.authorization;
                console.log(response);
            }).catch(error => {
                if(!(error.code === "ERR_NETWORK")){
                    this.lastHttpCode = error.response.status;
                }else {
                    this.lastHttpCode = 500;
                }
                // console.log(error);
            });
        },

        //Logout
        async userSignOut() {
            const userStore = useUserStore();
            const apiStore = useApiStore();
            await axios.post('api/auth/logout')
                .then(response => {
                    delete axios.defaults.headers.common['Authorization'];
                    this.lastHttpCode = response.status;
                    userStore.$reset();
                    apiStore.$reset();
                    // console.log(response);
                })
                .catch(error => {
                    if(!(error.code === "ERR_NETWORK")){
                        this.lastHttpCode = error.response.status;
                    }else {
                        this.lastHttpCode = 500;
                    }
                    // console.log(error)
                });
        },

        //Account Confirmation
        async confirmAccount(token) {
            let confirmUrl = "api/auth/confirm-account?token=" + token;
            await axios.get(confirmUrl)
                // eslint-disable-next-line no-unused-vars
                .then(response => {
                    // console.log(response);
                })
                // eslint-disable-next-line no-unused-vars
                .catch(error => {
                    // console.log(error);
                })
        },

        //Session
        sessionIntervalStart() {
            const userStore = useUserStore();
            this.session = setInterval(() => {
                this.userSignIn(userStore.getEmail, userStore.getPassword);
            }, 1800000);
        },

        sessionIntervalStop() {
            clearInterval(this.session);
        },

    },

});