<template>
  <div class = "apartmentTable">
    <md-dialog class="md-dialog-content md-theme-default" :md-active.sync="showDialog">
      <apartmentDetails :selectedApartment="this.selectedApartment"
                        :loggedInUser="this.loggedInUser"
                         @activateReservation="activateReservation($event)"
                         @openGallery="openGallery($event)"></apartmentDetails>
    </md-dialog>

    <md-dialog  class="md-dialog-content md-theme-default" :md-active.sync="isEdit" >
      <div v-if = this.isEdit>
        <Apartment @apartmentAdded = "apartmentCreated($event)"
                   @apartmentEdited = "apartmentEdited($event)"
        :id = "this.apartment.id" :isEdit = "this.isEdit" />
      </div>
    </md-dialog>
    

    <div>
      <create-reservation v-if="reservationActive" :selected-apartment="this.selectedApartment"></create-reservation>
    </div>

    <span>
      <md-button class="md-raised md-primary" v-if="!filterActive" @click="activateFilter">
        Filter
      </md-button>
    </span>
    <div>
      <filter-component v-if="filterActive" :activeFilters="this.activeFilters"
                                            :amenityList="this.amenities"
                                            @filtering="doFiltering"> </filter-component>
    </div>
    <md-table v-model="searched" md-sort="name" md-sort-order="asc" md-card md-fixed-header>
      <md-table-toolbar>
        <div class="md-toolbar-section-start">
          <h1 class="md-title">Apartments</h1>
          
        </div>
        

      </md-table-toolbar>

      <md-table-row slot="md-table-row" slot-scope="{ item }">
        <md-table-cell v-if="adminLoggedIn" md-label="ID" md-sort-by="id" md-numeric>{{ item.id }}</md-table-cell>
        <md-table-cell md-label="Type" md-sort-by="type">{{ item.type }}</md-table-cell>
        <md-table-cell md-label="RoomNumber" md-sort-by="roomNumber">{{ item.roomNumber }}</md-table-cell>
        <md-table-cell md-label="GuestNumber" md-sort-by="guestNumber">{{ item.guestNumber }}</md-table-cell>
        <md-table-cell md-label="Adress" md-sort-by="address">{{ item.street }}</md-table-cell>
        <md-table-cell md-label="Number" md-sort-by="number">{{ item.number }}</md-table-cell>
        <md-table-cell md-label="City" md-sort-by="city">{{ item.city }}</md-table-cell>

        <md-table-cell md-label="Details"><md-button class="md-dense md-raised md-secondary" @click="showApartmentDetails(item)">Details</md-button></md-table-cell>
        <md-table-cell md-label="Edit"><md-button class="md-dense md-raised md-default" @click="isEditFunction(item)">Edit</md-button></md-table-cell>
        <md-table-cell md-label="Deactivate" v-if = "canDeactivate(item)">
          <div v-if = "canDeactivate(item)">
            <md-button class="md-dense md-raised md-accent" @click = "deactivateApartment(item)">Deactivate</md-button>
          </div>
        </md-table-cell>
        <md-table-cell md-label="Activate" v-if = "canActivate(item)" >
          <div v-if = "canActivate(item)">
            <md-button class="md-dense md-raised md-primary" @click = "activate(item)">Activate</md-button>
          </div>
        </md-table-cell>
        <md-table-cell md-label="Delete" v-if = "adminLoggedIn || hostLoggedIn(item)">
          <div v-if = "adminLoggedIn || hostLoggedIn(item)">
            <md-button class="md-dense md-raised md-accent" @click = "deleteApartment(item)">Delete</md-button>
          </div>
        </md-table-cell>
        <!-- <md-table-cell md-label="EntryTime" md-sort-by="entryTime">{{ item.entryTime }}</md-table-cell>
        <md-table-cell md-label="LeaveTime" md-sort-by="leaveTime">{{ item.leaveTime }}</md-table-cell>
        <md-table-cell md-label="Amenities" md-sort-by="amenities">{{ item.amenities }}</md-table-cell> -->
      </md-table-row>
    </md-table>
    <md-button v-if="addButton" class="md-dense md-raised md-primary" to="/apartment">Add Apartment</md-button>
    
  </div>
</template>

<script>
  import http from '../../http-common'
  import ApartmentDetails from '../dialogContent/ApartmentDetails'
  import CreateReservation from '../CreateReservation'
  import Apartment from '../Apartment'
  import FilterComponent from '../FilterComponent'

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
    components: {
      apartmentDetails: ApartmentDetails,
      createReservation: CreateReservation,
      Apartment,
      filterComponent: FilterComponent,
    },
    data: () => ({
      activeFilters: ['city', 'street', 'price', 'roomNumber','guestNumber', 'type', 'hasAmenity', 'apartmentStatus', 'calendar'],
      search: null,
      searched: [],
      apartments: [],
      showDialog: false,
      selectedApartment: null,
      selectedDate: null,
      reservationActive: false,
      isEdit: false,
      apartment: null,
      filterActive: false,
    }),
    props: {
      loggedInUser: null,
    },
    methods: {
      doFiltering(parameters) {
        let searchedItems = [];
        this.apartments.forEach(apartment => {
          let valid = true;
          if(parameters.apartment.apartmentStatus !== null) {
            if(parameters.apartment.apartmentStatus === "ACTIVE") {
              if(!apartment.active) {
                valid = false;
              }
            }else {
              if(apartment.active) {
                valid = false;
              }
            }
          }
          if(parameters.apartment.apartmentType !== null)
            if(parameters.apartment.apartmentType !== apartment.type) {
              valid = false;
            }
            
          if(parameters.apartment.city !== null)
            if(parameters.apartment.city !== apartment.city) {
              valid = false;
            }
          if(parameters.apartment.fromPrice !== null)
            if(parameters.apartment.fromPrice > apartment.price || parameters.apartment.toPrice < apartment.price) {
              valid =false;
            }

          if(parameters.apartment.fromRoomNumber !== null && parameters.apartment.toRoomNumber !== null)
            if(parameters.apartment.fromRoomNumber > apartment.roomNumber || parameters.apartment.toRoomNumber < apartment.roomNumber) {
              valid = false;
            }

          if(parameters.apartment.guestNumber !== null) 
            if(parameters.apartment.guestNumber != apartment.guestNumber) {
              valid = false;
            }

          let hasAllAmenities = true;
          if(parameters.apartment.hasAmenities !== null)
            parameters.apartment.hasAmenities.forEach(amenityId => {
              let hasAmenity = false;
              apartment.amenities.forEach(amenity => {
                if(amenity.id === amenityId) {
                  hasAmenity = true;
                }
              });

              if(!hasAmenity) {
                hasAllAmenities = false;
              }
            });

          if(!hasAllAmenities) {
            valid = false;
          }

          if(valid) {
            searchedItems.push(apartment);
          }
        });

        this.searched = searchedItems;
      },
      activateFilter() {
        this.filterActive = true;
      },
      showApartmentDetails(apartment) {
        this.selectedApartment = apartment;
        this.showDialog = true;
      },
      activateReservation() {
        this.showDialog=false;
        this.reservationActive = true;
        this.$emit('activateReservation', this.selectedApartment);
      },
      deactiveReservation() {
        this.reservationActive = false;
      },
      openGallery(data) {
        this.$emit('openGallery', data)
      },
      isEditFunction(item) {
        this.isEdit = true;
        this.apartment = item;
      },
      apartmentCreated() {
        http.get('apartment').then(data => {
                                 this.apartment = data.data
                                 this.searched = data.data
      })
                            .catch(error => {
                                 console.log(error)
                            })
        
      },
      apartmentEdited() {
        this.isEdit = false
         http.get('apartment').then(data => {
                                 this.apartments = data.data
                                 this.searched = data.data
      })
                            .catch(error => {
                                 console.log(error)
                            })

      },
      deactivateApartment(item) {
        http.put('apartment',
                    {
                        id: item.id,
                        type: item.type,
                        roomNumber: item.roomNumber,
                        guestNumber: item.guestNumber,
                        latitude : item.latitude,
                        longitude : item.longitude,
                        city: item.city,
                        street: item.street,
                        zipCode: item.zipCode,
                        number: item.number,
                        imageList: [],
                        price: item.price,
                        entryTime: item.entryTime,
                        leaveTime: item.leaveTime,
                        amenities: item.selectedAmenities,
                        active: false,
                        rentDates: [],
                        freeDates: [],
                        comments: [],
                        hostUsername: this.loggedInUser.username

                    })
                    .then(() => { 
                        this.searched.forEach(ap => {
                        if(ap.id === item.id){
                          ap.active = false;
                        }
                      });
                      this.apartments.forEach(ap => {
                        if(ap.id === item.id){
                          ap.active = false;
                        }
                      });
                    })
                    .catch(error => {
                        console.log(error) 
                    });
      },
      activate(item) {
        http.put('apartment',
                    {
                        id: item.id,
                        type: item.type,
                        roomNumber: item.roomNumber,
                        guestNumber: item.guestNumber,
                        latitude : item.latitude,
                        longitude : item.longitude,
                        city: item.city,
                        street: item.street,
                        zipCode: item.zipCode,
                        number: item.number,
                        imageList: [],
                        price: item.price,
                        entryTime: item.entryTime,
                        leaveTime: item.leaveTime,
                        amenities: item.selectedAmenities,
                        active: true,
                        rentDates: [],
                        freeDates: [],
                        comments: [],
                        hostUsername: this.loggedInUser.username

                    })
                    .then(() => {
                      this.searched.forEach(ap => {
                        if(ap.id === item.id){
                          ap.active = true;
                        }
                      });
                      this.apartments.forEach(ap => {
                        if(ap.id === item.id){
                          ap.active = true;
                        }
                      });
                    })
                    .catch(error => {
                        console.log(error) 
                    });
      },
      canDeactivate(apartment) {
        if(this.loggedInUser === null) {
          return false
        }
        if(this.loggedInUser.userType === "GUEST") {
          return false
        }
        if(apartment.active === false) {
          return false
        }
        return true
      },
      canActivate(apartment) {
        if(this.loggedInUser === null) {
          return false
        }
        if(this.loggedInUser.userType === "GUEST") {
          return false    
        }
        if(apartment.active === true) {
          return false
        }
        return true
      },
      deleteApartment(apartment) {
        http.delete('apartment/'+apartment.id)
        .then(data => console.log(data))
        .catch(error => {
          console.log(error)
        })
        http.get('apartment')
            .then(data => {
              this.apartment = data.data,
              this.searched = data.data
            })
      }, hostLoggedIn: function(apartment) {
        if(this.loggedInUser===null) {
          return false;
        } else if(this.loggedInUser.userType === "HOST" && apartment.hostUsername === this.loggedInUser.username) {
          return true;
        }
        return false;
      }
      },
      searchOnTable() {
          if(this.searchedWord == "") {
              this.searched = this.reservations;
          } else {
              this.searched = searchOnTable(this.reservations, this.searchedWord);
          }
      },
    mounted () {
      if(this.loggedInUser === null) {
        http.get('apartment/active')
          .then(data => {
            this.apartments = data.data;
            this.searched = data.data;
          });
        return;
      } 
      if (this.loggedInUser.userType === "GUEST") {
        http.get('apartment/active')
          .then(data => {
            this.apartments = data.data;
            this.searched = data.data;
          });
        return;
      }
      if (this.loggedInUser.userType === "HOST") {
        http.get('apartment/user/'+this.loggedInUser.username)
          .then(data => {
            this.apartments = data.data;
            this.searched = data.data;
          });
        return;
      }
      if(this.loggedInUser.userType === "ADMINISTRATOR") {
       http.get('apartment')
            .then(data => { 
              this.apartments = data.data
              this.searched = data.data})
            .catch(error => {
                console.log(error) 
        });
      http.get('amenities')
          .then(data => {
            this.amenities = data.data;
          });
      this.reservationActive = false;
      }
    },
    computed: {
      addButton: function() {
        if(this.loggedInUser===null) {
          return false;
        } else if(this.loggedInUser.userType === "HOST") {
          return true;
        }
        return false;
      },
      adminLoggedIn: function() {
        if(this.loggedInUser===null) {
          return false;
        } else if(this.loggedInUser.userType === "ADMINISTRATOR") {
          return true;
        }
        return false;
      },
      
    },
  
  }
</script>

<style lang="scss" scoped>
  .md-field {
    max-width: 300px;
  }
  .md-table{
    max-width: 100%;
  }
  .md-dialog .md-dialog-container {
      min-height: 25%;
      max-height: 50%;
      min-width: 25%;
      max-width: 50%;
  }
</style>