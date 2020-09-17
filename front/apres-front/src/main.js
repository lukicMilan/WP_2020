import Vue from 'vue'
import VueRouter from "vue-router"
import App from './App.vue'

import HelloWorld from "@/components/HelloWorld.vue"
import Register from "@/components/Register.vue"
import Amenity from "@/components/Amenity.vue"
import Apartment from "@/components/Apartment.vue"
import ApartmentTable from "@/components/tables/ApartmentTable.vue"
import AmenitiesTable from "@/components/tables/AmenitiesTable.vue"
import MainPageLayout from "./pages/layout/MainPageLayout.vue"

import VueMaterial from "vue-material";
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default.css'

import SideBar from "./components/SidebarPlugin/SideBar"
import LoginPage from "./pages/LoginPage"
import UsersTable from "./components/tables/UsersTable"
import CreateReservation from "./components/CreateReservation"
import ReservationTable from "./components/tables/ReservationTable"
import ApartmentComment from "./components/ApartmentComment"
import FilterComponent from "./components/FilterComponent"

import VCalendar from 'v-calendar';

var VueCookie = require('vue-cookie');

Vue.use(VueRouter);
Vue.use(VueCookie);
Vue.use(VCalendar);

import { VueStars } from "vue-stars"
Vue.component("vue-stars", VueStars)


const router = new VueRouter({
  routes: [
    {
        path: "/",
        component: MainPageLayout,
        redirect: "/apartmentTable",
        children: [{
          path: "/hello",
          name: "Hello World",
          component: HelloWorld
        },
        {
          path: "/reservationTable",
          name: "Reservation Table",
          component: ReservationTable
        },
        {
          path: "/reservation",
          name: "Reservation",
          component: CreateReservation,
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
          path: "/login",
          name: "Login",
          component: LoginPage
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
        },
        {
          path: "/apartmentTable",
          name: "ApartmentTable",
          component: ApartmentTable
        },
        {
          path: "/amenitiesTable",
          name: "AmenitiesTable", 
          component: AmenitiesTable
        },
        {
          path: "/usersTable",
          name: "UsersTable",
          component: UsersTable
        },
        {
          path: "/apartmentComment",
          name: "ApartmentComment",
          component: ApartmentComment
        },
        {
          path: "/filterComponent",
          name: "FilterComponent",
          component: FilterComponent
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
