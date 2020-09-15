<template>
  <div class = "amenitiesTable">
   
    <md-dialog :md-active.sync="showDialog">
          <Amenity @amenityAdded = "amenityCreated($event)"
                   @amenityEdited = "amenityEdited($event)"
                   :changeId="this.editId" :changeName="this.editName" :changeType="this.editType" :isEdit="this.isEdit" />
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
  import http from '../../http-common'
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
      editName: null,
      editId: null
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
        this.editId = null
        this.showDialog = true
      },
      removeAmenity(item) {
        http.delete('amenities/'+item.id)
          .then(http.put('apartment/deletedAmenity/'+item.id)
                .then(data => console.log(data.data))
                .catch(error => console.log(error)))
          .catch(error => console.log(error))
        http.get('amenities').then(data => {
                                 this.amenities = data.data
                                 this.searched = data.data
      })
                            .catch(error => {
                                 console.log(error)
                            })
        
      },
      isEditFunction(item) {
        this.isEdit = true
        this.editType = item.type
        this.editName = item.name
        this.editId = item.id
        this.showDialog = true
      },
      amenityCreated(amenity) {
        this.showDialog = false
        this.amenities.push(amenity)
      },
      amenityEdited() {
        this.isEdit = false
        this.showDialog = false
        http.get('amenities').then(data => {
                                 this.amenities = data.data
                                 this.searched = data.data
      })
                            .catch(error => {
                                 console.log(error)
                            })
        
      }
    },
    created () {
      http.get('amenities').then(data => {
                                 this.amenities = data.data
                                 this.searched = data.data
      })
                            .catch(error => {
                                 console.log(error)
                            })
    }

  }
</script>

<style lang="scss" scoped>
  .md-field {
    max-width: 30   0px;
  }
</style>