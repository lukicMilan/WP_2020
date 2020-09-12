<template>
  <div class = "amenitiesTable">

    <md-dialog :md-active.sync="showDialog">
        <Amenity :changeType="this.editType" :changeName="this.editName" :isEdit="this.isEdit" />
    </md-dialog>

    <md-table v-model="searched" md-sort="name" md-sort-order="asc" md-card md-fixed-header>
      <md-table-toolbar>
        <div class="md-toolbar-section-start">
          <h1 class="md-title">Amenities</h1>
        </div>

        <md-field md-clearable class="md-toolbar-section-end">
          <md-input placeholder="Search" v-model="searchedWord" @input="searchOnTable" />
        </md-field>        
      </md-table-toolbar>
      <md-table-row slot="md-table-row" slot-scope="{ item }">
        <md-table-cell md-label="ID" md-sort-by="id" md-numeric>{{ item.id }}</md-table-cell>
        <md-table-cell md-label="Name" md-sort-by="name">{{ item.name }}</md-table-cell>
        <md-table-cell md-label="Type" md-sort-by="type">{{ item.type }}</md-table-cell>
        <md-table-cell md-label="Actions" >
          <md-button class="md-dense md-raised md-default" @click=" isEditFunction(item)">Edit</md-button>
          <md-button class="md-dense md-raised md-accent" @click = "removeAmenity(item)">Delete</md-button>
        </md-table-cell>
      </md-table-row>
    </md-table>
    <md-button class="md-dense md-raised md-primary" @click = "showDialogFunction()">Add Amenity</md-button>
  </div>
</template>

<script>
  import axios from 'axios'
  import Amenity from "../Amenity.vue"

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
        searchedItems = searchedItems.concat(items.filter(item => (item.type).includes(term)));
      }

      if (term) {
        searchedItems = searchedItems.concat(items.filter(item => toLower(item.name).includes(toLower(term))));
      }

      searchedItems = uniqueElementsBy(searchedItems, (a,b) => a.name == b.name);

      return searchedItems
  }

  export default {
    name: 'AmenitiesTable',
    data: () => ({
      search: null,
      searchType: null,
      searched: [],
      amenities: [],
      showDialog: false,
      searchedWord: "",
      isEdit: false,
      editType: null,
      editName: null
    }),
    props: {

    },
    components: {
        Amenity
    },
    methods: {
      searchOnTable () {
        if(this.searchedWord == "") {
                this.searched = this.amenities;
            } else {
                this.searched = searchOnTable(this.amenities, this.searchedWord);
            }
        },
      showDialogFunction() {
        this.isEdit = false
        this.editType = ""
        this.editName = ""
        this.showDialog = true
      },
      removeAmenity(item) {
        axios.post('http://localhost:8080/PocetniREST/rest/amenities/delete', {
                  id: item.id,
                  type: item.type,
                  name: item.name
        })
                    .then(data => console.log(data.data))
                    .catch(error => {
                      console.log(error)
                    })
        this.amenities.slice(item.id, 1)
      },
      isEditFunction(item) {
        this.isEdit = true
        
        console.log(item.type, item.name, this.isEdit)
        this.editType = item.type
        this.editName = item.name
        this.showDialog = true
      }
    },
    created () {
      axios.get('http://localhost:8080/PocetniREST/rest/amenities')
                            .then(data => { 
                              this.amenities = data.data
                              this.searched = data.data})
                            .catch(error => {
                                console.log(error) 
                            });
      },
    }
</script>

<style lang="scss" scoped>
  .md-field {
    max-width: 30   0px;
  }
</style>