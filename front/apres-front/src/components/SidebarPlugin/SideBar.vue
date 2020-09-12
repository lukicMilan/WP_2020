<template>
    <div>
        <md-toolbar class="md-primary">
        <md-button class="md-icon-button" @click="showNavigation = true">
            <md-icon>menu</md-icon>
        </md-button>
        <span class="md-title">Bookomat</span>
        <div class="md-toolbar-section-end">
          <md-button v-if="!loggedInUser" class="md-dense md-primary" to = "/login">Log in</md-button>
          <md-button v-if="!loggedInUser" class="md-dense md-raised md-primary" to = "/register">Sign up</md-button>
          <md-button v-if="loggedInUser" class="md-dense md-raised md-primary" to = "/login" @click.native="logout">Logout</md-button>
        </div>
        </md-toolbar>
        <md-drawer :md-active.sync="showNavigation" md-swipeable>
        <md-toolbar class="md-transparent" md-elevation="0">
            <span class="md-title">Menu</span>
        </md-toolbar>

        <md-list>
            <md-list-item to="apartmentTable">
            <md-icon>analytics</md-icon>
            <span class="md-list-item-text">Apartments</span>
            </md-list-item>

            <md-list-item v-if="loggedAdministrator" to="/usersTable">
            <md-icon>group</md-icon>
            <span class="md-list-item-text">Users Table</span>
            </md-list-item>

            <md-list-item v-if="loggedHost" to="apartment">
            <md-icon>add</md-icon>
            <span class="md-list-item-text">Add Apartment</span>
            </md-list-item>

            <md-list-item to="apartmentTable">
            <md-icon>analytics</md-icon>
            <span class="md-list-item-text">Show Apartments</span>
            </md-list-item>

            <md-list-item v-if="administrator" to="amenitiesTable">
            <md-icon>dns</md-icon>
            <span class="md-list-item-text">Show Amenities</span>
            </md-list-item>
        </md-list>
        </md-drawer>
    </div>
</template>

<script>
export default {
    name: "side-bar",
    props: {
      loggedInUser: null,
    },
    methods: {
      logout: function() {
        this.$emit('userLoggedIn', null);
      },

      ifLoggedIn: function () {
        if(this.loggedInUser === null) {
          return false;
        } else {
          return true;
        }
      }
    },
    data: () => ({
      showNavigation: false,
      showSidepanel: false,
      items: [{
            'id':'1',
            'title': 'hello',
            'description': 'ok ok',
            'created_date': '2018-09-09'
        },
        {
            'id':'2',
            'title': 'hello 2',
            'description': 'ok ok 2',
            'created_date': '2018-10-09'
        }],
        columns: ['id', 'title', 'description', 'created_date']
    }),
    computed: {
      loggedAdministrator: function() {
        if(this.loggedInUser===null) {
          return false;
        } else if(this.loggedInUser.userType === "ADMINISTRATOR" || this.loggedInUser.userType === "HOST") {
          return true;
        }
        return false;
      },
      administrator: function() {
        if(this.loggedInUser===null) {
          return false;
        } else if(this.loggedInUser.userType === "ADMINISTRATOR") {
          return true;
        }
        return false;
      },
      loggedHost: function() {
        if(this.loggedInUser===null) {
          return false;
        } else if(this.loggedInUser.userType === "HOST") {
          return true;
        }
        return false;
      }
    }
}
</script>

<style lang="scss" scoped>
  .page-container {
    min-height: 300px;
    overflow: hidden;
    position: relative;
    border: 1px solid rgba(#000, .12);
  }

   // Demo purposes only
  .md-drawer {
    width: 230px;
    max-width: calc(100vw - 125px);
  }

  .md-content {
    padding: 16px;
  }
  
</style>