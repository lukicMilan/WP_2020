<template>
    <div class = "apartment">
        <form novalidate class="md-layout" @submit.prevent="validateApartment">
            <md-card class="md-layout-item md-size-50 md-small-size-100">
                <md-card-header>
                    <div class="md-title">Apartment</div>
                </md-card-header>
                <md-card-content>
                    <div class="md-layout md-gutter">
                        <div class="md-layout-item md-small-size-100">
                        <md-field :class="getValidationClass('name')">
                            <label for="name">Name</label>
                            <md-input name="name" id="name" v-model="form.name" />
                            <span class="md-error" v-if="!$v.form.name.required">The name is required</span>
                            <span class="md-error" v-else-if="!$v.form.name.minlength">Invalid name</span>
                        </md-field>
                        </div>
                        <div class="md-layout-item md-small-size-50">
                            <md-field :class="getValidationClass('type')">
                                <label for="type">Apartment type</label>
                                <md-select name="type" id="type" v-model="form.type" md-dense >
                                <md-option value="full">Full</md-option>
                                <md-option value="oneRoom">One room</md-option>
                                </md-select>
                                <span class="md-error">Room type is required</span>
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
                                <label for="entryTime">Amenities</label>
                                <md-select v-model="selectedAmenities" name="amenities" id="amenities" multiple>
                                    <md-option value="hot-tub">Hot tub</md-option>
                                    <md-option value="room-service">Room Service</md-option>
                                </md-select>
                            </md-field>
                        </div>
                        <div class="md-layout-item md-small-size-50">
                            <md-field>
                                <label>Select images</label>
                                <md-file v-model="selectedImages" accept="image/*" multiple />
                            </md-field>
                        </div>
                    </div>  
                    <div>
                        <map>
                        </map>
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
import Map from '@/components/Map.vue'
export default {
    name: 'Apartment',
    mixins: [validationMixin],
    props: {

    },
    // components: {DateRangePicker},
    data: () => ({
        form: {
            name: null,
            type: null,
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
        selectedImages: []
    }),
    component: {
        Map
    }, 
    validations: {
      form: {
        name: {
            required,
            minLength: minLength(3)
        },
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
        }
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
      }
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