<template>
    <div>
        <div v-if="!loggedInUser">
            <access-denied> </access-denied>
        </div>
        <span>
        <md-button class="md-raised md-primary" v-if="!filterActive" @click="activateFilter">
            Filter
        </md-button>
        </span>
        <div v-if="filterActive">
            <filterComponent  :activeFilters="this.activeFilters"
                                                    @filtering="doFiltering"> </filterComponent>
        </div>
        <md-dialog :md-active.sync="comment">
          <ApartmentComment :loggedInUser = "loggedInUser" :apartmentId = "this.apartmentId" />
        </md-dialog>
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
                    <md-button v-if="loggedInGuest&&reservationCancelingEnabled(item)" @click="cancelReservation(item)" class="md-raised md-accent">Cancel</md-button>
                    <span>
                        <md-button v-if="loggedInHost&&reservationAcceptingEnabled(item)" @click="acceptReservation(item)" class="md-raised md-primary">Accept</md-button>
                    </span>
                    <span>
                        <md-button v-if="loggedInHost&&reservationDenieingEnabled(item)" @click="denyReservation(item)" class="md-raised md-accent">Deny</md-button>
                    </span>
                </md-table-cell >
                <md-table-cell v-if="loggedInGuest" md-label = "Comment">
                    <md-button class="md-raised" @click = "openCommentDialog(item)">Comment</md-button>
                </md-table-cell>
            </md-table-row>
        </md-table>
        </div>
    </div>
</template>

<script>
import AccessDenied from '../../pages/AccessDenied.vue';
import http from '../../http-common'
import ApartmentComment from '../ApartmentComment'
import FilterComponent from '../FilterComponent'

// const toLower = text => {
//     return text.toString().toLowerCase()
// }

// const uniqueElementsBy = (arr, fn) =>
//   arr.reduce((acc, v) => {
//     if (!acc.some(x => fn(v, x))) acc.push(v);
//     return acc;
// }, []);

// const searchOnTable = (items, term) => {
//     let searchedItems = [];
//     if (term) {
//     //   searchedItems = searchedItems.concat(items.filter(item => (item.reservationId).includes(term)));
//     //   searchedItems = searchedItems.concat(items.filter(item => (item.apartmentId).includes(term)));
//     //   searchedItems = searchedItems.concat(items.filter(item => (item.date).includes(term)));
//     //   searchedItems = searchedItems.concat(items.filter(item => (item.nights).includes(term)));
//     //   searchedItems = searchedItems.concat(items.filter(item => (item.totalPrice).includes(term)));
//     //   if(!loggedInHost) {
//     //     searchedItems = searchedItems.concat(items.filter(item => toLower(item.hostUserame).includes(toLower(term))));
//     //   }
//     //   if(!loggedInGuest) {
//     //     searchedItems = searchedItems.concat(items.filter(item => toLower(item.guestUsername).includes(toLower(term))));
//     //   }
//       searchedItems = searchedItems.concat(items.filter(item => toLower(item.status).includes(toLower(term))));
//       searchedItems = uniqueElementsBy(searchedItems, (a,b) => a.id == b.id);
//     }


//     return searchedItems
// }

export default {
    props: {
        loggedInUser: null,
    },
    components: {
        accessDenied: AccessDenied,
        ApartmentComment,
        filterComponent: FilterComponent
    },
    data: function () {
        return {
            reservations: [],
            searched: [],
            searchedWord: "",
            // loggedInHost: false,
            // loggedInGuest: false,
            comment: false,
            apartmentId: Number,
            filterActive: false,
            activeFilters: ['reservationStatus']
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
        reservationDenieingEnabled(reservation) {
            if(reservation.status==="CREATED") {
                return true;
            }
            return false;
        },
        reservationAcceptingEnabled(reservation) {
            if(reservation.status==="CREATED") {
                return true;
            }
            return false;
        },
        reservationCancelingEnabled(reservation) {
            if(reservation.status==="CREATED"||reservation.status==="ACCEPTED") {
                return true;
            }
            return false
        },
        reservationCenceled(reservation) {
            if(reservation.status==="CANCELED") {
                return true;
            } 
            return false;
        },
        // searchOnTable() {
        //     if(this.searchedWord == "") {
        //         this.searched = this.reservations;
        //     } else {
        //         this.searched = searchOnTable(this.reservations, this.searchedWord);
        //     }
        // },
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
        acceptReservation(reservation) {
            reservation.status="ACCEPTED";
            http.put('reservation/status', { 
                reservationId: reservation.reservationId,
                reservationStatus: reservation.status
            })
            .then(()=> {
                this.$emit('globalMessage', 'Reservation accepted.');
            })
            .catch(() => {
                this.$emit('globalMessage', 'An error has occured!');
            });
        },
        denyReservation(reservation) {
            reservation.status="DENIED";
            http.put('reservation/status', { 
                reservationId: reservation.reservationId,
                reservationStatus: reservation.status
            })
            .then(()=> {
                this.$emit('globalMessage', 'Reservation denied.');
            })
            .catch(() => {
                this.$emit('globalMessage', 'An error has occured!');
            });
        },
        openCommentDialog(reservation) {
            this.apartmentId = reservation.apartmentId
            this.comment = true
        }, 
        activateFilter() {
            this.filterActive = true
        },
        doFiltering(parameters) {
            let searchedItems = [];
            this.reservations.forEach(reservation => {
                let valid = true;
                if(parameters.reservation.reservationStatus !== null) {
                    if(parameters.reservation.reservationStatus !== reservation.status) {
                        valid = false
                    }
                }
                if(valid) {
                    searchedItems.push(reservation)
                }
                
            });
            this.searched = searchedItems;
        }
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