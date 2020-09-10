<template>
    <div class = "apartment">
        <form novalidate class="md-layout" @submit.prevent="validateApartment">
            <md-card class="md-layout-item md-size-50 md-small-size-100">
                <md-card-header>
                    <div class="md-title">Apartment</div>
                </md-card-header>
                <md-card-content>
                    <div class="md-layout md-gutter">
                        
                        <div class="md-layout-item md-small-size-50">
                            <md-field :class="getValidationClass('type')">
                                <label for="type">Apartment type</label>
                                <md-select name="type" id="type" v-model="form.type" md-dense >
                                <md-option value="FULL">Full</md-option>
                                <md-option value="ONE_ROOM">One room</md-option>
                                </md-select>
                                <span class="md-error">Room type is required</span>
                            </md-field>
                        </div>
                        <div class="md-layout-item md-small-size-50">
                            <md-field :class="getValidationClass('price')">
                                <label for="price">Price</label>
                                <md-input type="number" id="price" name="price"  v-model="form.price" />
                                <span class="md-error" v-if="!$v.form.price.required">The price is required</span>
                            </md-field>
                            </div>
                    </div>
                    <div class="md-layout md-gutter">
                        <div class="md-layout-item md-small-size-50">
                            <md-field :class="getValidationClass('roomNumber')">
                                <label for="roomNumber">Room number</label>
                                <md-input type="number" id="roomNumber" name="roomNumber" autocomplete="roomNumber" v-model="form.roomNumber" />
                                <span class="md-error" v-if="!$v.form.roomNumber.required">The room number is required</span>
                            </md-field>
                        </div>
                        <div class="md-layout-item md-small-size-50">
                            <md-field :class="getValidationClass('guestNumber')">
                                <label for="guestNumber">Guest number</label>
                                <md-input type="number" id="guestNumber" name="guestNumber" autocomplete="guestNumber" v-model="form.guestNumber" />
                                <span class="md-error" v-if="!$v.form.guestNumber.required">The guest number is required</span>
                            </md-field>
                        </div>
                    </div>
                    <div class="md-layout md-gutter">
                        <div class="md-layout-item md-small-size-50">
                            <md-field :class="getValidationClass('entryTime')">
                                <label for="entryTime">Entry time</label>
                                <md-input type="number" id="entryTime" name="entryTime" autocomplete="14" v-model="form.entryTime" />
                                <span class="md-error" v-if="!$v.form.entryTime.required">Entry time is required</span>
                            </md-field>
                        </div>
                        <div class="md-layout-item md-small-size-50">
                            <md-field :class="getValidationClass('leaveTime')">
                                <label for="leaveTime">Leave time</label>
                                <md-input type="number" id="leaveTime" name="leaveTime" autocomplete="10" v-model="form.leaveTime" />
                                <span class="md-error" v-if="!$v.form.leaveTime.required">Leave time is required</span>
                            </md-field>
                        </div>
                    </div>
                    <div class="md-layout md-gutter">
                        <div class="md-layout-item md-small-size-50">
                            <md-field>
                                <label for="amenities">Amenities</label>
                                <md-select v-model="selectedAmenities" name="amenities" id="amenities" multiple>
                                    <md-option v-for= "item in this.allAmenities" :key="item.id" :value="item.id">{{item.name}}</md-option>
                                </md-select>
                            </md-field>
                        </div>
                        
                        <div class="md-layout-item md-small-size-100">
                        <md-field :class="getValidationClass('location')">
                            <label for="location">Adress</label>
                            <md-input name="location" id="location" v-model="form.location" />
                            <span class="md-error" v-if="!$v.form.location.required">The location is required</span>
                            <span class="md-error" v-else-if="!$v.form.location.minlength">Invalid location</span>
                        </md-field>
                        </div>
                        
                        <div class="md-layout-item md-small-size-50">
                            <md-field>
                                <label>Select images</label>
                                <md-file v-model="selectedImages" accept="image/*" multiple />
                            </md-field>
                        </div>
                    </div> 

                        <!-- Ovo je za datepicker, moramo se dogovoriti kako cemo to odraditi -->
                     <!-- </div>
                    <div class="md-layout md-gutter">
                        <div class="md-layout-item md-small-size-50">
                            <date-range-picker >
                            </date-range-picker>
                        </div>
                    </div> -->

                </md-card-content>
                <md-card-actions>
                    <md-button type="submit" class="md-dense md-raised md-primary">Submit</md-button>
              </md-card-actions>
            </md-card>
        </form>
    </div>
</template>

<script>
import { validationMixin } from 'vuelidate'
  import {
    required,
    minLength,
    // sameAs
    // maxLength
  } from 'vuelidate/lib/validators'
//   import DateRangePicker from 'vue2-daterange-picker'
//   import 'vue2-daterange-picker/dist/vue2-daterange-picker.css'
import axios from 'axios'
export default {
    name: 'Apartment',
    mixins: [validationMixin],
    props: {

    },
    // components: {DateRangePicker},
    data: () => ({
        form: {
            type: String,
            roomNumber: null,
            guestNumber: null,
            location: null,
            rentDates: null, 
            freeDates: null,
            images: [], 
            price: null,
            entryTime: 14,
            leaveTime: 10, 
            amenities: []
        },
        selectedAmenities: [],
        selectedImages: [],
        allAmenities: []
    }),
    validations: {
      form: {
        type: {
            required
        },
        roomNumber: {
            required
        },
        guestNumber: {
            required
        },
        entryTime: {
            required
        }, 
        leaveTime: {
            required
        },
        location: {
            required, 
            minLength: minLength(3)
        },
        price: {
            required
        }
      }
    },
    computed: {
        getAmenities() {
            return this.items.filter(item => this.selectedItems.includes(item.id));
        }
    },
    methods: {
        getValidationClass (fieldName) {
        const field = this.$v.form[fieldName]

        if (field) {
          return {
            'md-invalid': field.$invalid && field.$dirty
          }
        }
      },
      validateApartment () {
        this.$v.$touch()

        if (!this.$v.$invalid) {
          this.saveApartment()
        }
      },
      saveApartment: function() {
        axios.post('http://localhost:8080/PocetniREST/rest/apartment',
                    {
                        type: this.form.type,
                        roomNumber: this.form.roomNumber,
                        guestNumber: this.form.guestNumber,
                        location: {
                            latitude : 0,
                            longitude : 0,
                            address : this.form.address
                        },
                        imageList: [],
                        price: this.form.price,
                        entryTime: this.form.entryTime,
                        leaveTime: this.form.leaveTime,
                        amenities: this.getAmenities,
                        active: true,
                        rentDates: [],
                        freeDates: [],
                        comments: [],
                        hostUsername: "host"

                    })
                    .catch(error => {
                        console.log(error) 
                    });
      },
    },
    created() {  
        axios.get('http://localhost:8080/PocetniREST/rest/amenities')
                            .then(data => { 
                            this.allAmenities = data.data})
                            .catch(error => {
                                console.log(error) 
                            });
    }
}
</script>

<style lang="scss" scoped>
  form {
    position: center;
    top: 0;
    right: 0;
    left: 0;
  }
</style>    