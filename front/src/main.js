import { createApp } from "vue"
import { createPinia } from "pinia";
import App from './App.vue'
import router from './router'
import axios from "axios"

axios.defaults.withCredentials = true
// axios.defaults.baseURL = 'http://localhost:3001/'
axios.defaults.baseURL = 'https://jsonplaceholder.typicode.com/'

const pinia = createPinia()
const app = createApp(App)

app.use(pinia)
app.use(router)
app.mount('#app')

