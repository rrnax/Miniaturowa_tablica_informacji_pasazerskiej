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
    console.log(pinia.state.value.auth);
}


watch(
    pinia.state,
    (state) => {
        localStorage.setItem("auth", JSON.stringify(state.auth));
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