<template>
    <div class = "filter">
        <form novalidate class="md-layout">
            <md-card class="md-layout-item md-size-100 md-small-size-100">
                <md-card-content>
                    <!-- APARTMENT  -->
                    <div class="md-layout md-gutter">
                        <div class="md-layout-item md-small-size-100" v-if="dateFilterActivated" >
                            <md-field>
                                <label for="availableDateFrom">Available from:</label>
                                <md-datepicker v-model="apartment.arrivalDate"></md-datepicker>
                            </md-field>
                        </div>
                        <div class="md-layout-item md-small-size-100" v-if="dateFilterActivated" >
                            <md-field>
                                <label for="availableDateTo">To: </label>
                                <md-datepicker v-model="apartment.checkoutDate"></md-datepicker>
                            </md-field>
                        </div>
                        <br>
                        <div class="md-layout-item md-small-size-100" v-if="guestNumberFilterActivated">
                            <md-field >
                                <label for="guestNumber">Guest Number</label>
                                <md-input type = "number" name="guestNumber" id="guestNumber" autocomplete="guestNumber" v-model="apartment.guestNumber"  />
                            </md-field>
                        </div>
                        <div class="md-layout-item md-small-size-100" v-if="cityFilterActivated">
                            <md-field >
                                <label for="city">City</label>
                                <md-input name="city" id="city" autocomplete="city" v-model="apartment.city"  />
                            </md-field>
                        </div>
                        <div class="md-layout-item md-small-size-100" v-if="countryFilterActivated">
                            <md-field >
                                <label for="country">Country</label>
                                <md-input name="country" id="country" autocomplete="country" v-model="apartment.country"  />
                            </md-field>
                        </div>
                        <div class="md-layout-item md-small-size-100" v-if="roomNumberFilterActivated">
                            <md-field >
                                <label for="fromRoomNumber">Room Number from:</label>
                                <md-input type = "number" name="fromRoomNumber" id="fromRoomNumber" autocomplete="fromRoomNumber" v-model="apartment.fromRoomNumber"  />
                            </md-field>
                        </div>
                        <div class="md-layout-item md-small-size-100" v-if="roomNumberFilterActivated">
                            <md-field >
                                <label for="toRoomNumber">To:</label>
                                <md-input type = "number" name="toRoomNumber" id="toRoomNumber" autocomplete="toRoomNumber" v-model="apartment.toRoomNumber"  />
                            </md-field>
                        </div>
                    </div>
                    <div class="md-layout md-gutter">
                        <div class="md-layout-item md-small-size-100" v-if="priceFilterActivated">
                            <md-field >
                                <label for="fromPrice">Price from: </label>
                                <md-input type = "number" name="fromPrice" id="fromPrice" autocomplete="fromPrice" v-model="apartment.fromPrice"  />
                            </md-field>
                        </div>
                        <div class="md-layout-item md-small-size-100" v-if="priceFilterActivated">
                            <md-field >
                                <label for="toPrice">To:</label>
                                <md-input type = "number" name="toPrice" id="toPrice" autocomplete="toPrice" v-model="apartment.toPrice"  />
                            </md-field>
                        </div>
                        <div class="md-layout-item" v-if="typeFilterActivated">
                            <md-field>
                            <label for="apartmentType">Apartment type</label>
                                <md-select name="apartmentType" id="apartmentType" @md-selected="apartmentTypeSelected" v-model="this.apartment.apartmentType" md-dense >
                                    <md-option value="ALL">All</md-option>
                                    <md-option value="FULL">Full</md-option>
                                    <md-option value="ONE_ROOM">One room</md-option>
                                </md-select>
                            </md-field>
                        </div>
                        <div class="md-layout-item" v-if="amenitiesFilterActivated">
                            <md-field>
                            <label for="apartmentAmenities">Apartment amenities</label>
                                <md-select v-model="this.apartment.hasAmenities" @md-selected="amenitySelected" multiple md-dense>
                                    <md-option v-for="amenity in this.amenityList" :value="amenity.id" :key="amenity.id"> {{amenity.name}}</md-option>
                                </md-select>
                            </md-field>
                        </div>
                        <div class="md-layout-item" v-if="apartmentStatusFilterActivated"> 
                            <md-field>
                            <label for="apartmentStatus">Apartment Status</label>
                                <md-select name="apartmentStatus" id="apartmentStatus" v-model="this.apartment.apartmentStatus" @md-selected="apartmentStatusSelected" md-dense >
                                    <md-option value="ALL">All</md-option>
                                    <md-option value="ACTIVE">Active</md-option>
                                    <md-option value="NON_ACTIVE">Non active</md-option>
                                </md-select>
                            </md-field>
                        </div>
                    </div>

                    <!-- USER -->
                    <div class="md-layout md-gutter" v-if="userTypeFilterActivated">
                        <div class="md-layout-item">
                            <md-field>
                            <label for="userType">User Type</label>
                                <md-select name="userType" id="userType" v-model="this.user.userType" @md-selected="userTypeSelected" md-dense >
                                    <md-option value="ALL">All</md-option>
                                    <md-option value="ADMINISTRATOR">Administrator</md-option>
                                    <md-option value="HOST">Host</md-option>
                                    <md-option value="GUEST">Guest</md-option>
                                </md-select>
                            </md-field>
                        </div>
                        <div class="md-layout-item" v-if="genderFilterActivated">
                            <md-field>
                            <label for="gender">Gender</label>
                                <md-select name="gender" id="gender" v-model="this.user.gender" @md-selected="userGenderSelected" md-dense >
                                    <md-option value="ALL">All</md-option>
                                    <md-option value="MALE">Male</md-option>
                                    <md-option value="FEMALE">Female</md-option>
                                    <md-option value="OTHER">Other</md-option>
                                </md-select>
                            </md-field>
                        </div>
                    </div>

                    <!-- RESERVATION -->
                    <div class="md-layout md-gutter" v-if="reservationsStatusFilterActivated">
                        <div class="md-layout-item">
                            <md-field>
                            <label for="reservationStatus">Reservation Status</label>
                                <md-select name="reservationStatus" id="reservationStatus" @md-selected="selectedReservationStatus" v-model="this.reservation.reservationStatus" md-dense >
                                    <md-option value="ALL"> All </md-option>
                                    <md-option value="CREATED" >Created</md-option>
                                    <md-option value="DENIED">Denied</md-option>
                                    <md-option value="CANCELED">Canceled</md-option>
                                    <md-option value="ACCEPTED">Accepted</md-option>
                                    <md-option value="PASSED">Passed</md-option>
                                </md-select>
                            </md-field>
                        </div>
                    </div>

                </md-card-content>
                
                <md-chip v-if="this.guestNumberFilter" class="md-primary" md-deletable @md-delete="guestNumberDeleted">{{this.apartment.guestNumber}} <md-tooltip md-direction="top">Guest Number</md-tooltip></md-chip>
                <md-chip v-if="this.cityFilter" class="md-primary" md-deletable @md-delete="cityDeleted($event)">{{this.apartment.city}} <md-tooltip md-direction="top">City</md-tooltip></md-chip>
                <md-chip v-if="this.countryFilter" class="md-primary" md-deletable @md-delete="countryDeleted($event)">{{this.apartment.country}} <md-tooltip md-direction="top">Country</md-tooltip></md-chip>
                <md-chip v-if="this.roomNumberFilter" class="md-primary" md-deletable @md-delete="roomNumberDeleted($event)">{{this.apartment.fromRoomNumber}} - {{this.apartment.toRoomNumber}} <md-tooltip md-direction="top">Room Number</md-tooltip></md-chip>
                <md-chip v-if="this.priceFilter" class="md-primary" md-deletable @md-delete="priceDeleted($event)">{{this.apartment.fromPrice}} - {{this.apartment.toPrice}} <md-tooltip md-direction="top">Price range</md-tooltip></md-chip>
                <md-chip v-if="this.typeFilter" class="md-primary" md-deletable @md-delete="typeDeleted($event)">{{this.apartment.apartmentType}} <md-tooltip md-direction="top">Apartment Type</md-tooltip></md-chip>
                <md-chip v-if="this.statusFilter" class="md-primary" md-deletable @md-delete="statusDeleted($event)">{{this.apartment.apartmentStatus}} <md-tooltip md-direction="top">Apartment Status</md-tooltip></md-chip>
                <md-chip v-if="this.userTypeFilter" class="md-primary" md-deletable @md-delete="userTypeDeleted($event)">{{this.user.userType}} <md-tooltip md-direction="top">User Type</md-tooltip></md-chip>
                <md-chip v-if="this.genderFilter" class="md-primary" md-deletable @md-delete="genderDeleted($event)">{{this.user.gender}} <md-tooltip md-direction="top">Gender</md-tooltip></md-chip>
                <md-chip v-if="this.reservationsStatusFilter" class="md-primary" md-deletable @md-delete="reservationStatusDeleted($event)">{{this.reservation.reservationStatus}} <md-tooltip md-direction="top">Reservation Status</md-tooltip></md-chip>
                 <md-chip v-if="this.dateFilter" class="md-primary" md-deletable @md-delete="dateFilterDeleted($event)">Selected dates <md-tooltip md-direction="top">Reservation Status</md-tooltip></md-chip>

                <md-button  class="md-dense md-raised md-primary" @click="filter()" >Filter</md-button>
            </md-card>
        </form>

    </div>
</template>

<script>
export default {
    name: 'FilterComponent',
    data: () => ({
        apartment: {
            guestNumber: null,
            arrivalDate: null,
            checkoutDate: null,
            city: null,
            country: null,
            fromPrice: null,
            toPrice: null,
            fromRoomNumber: null,
            toRoomNumber: null,
            apartmentType: "ALL",
            hasAmenities: [],
            apartmentStatus: null
        },
        user: {
            userType: "ALL",
            gender: "ALL"
        },
        reservation: {
            reservationStatus: "ALL"
        },
        guestNumberFilter: false,
        cityFilter: false,
        countryFilter: false,
        roomNumberFilter: false,
        priceFilter: false,
        typeFilter: false,
        statusFilter: false,
        userTypeFilter: false,
        genderFilter: false,
        reservationsStatusFilter: false,
        dateFilter: false,

        guestNumberFilterActivated: false,
        cityFilterActivated: false,
        countryFilterActivated: false,
        roomNumberFilterActivated: false,
        priceFilterActivated: false,
        typeFilterActivated: false,
        statusFilterActivated: false,
        userTypeFilterActivated: false,
        genderFilterActivated: false,
        reservationsStatusFilterActivated: false,
        apartmentStatusFilterActivated: false,
        dateFilterActivated: false,
        amenitiesFilterActivated: false,
    }),
    props: {
        activeFilters: Array,
        amenityList: Array,
    },
    mounted() {
        if(this.activeFilters) {
            if(this.activeFilters.includes('guestNumber')) {
                this.guestNumberFilterActivated = true;
            }
            if(this.activeFilters.includes('city')) {
                this.cityFilterActivated = true;
            }
            if(this.activeFilters.includes('country')) {
                this.countryFilterActivated = true;
            }
            if(this.activeFilters.includes('roomNumber')) {
                this.roomNumberFilterActivated = true;
            }
            if(this.activeFilters.includes('price')) {
                this.priceFilterActivated = true;
            }
            if(this.activeFilters.includes('type')) {
                this.typeFilterActivated = true;
            }
            if(this.activeFilters.includes('status')) {
                this.statusFilterActivated = true;
            }
            if(this.activeFilters.includes('userType')) {
                this.userTypeFilterActivated = true;
            }
            if(this.activeFilters.includes('gender')) {
                this.genderFilterActivated = true;
            }
            if(this.activeFilters.includes('reservationStatus')) {
                this.reservationsStatusFilterActivated = true;
            }
            if(this.activeFilters.includes('apartmentStatus')) {
                this.apartmentStatusFilterActivated = true;
            }
            if(this.activeFilters.includes('hasAmenity')) {
                this.amenitiesFilterActivated = true;
            }
            if(this.activeFilters.includes('calendar')) {
                this.dateFilterActivated = true;
            }
        }
    },
    methods: {
        userGenderSelected(value) {
            this.user.gender = value;
        },
        userTypeSelected(value) {
            this.user.userType = value;
        },
        apartmentStatusSelected(value) {
            this.apartment.apartmentStatus = value;
        },
        apartmentTypeSelected(value) {
            this.apartment.apartmentType = value;
        },
        amenitySelected(value) {
            this.apartment.hasAmenities = value;
        },
        selectedReservationStatus(value) {
            this.reservation.reservationStatus = value;
        },
        amenitiesDeleted() {
            this.apartment.hasAmenities = [];
            this.filter();
        },
        guestNumberDeleted() {
            this.apartment.guestNumber = null;
            this.filter();
        },
        cityDeleted() {
            this.apartment.city = null;
            this.filter();
        },
        countryDeleted() {
            this.apartment.country = null;
            this.filter();
        },
        roomNumberDeleted() {
            this.apartment.fromRoomNumber = null;
            this.apartment.toRoomNumber = null;
            this.filter();
        },
        priceDeleted() {
            this.apartment.fromPrice = null;
            this.apartment.toPrice = null;
            this.filter();
        },
        typeDeleted() {
            this.apartment.apartmentType = null;
            this.filter();
        },
        statusDeleted() {
            this.apartment.apartmentStatus = null;
            this.filter();
        },
        userTypeDeleted() {
            this.user.userType = null;
            this.filter();
        },
        genderDelted() {
            this.user.gender = null;
            this.filter();
        },
        reservationStatusDeleted() {
            this.reservation.reservationStatus = null;
            this.filter();
        },
        dateFilterDeleted() {
            this.apartment.arrivalDate = null;
            this.apartment.checkoutDate = null;
            this.filter();
        },
        filter() { 
            if(this.apartment.arrivalDate === null || this.apartment.checkoutDate === "null") {
                this.dateFilter = false;
            } else {
                this.dateFilter = true;
            }
            if(this.apartment.guestNumber === null || this.apartment.guestNumber === "") {
                this.guestNumberFilter = false
            } else {
                this.guestNumberFilter = true
            }

            if(this.apartment.city === null || this.apartment.city === "") {
                this.cityFilter = false
            } else {
                this.cityFilter = true
            }

            if(this.apartment.country === null || this.apartment.country === "") {
                this.countryFilter = false
            } else {
                this.countryFilter = true
            }

            if(this.apartment.fromRoomNumber === null || this.apartment.fromRoomNumber === "" || this.apartment.toRoomNumber === null || this.apartment.toRoomNumber === "" || this.apartment.toRoomNumber < this.apartment.fromRoomNumber ) {
                this.roomNumberFilter = false
            } else {
                this.roomNumberFilter = true
            }

            if(this.apartment.fromPrice === null || this.apartment.fromPrice === "" || this.apartment.toPrice === null || this.apartment.toPrice === "" || this.apartment.toPrice < this.apartment.fromPrice) {
                this.priceFilter = false
            } else {
                this.priceFilter = true
            }
            
            if(this.apartment.apartmentType === null || this.apartment.apartmentType === "" || this.apartment.apartmentType === "ALL") {
                this.typeFilter = false
                this.apartment.apartmentType = null;
            } else {
                this.typeFilter = true
            }

            if(this.apartment.apartmentStatus === null || this.apartment.apartmentStatus === "" || this.apartment.apartmentStatus === "ALL") {
                this.statusFilter = false
                this.apartment.apartmentStatus = null;
            } else {
                this.statusFilter = true
            }

            if(this.user.userType === null || this.user.userType === "" || this.user.userType==="ALL") {
                this.userTypeFilter = false
                this.user.userType = null;
            } else {
                this.userTypeFilter = true
            }

             if(this.user.gender === null || this.user.gender === "" || this.user.gender==="ALL") {
                this.genderFilter = false
                this.user.gender = null;
            } else {
                this.genderFilter = true
            }

             if(this.reservation.reservationStatus === null || this.reservation.reservationStatus === "" || this.reservation.reservationStatus === "ALL") {
                this.reservationsStatusFilter = false
                this.reservation.reservationStatus = null;
            } else {
                this.reservationsStatusFilter = true
            }

            this.$emit('filtering', {apartment: this.apartment, user:this.user, reservation:this.reservation});
        }
    }

}
</script>

<style lang="scss" scoped>

</style>