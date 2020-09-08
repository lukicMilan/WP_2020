<template>
<div>
    <div class="wrapper ">
        <side-bar :logged-in-user="loggedInUser" />

        <div class="main-panel">
            <!--top-navbar -->
            <!--content-->
            <router-view  @userLoggedIn="userLoggedIn($event)"></router-view>
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
            alert("VRATIO " + JSON.stringify(user));
            this.loggedInUser = user;

            alert(JSON.stringify(this.loggedInUser));
        }
    },
    data: 
        function() {
            return {
                loggedInUser: null
            }
        }
    
};
</script>