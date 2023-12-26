import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import AboutView from '../views/AboutView.vue'
import LoginView from '../views/LoginView.vue'
import SignupView from '../views/SignupView.vue'
import AccountView from '../views/AccountView.vue'
import ProductView from '@/views/ProductView.vue'
import ContactView from '@/views/ContactView.vue'
import EditAccountView from '../views/EditAccountView.vue'
import ShowAccountOrdersView from '@/views/ShowAccountOrdersView.vue'
import { useAuthenticationStore } from '@/stores/authenticationStore'
import { useShoppingCartStore } from '@/stores/shoppingCartStore'
import AdminToolsView from '@/views/admin/AdminToolsView.vue'
import HandleProductsView from '@/views/admin/HandleProductsView.vue'
import AddProduct from '@/components/admintools/AddProduct.vue'
import EditProduct from '@/components/admintools/EditProduct.vue'
import DeleteProduct from '@/components/admintools/DeleteProduct.vue'
import CheckoutView from '@/views/CheckoutView.vue'

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
      component: AboutView
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
      component: ContactView
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
      component: CheckoutView,
      beforeEnter: (to, from, next) => {
        if (useShoppingCartStore().states.productAmount > 0) {
          next()
        } else {
          next('/shop')
        }
      }
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
          component: HandleProductsView,
          redirect: '/admintools/products/add',
          children: [
            {
              path: 'add',
              name: 'AddProduct',
              component: AddProduct
            },
            {
              path: 'edit',
              name: 'EditProduct',
              component: EditProduct
            },
            {
              path: 'delete',
              name: 'DeleteProduct',
              component: DeleteProduct
            }
          ]
        }
      ]
    },
    { path: '/:catchAll(.*)', redirect: '/' }
  ]
})

export default router
