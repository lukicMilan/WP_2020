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
             @reservationCompleted="reservationCompleted($event)"></router-view>
        </div>
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
        }
    },
    
    data: 
        function() {
            return {
                loggedInUser: null,
                selectedApartment: null
            }
        },
    
    created() {
      this.loggedInUser = JSON.parse(this.$cookie.get('loggedInUser'));
    },
};
</script>