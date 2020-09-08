<template>
  <div class = "apartmentTable">
    <md-table v-model="searched" md-sort="name" md-sort-order="asc" md-card md-fixed-header>
      <md-table-toolbar>
        <div class="md-toolbar-section-start">
          <h1 class="md-title">Apartments</h1>
        </div>

        <md-field md-clearable class="md-toolbar-section-end">
          <md-input placeholder="Search by type..." v-model="search" @input="searchOnTable" />
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
  </div>
</template>

<script>
  const toLower = text => {
    return text.toString().toLowerCase()
  }

  const searchByType = (items, term) => {
    if (term) {
      return items.filter(item => toLower(item.type).includes(toLower(term)))
    }

    return items
  }

  export default {
    name: 'ApartmentTable',
    data: () => ({
      search: null,
      searched: [],
      apartments: [
        {
          id: 1,
          type: "Full",
          roomNumber: 2,
          guestNumber: 5,
          entryTime: 14,
          leaveTime: 10,
          amenities: ["Hot tub", "TV"]
        },
        {
          id: 2,
          type: "One Room",
          roomNumber: 1,
          guestNumber: 2,
          entryTime: 14,
          leaveTime: 10,
          amenities: ["Room service", "TV"]
        },
      ]
    }),
    methods: {
      searchOnTable () {
        this.searched = searchByType(this.apartments, this.search)
      }
    },
    created () {
      this.searched = this.apartments
    }
  }
</script>

<style lang="scss" scoped>
  .md-field {
    max-width: 300px;
  }
</style>