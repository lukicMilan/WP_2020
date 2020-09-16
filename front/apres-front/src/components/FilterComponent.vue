<template>
    <div class = "filter">
        <form novalidate class="md-layout">
            <md-card class="md-layout-item md-size-100 md-small-size-100">
                <md-card-content>
                    <!-- APARTMENT  -->
                    <div class="md-layout md-gutter">
                        <div class="md-layout-item md-small-size-100">
                            <md-field >
                                <label for="guestNumber">Guest Number</label>
                                <md-input type = "number" name="guestNumber" id="guestNumber" autocomplete="guestNumber" v-model="apartment.guestNumber"  />
                            </md-field>
                        </div>
                        <div class="md-layout-item md-small-size-100">
                            <md-field >
                                <label for="city">City</label>
                                <md-input name="city" id="city" autocomplete="city" v-model="apartment.city"  />
                            </md-field>
                        </div>
                        <div class="md-layout-item md-small-size-100">
                            <md-field >
                                <label for="country">Country</label>
                                <md-input name="country" id="country" autocomplete="country" v-model="apartment.country"  />
                            </md-field>
                        </div>
                        <div class="md-layout-item md-small-size-100">
                            <md-field >
                                <label for="fromRoomNumber">Room Number from:</label>
                                <md-input type = "number" name="fromRoomNumber" id="fromRoomNumber" autocomplete="fromRoomNumber" v-model="apartment.fromRoomNumber"  />
                            </md-field>
                        </div>
                        <div class="md-layout-item md-small-size-100">
                            <md-field >
                                <label for="toRoomNumber">To:</label>
                                <md-input type = "number" name="toRoomNumber" id="toRoomNumber" autocomplete="toRoomNumber" v-model="apartment.toRoomNumber"  />
                            </md-field>
                        </div>
                    </div>
                    <div class="md-layout md-gutter">
                        <div class="md-layout-item md-small-size-100">
                            <md-field >
                                <label for="fromPrice">Price from: </label>
                                <md-input type = "number" name="fromPrice" id="fromPrice" autocomplete="fromPrice" v-model="apartment.fromPrice"  />
                            </md-field>
                        </div>
                        <div class="md-layout-item md-small-size-100">
                            <md-field >
                                <label for="toPrice">To:</label>
                                <md-input type = "number" name="toPrice" id="toPrice" autocomplete="toPrice" v-model="apartment.toPrice"  />
                            </md-field>
                        </div>
                        <div class="md-layout-item">
                            <md-field>
                            <label for="apartmentType">Apartment type</label>
                                <md-select name="apartmentType" id="apartmentType" v-model="this.apartment.apartmentType" md-dense >
                                    <md-option value="FULL">Full</md-option>
                                    <md-option value="ONE_ROOM">One room</md-option>
                                </md-select>
                            </md-field>
                        </div>
                        <div class="md-layout-item">
                            <md-field>
                            <label for="apartmentStatus">Apartment Status</label>
                                <md-select name="apartmentStatus" id="apartmentStatus" v-model="this.apartment.apartmentStatus" md-dense >
                                    <md-option value="ACTIVE">Active</md-option>
                                    <md-option value="NON_ACTIVE">Non active</md-option>
                                </md-select>
                            </md-field>
                        </div>
                    </div>

                    <!-- USER -->
                    <div class="md-layout md-gutter">
                        <div class="md-layout-item">
                            <md-field>
                            <label for="userType">User Type</label>
                                <md-select name="userType" id="userType" v-model="this.user.userType" md-dense >
                                    <md-option value="ADMINISTRATOR">Administrator</md-option>
                                    <md-option value="HOST">Host</md-option>
                                    <md-option value="GUEST">Guest</md-option>
                                </md-select>
                            </md-field>
                        </div>
                        <div class="md-layout-item">
                            <md-field>
                            <label for="gender">Gender</label>
                                <md-select name="gender" id="gender" v-model="this.user.gender" md-dense >
                                    <md-option value="MALE">Male</md-option>
                                    <md-option value="FEMALE">Female</md-option>
                                    <md-option value="OTHER">Other</md-option>
                                </md-select>
                            </md-field>
                        </div>
                    </div>

                    <!-- RESERVATION -->
                    <div class="md-layout md-gutter">
                        <div class="md-layout-item">
                            <md-field>
                            <label for="reservationStatus">Reservation Status</label>
                                <md-select name="reservationStatus" id="reservationStatus" v-model="this.reservation.reservationStatus" md-dense >
                                    <md-option value="CREATED">Created</md-option>
                                    <md-option value="DENIED">Denied</md-option>
                                    <md-option value="CANCELED">Canceled</md-option>
                                    <md-option value="ACCEPTED">Accepted</md-option>
                                    <md-option value="PASSED">Passed</md-option>
                                </md-select>
                            </md-field>
                        </div>
                    </div>

                </md-card-content>
                
                <md-chip v-if="this.guestNumberFilter" class="md-primary" md-deletable>{{this.apartment.guestNumber}} <md-tooltip md-direction="top">Guest Number</md-tooltip></md-chip>
                <md-chip v-if="this.cityFilter" class="md-primary" md-deletable>{{this.apartment.city}} <md-tooltip md-direction="top">City</md-tooltip></md-chip>
                <md-chip v-if="this.countryFilter" class="md-primary" md-deletable>{{this.apartment.country}} <md-tooltip md-direction="top">Country</md-tooltip></md-chip>
                <md-chip v-if="this.roomNumberFilter" class="md-primary" md-deletable>{{this.apartment.fromRoomNumber}} - {{this.apartment.toRoomNumber}} <md-tooltip md-direction="top">Room Number</md-tooltip></md-chip>
                <md-chip v-if="this.priceFilter" class="md-primary" md-deletable>{{this.apartment.fromPrice}} - {{this.apartment.toPrice}} <md-tooltip md-direction="top">Price range</md-tooltip></md-chip>
                <md-chip v-if="this.typeFilter" class="md-primary" md-deletable>{{this.apartment.apartmentType}} <md-tooltip md-direction="top">Apartment Type</md-tooltip></md-chip>
                <md-chip v-if="this.statusFilter" class="md-primary" md-deletable>{{this.apartment.apartmentStatus}} <md-tooltip md-direction="top">Apartment Status</md-tooltip></md-chip>
                <md-chip v-if="this.userTypeFilter" class="md-primary" md-deletable>{{this.user.userType}} <md-tooltip md-direction="top">User Type</md-tooltip></md-chip>
                <md-chip v-if="this.genderFilter" class="md-primary" md-deletable>{{this.user.gender}} <md-tooltip md-direction="top">Gender</md-tooltip></md-chip>
                <md-chip v-if="this.reservationsStatusFilter" class="md-primary" md-deletable>{{this.reservation.reservationStatus}} <md-tooltip md-direction="top">Reservation Status</md-tooltip></md-chip>

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
            apartmentType: null,
            hasAmenities: null,
            apartmentStatus: null
        },
        user: {
            userType: null,
            gender: null
        },
        reservation: {
            reservationStatus: null
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
        reservationsStatusFilter: false
    }),
    props: {
        
    },
    created() {

    },
    methods: {
        filter() {
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
            
            if(this.apartment.apartmentType === null || this.apartment.apartmentType === "") {
                this.typeFilter = false
            } else {
                this.typeFilter = true
            }

            if(this.apartment.apartmentStatus === null || this.apartment.apartmentStatus === "") {
                this.statusFilter = false
            } else {
                this.statusFilter = true
            }

            if(this.user.userType === null || this.user.userType === "") {
                this.userTypeFilter = false
            } else {
                this.userTypeFilter = true
            }

             if(this.user.gender === null || this.user.gender === "") {
                this.genderFilter = false
            } else {
                this.genderFilter = true
            }

             if(this.reservation.reservationStatus === null || this.reservation.reservationStatus === "") {
                this.reservationsStatusFilter = false
            } else {
                this.reservationsStatusFilter = true
            }
        }
    }

}
</script>

<style lang="scss" scoped>

</style>