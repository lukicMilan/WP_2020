<template>
    <div>
        <div v-if="!loggedInUser">
            <access-denied> </access-denied>
        </div>
        <div v-if="loggedInUser">
            <md-table v-model="searched" md-sort="id" md-sort-order="asc" md-card>
            <md-table-toolbar>
                <div class="md-toolbar-section-start">
                    <h1 class="md-title">Reservations</h1>
                </div>
                
                <md-field md-clearable class="md-toolbar-section-end">
                    <md-input placeholder="Search status..." v-model="searchedWord" @input="searchOnTable" />
                </md-field>
            </md-table-toolbar>

            <md-table-row slot="md-table-row" slot-scope="{ item }">
                <md-table-cell md-label="Id" md-sort-by="reservationId">{{item.reservationId}}</md-table-cell>
                <md-table-cell md-label="Apartment Id" md-sort-by="apartmentId">{{item.apartmentId}}</md-table-cell>
                <md-table-cell md-label="Date" md-sort-by="date">{{item.date}}</md-table-cell>
                <md-table-cell md-label="Nights" md-sort-by="nights">{{item.nights}}</md-table-cell>
                <md-table-cell md-label="Price" md-sort-by="totalPrice">{{item.totalPrice}}</md-table-cell>
                
                <md-table-cell v-if="!loggedInHost" md-label="Host" md-sort-by="hostUsername">{{item.hostUsername}}</md-table-cell>
                <md-table-cell v-if="!loggedInGuest" md-label="Guest" md-sort-by="guestUsername">{{item.guestUsername}}</md-table-cell>
                <md-table-cell md-label="Status" md-sort-by="status">{{item.status}}</md-table-cell>
                <md-table-cell md-label="Actions">
                    <md-button v-if="loggedInGuest&&!reservationCenceled(item)" @click="cancelReservation(item)" class="md-raised md-accent">Cancel</md-button>
                </md-table-cell>
            </md-table-row>
        </md-table>
        </div>
    </div>
</template>

<script>
import AccessDenied from '../../pages/AccessDenied.vue';
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
    //   searchedItems = searchedItems.concat(items.filter(item => (item.reservationId).includes(term)));
    //   searchedItems = searchedItems.concat(items.filter(item => (item.apartmentId).includes(term)));
    //   searchedItems = searchedItems.concat(items.filter(item => (item.date).includes(term)));
    //   searchedItems = searchedItems.concat(items.filter(item => (item.nights).includes(term)));
    //   searchedItems = searchedItems.concat(items.filter(item => (item.totalPrice).includes(term)));
    //   if(!loggedInHost) {
    //     searchedItems = searchedItems.concat(items.filter(item => toLower(item.hostUserame).includes(toLower(term))));
    //   }
    //   if(!loggedInGuest) {
    //     searchedItems = searchedItems.concat(items.filter(item => toLower(item.guestUsername).includes(toLower(term))));
    //   }
      searchedItems = searchedItems.concat(items.filter(item => toLower(item.status).includes(toLower(term))));
      searchedItems = uniqueElementsBy(searchedItems, (a,b) => a.id == b.id);
    }


    return searchedItems
}

export default {
    props: {
        loggedInUser: null,
    },
    components: {
        accessDenied: AccessDenied,
    },
    data: function () {
        return {
            reservations: [],
            searched: [],
            searchedWord: "",
            // loggedInHost: false,
            // loggedInGuest: false,
        }
    },
    mounted: function () {
        if(this.loggedInUser === null) {
            this.$emit('globalMessage', 'You have to be loged in to access this page.');
            
            return;
        } 
        if(this.loggedInUser.userType === "ADMINISTRATOR") {
            http.get('reservation')
                .then(data => {
                    this.reservations = data.data;
                    this.searched = data.data;
                })
                .catch(() => {
                    this.$emit('globalMessage', 'Error!');
                });
            // this.loggedInHost = false;
            // this.loggedInGuest = false;
            return;
        }
        if(this.loggedInUser.userType === "HOST") {
            http.get('reservation/host/'+this.loggedInUser.username)
                .then(data => {
                    this.reservations = data.data;
                    this.searched = data.data;
                })
                .catch(() => {
                    this.$emit('globalMessage', 'Error!');
                });
            // this.loggedInHost = true;
            // this.loggedInGuest = false;
            return;
        }
        if(this.loggedInUser.userType === "GUEST") {
            http.get('reservation/guest/'+this.loggedInUser.username)
                .then(data => {
                    this.reservations = data.data;
                    this.searched = data.data;
                })
                .catch(() => {
                    this.$emit('globalMessage', 'Error!');
                });
            // this.loggedInHost = false;
            // this.loggedInGuest = true;
            return;
        }
    },
    methods: {
        reservationCenceled(reservation) {
            if(reservation.status==="CANCELED") {
                return true;
            } 
            return false;
        },
        searchOnTable() {
            if(this.searchedWord == "") {
                this.searched = this.reservations;
            } else {
                this.searched = searchOnTable(this.reservations, this.searchedWord);
            }
        },
        cancelReservation(reservation) {
            reservation.status="CANCELED";
            http.put('reservation/status', { 
                reservationId: reservation.reservationId,
                reservationStatus: reservation.status
            })
            .then(()=> {
                this.$emit('globalMessage', 'Reservation canceled.');
            })
            .catch(() => {
                this.$emit('globalMessage', 'An error has occured!');
            });
        },
    },
    computed: {
        loggedInHost() {
            if(this.loggedInUser === null) {
                return false;
            } 
            if(this.loggedInUser.userType ==="HOST") {
                return true;
            }
            return false;
        },
        loggedInGuest() {
            if(this.loggedInUser === null) {
                return false;
            } 
            if(this.loggedInUser.userType ==="GUEST") {
                return true;
            }
            return false;
        }
    }
}
</script>