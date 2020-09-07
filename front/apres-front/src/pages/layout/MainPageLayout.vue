<template>
<div>
    <div class="wrapper ">
        <side-bar/>

        <div class="main-panel" :logged-in-user="loggedInUser">
            <!--top-navbar -->
            <!--content-->
            <router-view @userLoggedIn="userLoggedIn($event)"></router-view>
        </div>
    </div>
</div>
</template>

<script>
export default {
    props: {
        loggedInUser: null,
    },
    methods: {
        userLoggedIn(user) {
            alert(JSON.stringify(user));
        }
    }
};
</script>