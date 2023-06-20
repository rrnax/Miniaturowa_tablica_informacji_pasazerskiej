import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from "@/views/LoginView.vue";
import RegistrationView from "@/views/RegistrationView.vue";
import {useAuthStore} from "@/store/auth.store";
import AccountView from "@/views/AccountView";
import AccountConfirmationView from "@/views/AccountConfirmationView";
import DeviceView from "@/views/DeviceView";
import RanksView from "@/views/RanksView";
import WelcomePage from "@/views/WelcomePage";
import StopDeparturesView from "@/views/StopDeparturesView";

const routes = [
  {
    path: '/',
    name: 'welcome',
    component: WelcomePage,
    meta: { requiredAuth: false },
  },
  {
    path: '/stations',
    name: 'stations',
    component: HomeView,
    meta: { requiredAuth: true },
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView,
    meta: { requiredAuth: false },
  },
  {
    path: '/registration',
    name: 'registration',
    component: RegistrationView,
    meta: { requiredAuth: false },
  },
  {
    path: '/account-confirmation',
    name: 'account-confirmation',
    component: AccountConfirmationView,
    meta: { requiredAuth: false },
  },
  {
    path: '/account',
    name: 'account',
    component: AccountView,
    meta: { requiredAuth: true },
  },
  {
    path: '/device',
    name: 'device',
    component: DeviceView,
    meta: { requiredAuth: true },
  },
  {
    path: '/ranks',
    name: 'ranks',
    component: RanksView,
    meta: { requiredAuth: true },
  },
  {
    path: '/departures',
    name: 'departures',
    component: StopDeparturesView,
    meta: { requiredAuth: true },
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

router.beforeEach(async (to) => {
  const authStore = useAuthStore()
  if (to.meta.requiredAuth && !authStore.authStatus){
    return '/';
  }
})

export default router
