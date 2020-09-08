<template>
  <div class = "amenitiesTable">
    <md-table v-model="searched" md-sort="name" md-sort-order="asc" md-card md-fixed-header>
      <md-table-toolbar>
        <div class="md-toolbar-section-start">
          <h1 class="md-title">Amenities</h1>
        </div>

        <md-field md-clearable class="md-toolbar-section-end">
          <md-input placeholder="Search by name..." v-model="search" @input="searchOnTable" />
        </md-field>
        <md-field md-clearable class="md-toolbar-section-end">
          <md-input placeholder="Search by type..." v-model="searchType" @input="searchOnTableByType" />
        </md-field>          
      </md-table-toolbar>
      <md-table-row slot="md-table-row" slot-scope="{ item }">
        <md-table-cell md-label="ID" md-sort-by="id" md-numeric>{{ item.id }}</md-table-cell>
        <md-table-cell md-label="Name" md-sort-by="name">{{ item.name }}</md-table-cell>
        <md-table-cell md-label="Type" md-sort-by="type">{{ item.type }}</md-table-cell>
      </md-table-row>
      <md-table-row>
         
      </md-table-row>
    </md-table>
    <md-button class="md-dense md-raised md-primary" to="/amenity">Add Amenity</md-button>
  </div>
</template>

<script>
  // import axios from 'axios'

  const toLower = text => {
    return text.toString().toLowerCase()
  }

  const searchByName = (items, term) => {
    if (term) {
      return items.filter(item => toLower(item.name).includes(toLower(term)))
    }

    return items
  }

  const searchByType = (items, term) => {
      if(term) {
          return items.filter(item => toLower(item.type).includes(toLower(term)))
      }
      return items
  }

  export default {
    name: 'AmenitiesTable',
    data: () => ({
      search: null,
      searchType: null,
      searched: [],
      amenities: [
        {
          id: 1,
          name: "Room service",
          type: "Service"
        },
        {
          id: 2,
          name: "Hot tub",
          type: "Object"
        },
        {
          id: 3,
          name: "Plasma TV",
          type: "Object"
        }
      ]
    }),
    methods: {
      searchOnTable () {
        this.searched = searchByName(this.amenities, this.search).filter() 
      },
      searchOnTableByType() {
        this.searched = searchByType(this.amenities, this.searchType)
      }
    },
    created () {
      this.searched = this.amenities
    }
  }
</script>

<style lang="scss" scoped>
  .md-field {
    max-width: 30   0px;
  }
</style>