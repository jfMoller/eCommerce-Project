import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import SignupView from '../views/SignupView.vue'
import AccountView from '../views/AccountView.vue'
import ProductView from '@/views/ProductView.vue'
import EditAccountView from '../views/EditAccountView.vue'
import ShowAccountOrdersView from '@/views/ShowAccountOrdersView.vue'
import { useAuthenticationStore } from '@/stores/authenticationStore'
import AdminToolsView from '@/views/admin/AdminToolsView.vue'
import HandleProductsView from '@/views/admin/HandleProductsView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue')
    },
    {
      path: '/shop',
      name: 'shop',
      component: () => import('../views/ShopView.vue')
    },
    {
      path: '/product/:productId',
      name: 'productView',
      component: ProductView
    },
    {
      path: '/contact',
      name: 'contact',
      component: () => import('../views/ContactView.vue')
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView
    },
    {
      path: '/signup',
      name: 'signup',
      component: SignupView
    },
    {
      path: '/account',
      name: 'account',
      component: AccountView,
      beforeEnter: (to, from, next) => {
        if (useAuthenticationStore().states.isAuthenticated) {
          next()
        } else {
          next('/login')
        }
      },
      children: [
        {
          path: 'edit',
          name: 'EditAccountView',
          component: EditAccountView
        },
        {
          path: 'orders',
          name: 'ShowAccountOrdersView',
          component: ShowAccountOrdersView
        }
      ]
    },
    {
      path: '/checkout',
      name: 'checkout',
      component: () => import('../views/CheckoutView.vue')
    },
    {
      path: '/admintools',
      name: 'admintools',
      component: AdminToolsView,
      beforeEnter: (to, from, next) => {
        if (
          useAuthenticationStore().states.isAuthenticated &&
          useAuthenticationStore().states.isAdmin
        ) {
          next()
        } else {
          next('/login')
        }
      },
      children: [
        {
          path: 'products',
          name: 'HandleProductsView',
          component: HandleProductsView
        }
      ]
    },
    { path: '/:catchAll(.*)', redirect: '/' }
  ]
})

export default router
