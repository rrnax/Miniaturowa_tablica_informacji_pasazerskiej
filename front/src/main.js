import { createApp } from "vue"
import { createPinia } from "pinia";
import App from './App.vue'
import router from './router'
import axios from "axios"

axios.defaults.baseURL = 'http://localhost:8080/'

const pinia = createPinia()
const app = createApp(App)

app.use(pinia)
app.use(router)
app.mount('#app')

