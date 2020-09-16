<template>
    <div class = "ApartmentDetails">
        <md-tabs>
            <md-tab md-label="Basic info">
                <md-field>
                    <label>Apartment type</label>
                    <md-input v-model="selectedApartment.type" readonly/>
                </md-field>
                <md-field>
                    <label>Room number</label>
                    <md-input v-model="selectedApartment.roomNumber" readonly/>
                </md-field>
                <md-field>
                    <label>Guest number</label>
                    <md-input v-model="selectedApartment.guestNumber" readonly/>
                </md-field>
                <md-field>
                    <label>Location</label>
                    <md-input v-model="address" readonly></md-input>
                </md-field>
                <md-field>
                    <label>Host username</label>
                    <md-input v-model="selectedApartment.hostUsername" readonly></md-input>
                </md-field>
                <md-field>
                    <label>Price</label>
                    <md-input v-model="selectedApartment.price" readonly></md-input>
                </md-field>
                <md-field>
                    <label>Entry time</label>
                    <md-input v-model="selectedApartment.entryTime" readonly></md-input>
                </md-field>
                <md-field>
                    <label>Leave time</label>
                    <md-input v-model="selectedApartment.leaveTime" readonly></md-input>
                </md-field>
                <md-field v-if="this.selectedApartment.active">
                    <label>Active</label>
                    <md-input v-model="active" readonly>
                    </md-input>
                </md-field>
                 <md-field v-if="!this.selectedApartment.active">
                    <label>Active</label>
                    <md-input v-model="nonActive" readonly>
                    </md-input>
                </md-field>
                    
                <div v-if="canReserve">
                    <span>
                    <md-button  @click="createReservation" class="md-raised md-primary">RESERVE</md-button>
                    </span>
                </div>
            </md-tab>
            <md-tab md-label="Amenities">
                
                <md-list class = "md-double-line">
                    <div v-for="(type, index) in amenityTypes" :key="index">
                        <md-subheader>
                            {{type}}
                        </md-subheader>

                        <div v-for="(amenity, index) in selectedApartment.amenities" :key="index">
                            <md-list-item v-if="amenity.type===type">
                                <div class="md-list-item-text">
                                    {{amenity.name}}
                                </div>
                            </md-list-item>
                        </div>
                    </div>
                </md-list>
                <div v-if="canReserve">
                    <span>
                    <md-button  @click="createReservation" class="md-raised md-primary">RESERVE</md-button>
                    </span>
                </div>
            </md-tab>
            <md-tab md-label="Images">
                    <ul
                    style="margin: 0; padding: 0">
                        <li
                            v-for="(image, index) in media"
                            :key="index"
                            style="display: inline-block; margin: 0 5px 5px 0"
                        >
                            <img
                            :src="image"
                            style="height: 100px; cursor: pointer"
                            @click="openGallery(index)"
                            >
                        </li>
                    </ul>
                <div v-if="canReserve">
                    <span>
                        <md-button @click="createReservation">RESERVE</md-button>
                    </span>
                </div>   
            </md-tab>
            <md-tab md-label="Availability">
                <v-calendar :available-dates='[{start: startRentDate, end: endRentDate}]' is-inline></v-calendar>
                <div v-if="canReserve" >
                    <span>
                    <md-button @click="createReservation">RESERVE</md-button>
                    </span>
                </div>
            </md-tab>
            <md-tab md-label="Comments">
                <div v-if="canReserve">
                    <span>
                    <md-button @click="createReservation">RESERVE</md-button>
                    </span>
                </div>   
            </md-tab>
        </md-tabs>
    </div>
</template>

<script>

export default {
    components: {
        
    },
    props: {
        selectedApartment: null,
        loggedInUser: null
    },
    data: function() {
        return {
            amenityTypes: [],
            active: "ACTIVE",
            nonActive: "NON ACTIVE",
            address: "",
            startRentDate: Date,    
            endRentDate: Date,
            selectedDate: Date,
            media: [],
        }
    },
    computed: {
        canReserve: function() {
            if(this.loggedInUser === null) {
                return false;
            }
            if(this.loggedInUser.userType === "GUEST") {
                return true;
            }
            return false;
        },
    },
    mounted: function() {
        let types = [];
        
        if(this.selectedApartment !== null) {
            this.address = this.selectedApartment.number + " " + this.selectedApartment.street + ", " + this.selectedApartment.city
            this.selectedApartment.amenities.forEach(amenity => {
                if(!types.includes(amenity.type)) {
                    types.push(amenity.type);
                }
            });
            this.startRentDate = new Date(this.selectedApartment.rentDates[0]);
            this.endRentDate = new Date(this.selectedApartment.rentDates[1]); 
            this.selectedApartment.imageList.forEach(element => {
                this.media.push(element);
            });
        }

        this.amenityTypes = types;
    },
    methods: {
        openGallery(index) {
            //this.$refs.lightBox.showImage(index);
            this.$emit('openGallery', {media:this.media, index:index});
            
        },
        createReservation: function() {
            this.$emit('activateReservation', this.selectedApartment);
        }
    }
}
</script>
<style scoped>
    .ApartmentDetails {
        min-height: 25%;
        max-height: 50%;
        min-width: 25%;
        min-width: 50%;
    }
</style>