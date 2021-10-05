import { createRouter, createWebHashHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Register from '../views/Register.vue'
import Login from '../views/Login.vue'

const routes = [{
        path: '/',
        name: 'Home',
        component: Home
    }, {
        path: '/login',
        name: "login",
        component: Login
    }, {
        path: '/register',
        name: "register",
        component: Register
    }

]

const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router