<template>
    <div>
        <div v-if="!allowed">
            <access-denied> </access-denied>
        </div>
        <div v-if="allowed">
            <md-steppers>
                <md-step id="first" md-label="Select dates">
                    <v-date-picker v-model="selectedDates" 
                    :available-dates='availableDates'  
                    :columns="$screens({ default: 1, lg: 2 })"
                    mode="range"
                    :select-attribute="selectDragAttribute"
                    :drag-attribute="selectDragAttribute"
                    @drag="dragValue = $event"
                    is-inline>
                        <div slot="day-popover" slot-scope="{ format }">
                            {{ format(dragValue ? dragValue.start : selectedDates.start, 'MMM D') }}
                            -
                            {{ format(dragValue ? dragValue.end : selectedDates.end, 'MMM D') }}
                        </div>
                    </v-date-picker>

                    <span>
                        Night number {{nightNumber}}
                    </span>
                </md-step>

                <md-step id="second" md-label="Message for host">
                    <md-field>
                        <label>Message</label>
                        <md-textarea v-model="messageForHost"></md-textarea>
                    </md-field>
                </md-step>

                <md-step id="third" md-label="Confirm reservation">
                    <div>
                        <md-field>
                            <label>Start date</label>
                            <md-input v-model="getFormatedStartDate" disabled></md-input>
                        </md-field>
                        <md-field>
                            <label>End date</label>
                            <md-input v-model="getFormatedEndDate" disabled></md-input>
                        </md-field>
                        <md-field>
                            <label>Night number</label>
                            <md-input v-model="nightNumber" disabled></md-input>
                        </md-field>
                        <md-field>
                            <label>Total price</label>
                            <md-input v-model="totalPrice" disabled></md-input>
                        </md-field>
                    </div>
                    <span>
                        <div>
                            <md-button class="md-dense md-raised md-primary" @click="confirmReservation">Confirm</md-button>
                            <md-button class="md-dense md-raised md-secondary" @click="cancel">Cancel</md-button>
                        </div>
                    </span>
                </md-step>
            </md-steppers>
        </div>
    </div>
</template>

<script>
import AccessDeniedVue from '../pages/AccessDenied.vue';
import format from 'date-fns/format';
import http from '../http-common';

export default {
    components: {
        accessDenied: AccessDeniedVue,
    },
    props: {
        selectedApartment: null,
        loggedInUser: null,
    },
    data: function() {
        return {
            dragValue: null,
            selectedDates: {
                start: null,
                end: null,
                span: null
            },
            startRentDate: Date,
            endRentDate: Date,
            reservationDTO: {
                apartmentId: Number,
                date: String,
                nights: Number,
                totalPrice: Number,
                welcomeNote: String,
                guestUsername: String,
                hostUsername:String,
                status: "CREATED"
            },
            messageForHost: "",

            availableDates: [],

        }
    },
    mounted: function() {
        if(this.selectedApartment !== null) {
            this.startRentDate = new Date(this.selectedApartment.rentDates[0]);
            this.endRentDate = new Date(this.selectedApartment.rentDates[1]); 
            http.get('reservation/apartment/' + this.selectedApartment.id)
            .then(data => {
                let reservations = data.data;
                let activeReservations = [];
                reservations.forEach(element => {
                    if(element.status==="CREATED"||element.status==="ACCEPTED") {
                        activeReservations.push(element);
                    }
                });
                alert(JSON.stringify(activeReservations));
                
            });
        }
    },
    computed: {
        selectDragAttribute() {
            return {
                popover: {
                visibility: 'hover',
                isInteractive: false, // Defaults to true when using slot
                },
            };
        },
        nightNumber() {
            if(this.selectedDates===null) {
                return 0;
            } else {
                return (this.selectedDates.end - this.selectedDates.start)/86400000;
            }
        },
        totalPrice() {
            if(this.selectedDates===null||this.selectedApartment===null) {
                return 0;
            } else {
                return this.nightNumber*this.selectedApartment.price;
            }
        },
        //PROBLEM NIJE OVDE, problem nastaje jer je bajndovano preko v-modela pa on ocekuje seter neki kao
        //TODO naci nacin da se drugacije prikaze ovo odavde, tipa preko {{}} samo nesto lepo da bude
        getFormatedStartDate() {
            if(this.selectedDates!==null) {
                if(this.selectedDates.start !== null) {
                    return format(this.selectedDates.start, 'yyyy-MM-dd');
                }
            }
            return "";
        },
        getFormatedEndDate() {
            if(this.selectedDates!==null) {
                if(this.selectedDates.end !== null) {
                    return format(this.selectedDates.end, 'yyyy-MM-dd');
                }
            }
            return "";
        },
        allowed() {
            if(this.selectedApartment !== null && this.loggedInUser.userType==="GUEST") {
                return true;
            } else {
                return false;
            }
        }
    },
    methods: {
        cancel() {
            this.$router.push('/apartmentTable');
        },
        confirmReservation() {
            this.reservationDTO.apartmentId = this.selectedApartment.id;
            this.reservationDTO.date = this.getFormatedStartDate;
            this.reservationDTO.nights = this.nightNumber;
            this.reservationDTO.totalPrice = this.totalPrice;
            this.reservationDTO.welcomeNote = this.messageForHost;
            this.reservationDTO.guestUsername = this.loggedInUser.username;
            this.reservationDTO.hostUsername = this.selectedApartment.hostUsername;
            alert(JSON.stringify(this.reservationDTO));
            http.post('reservation', JSON.stringify(this.reservationDTO));
        },
    }
}
</script>

