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
                    <md-button>RESERVE</md-button>
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
                    <md-button>RESERVE</md-button>
                </div>
            </md-tab>
            <md-tab md-label="Comments">

            </md-tab>
        </md-tabs>
    </div>
</template>

<script>
export default {
    props: {
        selectedApartment: null,
        loggedInUser: null
    },
    data: function() {
        return {
            amenityTypes: [],
            active: "ACTIVE",
            nonActive: "NON ACTIVE",
            address: ""
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
        }
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
        }

        this.amenityTypes = types;
    }
    
}
</script>
<style scoped>
    .ApartmentDetails {
        max-height: 50%;
        min-width: 25%;
        min-width: 50%;
    }
</style>