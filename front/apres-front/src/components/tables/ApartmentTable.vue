<template>
  <div class = "apartmentTable">
    <md-table v-model="searched" md-sort="name" md-sort-order="asc" md-card md-fixed-header>
      <md-table-toolbar>
        <div class="md-toolbar-section-start">
          <h1 class="md-title">Apartments</h1>
        </div>

        <md-field md-clearable class="md-toolbar-section-end">
          <md-input placeholder="Search..." v-model="search" @input="searchOnTable" />
        </md-field>
      </md-table-toolbar>

      <md-table-row slot="md-table-row" slot-scope="{ item }">
        <md-table-cell md-label="ID" md-sort-by="id" md-numeric>{{ item.id }}</md-table-cell>
        <md-table-cell md-label="Type" md-sort-by="type">{{ item.type }}</md-table-cell>
        <md-table-cell md-label="RoomNumber" md-sort-by="roomNumber">{{ item.roomNumber }}</md-table-cell>
        <md-table-cell md-label="GuestNumber" md-sort-by="guestNumber">{{ item.guestNumber }}</md-table-cell>
        <md-table-cell md-label="EntryTime" md-sort-by="entryTime">{{ item.entryTime }}</md-table-cell>
        <md-table-cell md-label="LeaveTime" md-sort-by="leaveTime">{{ item.leaveTime }}</md-table-cell>
        <md-table-cell md-label="Amenities" md-sort-by="amenities">{{ item.amenities }}</md-table-cell>
      </md-table-row>
    </md-table>
    <md-button v-if="addButton" class="md-dense md-raised md-primary" to="/apartment">Add Apartment</md-button>
  </div>
</template>

<script>
  import axios from 'axios'

  const toLower = text => {
    return text.toString().toLowerCase()
  }

  const searchOnTable = (items, term) => {
    let searchedItems = [];
    if (term) {
      searchedItems = searchedItems.concat(items.filter(item => (item.id).includes(term)));
      searchedItems = searchedItems.concat(items.filter(item => toLower(item.type).includes(toLower(term))));
      searchedItems = searchedItems.concat(items.filter(item => (item.rootNumber).includes(term)));
      searchedItems = searchedItems.concat(items.filter(item => (item.guestNumber).includes(term)));
      searchedItems = searchedItems.concat(items.filter(item => (item.entryTime).includes(term)));
      searchedItems = searchedItems.concat(items.filter(item => (item.leaveTime).includes(term)));
      
      searchedItems = uniqueElementsBy(searchedItems, (a,b) => a.id === b.id);
    }

    return searchedItems
  }

  const uniqueElementsBy = (arr, fn) =>
  arr.reduce((acc, v) => {
    if (!acc.some(x => fn(v, x))) acc.push(v);
    return acc;
  }, []);

  export default {
    name: 'ApartmentTable',
    data: () => ({
      search: null,
      searched: [],
      apartments: []
    }),
    props: {
      loggedInUser: null,
    },
    methods: {
      searchOnTable () {
        this.searched = searchOnTable(this.apartments, this.search)
      }
    },
    mounted () {
      if(this.loggedInUser === null) {
        axios.get('http://localhost:8080/PocetniREST/rest/apartment/active')
          .then(data => {
            this.apartments = data.data;
            this.searched = data.data;
          });
        return;
      } 
      if (this.loggedInUser.userType === "GUEST") {
        axios.get('http://localhost:8080/PocetniREST/rest/apartment/active')
          .then(data => {
            this.apartments = data.data;
            this.searched = data.data;
          });
        return;
      }
      if (this.loggedInUser.userType === "HOST") {
        axios.get('http://localhost:8080/PocetniREST/rest/apartment/'+this.loggedInUser.username)
          .then(data => {
            this.apartments = data.data;
            this.searched = data.data;
          });
        return;
      }
      if(this.loggedInUser.userType === "ADMINISTRATOR")
       axios.get('http://localhost:8080/PocetniREST/rest/apartment')
            .then(data => { 
              this.apartments = data.data
              this.searched = data.data})
            .catch(error => {
                console.log(error) 
            });
      console.log(this.apartments)
    },
    computed: {
      addButton: function() {
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
  .md-field {
    max-width: 300px;
  }
</style>