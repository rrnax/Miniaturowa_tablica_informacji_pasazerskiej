import { createApp, watch } from "vue"
import { createPinia } from "pinia";
import App from './App.vue'
import router from './router'
import axios from "axios"
import {useAuthStore} from "@/store/auth.store";


axios.defaults.baseURL = 'http://localhost:8080/'



const pinia = createPinia()
const app = createApp(App)


if (localStorage.getItem("auth")) {
    pinia.state.value.auth = JSON.parse(localStorage.getItem("auth"));
    // console.log(pinia.state.value.auth);
}

if (localStorage.getItem("user")) {
    pinia.state.value.user = JSON.parse(localStorage.getItem("user"));
    // console.log(pinia.state.value.user);
}


watch(
    pinia.state,
    (state) => {
        localStorage.setItem("auth", JSON.stringify(state.auth));
        localStorage.setItem("user", JSON.stringify(state.user));
        localStorage.setItem("api", JSON.stringify(state.api));
    },{ deep: true }
);

app.use(pinia)
app.use(router)
app.mount('#app')


const authStore = useAuthStore();
if (authStore.getAuthStatus){
    axios.defaults.headers.common['Authorization'] = authStore.getJwt;
    authStore.sessionIntervalStart();
}