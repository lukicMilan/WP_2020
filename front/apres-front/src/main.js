import Vue from 'vue'
import VueRouter from "vue-router"
import App from './App.vue'

import HelloWorld from "@/components/HelloWorld.vue"
import Register from "@/components/Register.vue"
import Table from "@/components/Table.vue"
import MainPageLayout from "./pages/layout/MainPageLayout.vue"

import VueMaterial from "vue-material";
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default.css'

import SideBar from "./components/SidebarPlugin/SideBar"
import LoginPage from "./pages/LoginPage"

Vue.use(VueRouter);

const router = new VueRouter({
  routes: [
    {
        path: "/",
        component: MainPageLayout,
        children: [{
          path: "/hello",
          name: "Hello World",
          component: HelloWorld
        },
        {
          path: "/home",
          name: "Home",
        },
        {
          path: "/register",
          name: "Register",
          component: Register
        },
        {
          path: "/table",
          name: "Table",
          component: Table
        },
        {
          path: "/login",
          name: "Login",
          component: LoginPage
        }
        ]
    }
]
});

Vue.config.productionTip = false

Vue.use(VueMaterial);
Vue.component('side-bar', SideBar);

new Vue({
  router,
  render: h => h(App),
  data: {

  }
}).$mount('#app')
