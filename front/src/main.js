import Vue from "vue";
import App from './App.vue'
import router from './router';
import store from './store';
import axios from "axios";

axios.defaults.withCredentials = true;
axios.defaults.baseURL = 'http://localhost:3001';

new Vue({
    store,
    router,
    render: h => h(App)
}).$mount('#app')

// createApp(App).use(router).mount('#app')
