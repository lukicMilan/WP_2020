<template>
    <div class="usersTable">
        <md-table v-model="searched" md-sort="username" md-sort-order="asc" md-card>
            <md-table-toolbar>
                <div class="md-toolbar-section-start">
                    <h1 class="md-title">Users</h1>
                </div>
                
                <md-field md-clearable class="md-toolbar-section-end">
                    <md-input placeholder="Search..." v-model="searchedWord" @input="searchOnTable" />
                </md-field>
            </md-table-toolbar>

            <md-table-row slot="md-table-row" slot-scope="{ item }">
                <md-table-cell md-label="Username" md-sort-by="username">{{item.username}}</md-table-cell>
                <md-table-cell md-label="First Name" md-sort-by="name">{{item.name}}</md-table-cell>
                <md-table-cell md-label="Last Name" md-sort-by="surname">{{item.surname}}</md-table-cell>
                <md-table-cell md-label="Type" md-sort-by="userType">{{item.userType}}</md-table-cell>
            </md-table-row>
        </md-table>
    </div>
</template>

<script>
import http from '../../http-common'

const toLower = text => {
    return text.toString().toLowerCase()
}

const uniqueElementsBy = (arr, fn) =>
  arr.reduce((acc, v) => {
    if (!acc.some(x => fn(v, x))) acc.push(v);
    return acc;
}, []);

const searchOnTable = (items, term) => {
    let searchedItems = [];
    if (term) {
      searchedItems = searchedItems.concat(items.filter(item => (item.username).includes(term)));
      searchedItems = searchedItems.concat(items.filter(item => toLower(item.name).includes(toLower(term))));
      searchedItems = searchedItems.concat(items.filter(item => toLower(item.surname).includes(toLower(term))));
      searchedItems = searchedItems.concat(items.filter(item => toLower(item.userType).includes(toLower(term))));
      searchedItems = uniqueElementsBy(searchedItems, (a,b) => a.username == b.username);
    }


    return searchedItems
}

export default {
    name: 'UsersTable',
    props: {
        loggedInUser: null
    },
    data: () => ({
        searchedWord: "",
        searched: [],
        users: []
    }),

    mounted() {
        if(this.loggedInUser === null) {
            return;
        }
        if(this.loggedInUser.userType === "ADMINISTRATOR") {
            http.get('user')
            .then(data => {
                this.users = data.data;
                this.searched = data.data;
            });
            return;
        }
        if(this.loggedInUser.userType === "HOST") {
            http.get('reservation/host/' + this.loggedInUser.username)
            .then(data => {
                data.data.forEach(element => {
                    http.get('user/' + element.guestUsername)
                    .then(data1=> {
                        if(!this.users.includes(data1.data)) {
                            this.users.push(data1.data);
                            this.searched.push(data1.data);
                        }
                    });
                });
            })
        }
    },

    methods: {
        searchOnTable() {
            if(this.searchedWord == "") {
                this.searched = this.users;
            } else {
                this.searched = searchOnTable(this.users, this.searchedWord);
            }
        }
    }
}
</script>

<style lang="scss" scoped>
  .md-field {
    max-width: 30   0px;
  }
</style>