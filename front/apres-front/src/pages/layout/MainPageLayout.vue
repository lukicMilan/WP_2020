<template>
<div>
    <div class="wrapper ">
        <side-bar :logged-in-user="loggedInUser" @userLoggedIn="userLoggedIn($event)"/>

        <div class="main-panel">
            <!--top-navbar -->
            <!--content-->
            <router-view :logged-in-user="loggedInUser" :selected-apartment="selectedApartment"
             @userLoggedIn="userLoggedIn($event)" 
             @activateReservation="activateReservation($event)"
             @reservationCompleted="reservationCompleted($event)"
             @globalMessage="showGlobalMessage($event)"
             @openGallery="openGallery($event)"></router-view>
        </div>
        <md-snackbar :md-position="'center'" :md-duration="snackbarDuration" :md-active.sync="showSnackbar" md-persistent>
            <span>{{snackbarText}}</span>
            <md-button class="md-primary" @click="showSnackbar = false">Ok</md-button>
        </md-snackbar>

         
        <LightBox
            ref="lightBox"
            :media="media"
            :show-light-box="false"
            :show-caption="false"
        />   
    </div>
</div>
</template>

<script>
import LightBox from "vue-it-bigger"
import('vue-it-bigger/dist/vue-it-bigger.min.css') 

export default {
    components: {
        LightBox
    },
    props: {

    },
    methods: {
        userLoggedIn(user) {
            this.loggedInUser = user;
            this.$cookie.set('loggedInUser', JSON.stringify(user));
            if(user !== null) {
                if(user.userType==="ADMINISTRATOR") {
                    this.$router.push('/usersTable');
                } else if(user.userType==="GUEST") {
                    this.$router.push('/apartmentTable');
                }
            }
        }, 
        activateReservation(selectedApartment) {
            this.selectedApartment = selectedApartment;
            this.$router.push('/reservation');
        },
        reservationCompleted() {
            this.selectedApartment = null;
            //TODO reservation preview
        },
        showGlobalMessage(message) {
            this.snackbarText = message;
            this.showSnackbar = true;
        },
        openGallery(params) {
            this.media = params.media;
        }
    },
    
    data: 
        function() {
            return {
                loggedInUser: null,
                selectedApartment: null,
                snackbarDuration: 4000,
                showSnackbar: false,
                snackbarText: "",
                media: [],
            }
        },
    
    created() {
      this.loggedInUser = JSON.parse(this.$cookie.get('loggedInUser'));
    },
};
</script>