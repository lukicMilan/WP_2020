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
             @userEdited = "userEdited($event)"></router-view>
        </div>
        <md-snackbar :md-position="'center'" :md-duration="snackbarDuration" :md-active.sync="showSnackbar" md-persistent>
            <span>{{snackbarText}}</span>
            <md-button class="md-primary" @click="showSnackbar = false">Ok</md-button>
        </md-snackbar>
    </div>
</div>
</template>

<script>
export default {
    props: {

    },
    methods: {
        userLoggedIn(user) {
            this.loggedInUser = user;
            this.$cookie.set('loggedInUser', JSON.stringify(user));
            if(user !== null) {
                if(user.userType==="ADMINISTRATOR") {
                    this.$router.push('/usersTable');
                } else  {
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
        userEdited(user) {
        this.userLoggedIn(user)
        this.$router.push('/apartmentTable')
        
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
            }
        },
    
    created() {
      this.loggedInUser = JSON.parse(this.$cookie.get('loggedInUser'));
    },
};
</script>