import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from "@/views/LoginView.vue";
import RegistrationView from "@/views/RegistrationView.vue";
import {useAuthStore} from "@/store/auth.store";
import AccountView from "@/views/AccountView";
import AccountConfirmationView from "@/views/AccountConfirmationView";

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    meta: { requiredAuth: false },
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
    meta: { requiredAuth: false },
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

router.beforeEach(async (to) => {
  const authStore = useAuthStore()
  if (to.meta.requiredAuth && !authStore.authStatus){
    return '/login'
  }
})

export default router
