<template>
    <div>
        <md-toolbar class="md-primary">
        <md-button class="md-icon-button" @click="showNavigation = true">
            <md-icon>menu</md-icon>
        </md-button>
        <span class="md-title">Bookomat</span>
        <div class="md-toolbar-section-end">
          <md-button class="md-dense md-primary" to = "/login">Log in</md-button>
          <md-button class="md-dense md-raised md-primary" to = "/register">Sign up</md-button>
          <md-button class="md-dense md-raised md-primary" to = "/login" @click="logout()">Logout</md-button>
        </div>
        </md-toolbar>
        <md-drawer :md-active.sync="showNavigation" md-swipeable>
        <md-toolbar class="md-transparent" md-elevation="0">
            <span class="md-title">My App name</span>
        </md-toolbar>

        <md-list>
            <md-list-item  to="/hello">
            <md-icon>move_to_inbox</md-icon>
            <span class="md-list-item-text">Inbox</span>
            </md-list-item>

            <md-list-item to="/table" >
            <md-icon>send</md-icon>
            <span class="md-list-item-text" :items = "this.items" :columns = "this.columns">Table (example)</span>
            </md-list-item>

            <md-list-item>
            <md-icon>delete</md-icon>
            <span class="md-list-item-text">Trash</span>
            </md-list-item>

            <md-list-item>
            <md-icon>error</md-icon>
            <span class="md-list-item-text">Spam</span>
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
        alert(JSON.stringify(this.loggedInUser));
        this.$emit('loggedInUser', null);
      },

      ifLoggedIn: function () {
        if(this.loggedInUser == null) {
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
    })
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