import Vue from 'vue'
import VueRouter from "vue-router"
import App from './App.vue'
import HelloWorld from "@/components/HelloWorld.vue"
import Register from "@/components/Register.vue"
import Table from "@/components/Table.vue"
import Amenity from "@/components/Amenity.vue"
import Apartment from "@/components/Apartment.vue"
import MainPageLayout from "./pages/layout/MainPageLayout.vue"

import VueMaterial from "vue-material";
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default.css'
import VueGeolocation from 'vue-browser-geolocation'


import SideBar from "./components/SidebarPlugin/SideBar"

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
          path: "/amenity",
          name: "Amenity",
          component: Amenity
        },
        {
          path: "/apartment",
          name: "Apartment",
          component: Apartment
        }
        ]
    }
]
});

Vue.config.productionTip = false

Vue.use(VueMaterial);
Vue.component('side-bar', SideBar);

Vue.config.productionTip = false
Vue.use(VueGeolocation)

new Vue({
  router,
  render: h => h(App),
  data: {

  }
}).$mount('#app')
