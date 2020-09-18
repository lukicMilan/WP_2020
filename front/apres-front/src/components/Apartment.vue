<template>
    <div class = "apartment">
        <div v-if="!isHost()">
            <accessDenied/>
        </div>
        <div v-else>
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
                                <md-field>
                                    <label for="amenities">Amenities</label>
                                    <md-select v-model="selectedAmenities" name="amenities" id="amenities" multiple>
                                        <md-option v-for= "item in this.allAmenities" :key="item.id" :value="item">{{item.name}}</md-option>
                                    </md-select>
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
                                    <span class="md-error" v-if="!$v.form.entryTime.between">Entry time must be between 0-24</span>
                                </md-field>
                            </div>
                            <div class="md-layout-item md-small-size-50">
                                <md-field :class="getValidationClass('leaveTime')">
                                    <label for="leaveTime">Leave time</label>
                                    <md-input type="number" id="leaveTime" name="leaveTime" autocomplete="10" v-model="form.leaveTime" />
                                    <span class="md-error" v-if="!$v.form.leaveTime.required">Leave time is required</span>
                                    <span class="md-error" v-if="!$v.form.leaveTime.between">Leave time must be between 0-24</span>
                                </md-field>
                            </div>
                        </div>
                        <div class="md-layout md-gutter">
                            <div class="md-layout-item md-small-size-50">
                                <md-field :class="getValidationClass('price')">
                                    <label for="price">Price</label>
                                    <md-input type="number" id="price" name="price"  v-model="form.price" />
                                    <span class="md-error" v-if="!$v.form.price.required">The price is required</span>
                                </md-field>
                            </div>
                            <div class="md-layout-item md-small-size-50">
                                <md-field>
                                    <label>Select images</label>
                                    <md-file ref="file" v-model="selectedImages" type="file" accept="image/jpg" @change="fileChanged" multiple />
                                </md-field>
                            </div>
                        </div>
                        <div class="md-layout md-gutter">
                            <div class="md-layout-item md-small-size-100">
                            <md-field>
                                <label for="startRentDate">Start rent date</label>
                                <md-datepicker name="startRentDate" id="startRentDate" v-model="form.startRentDate" />
                            </md-field>
                            </div>
                            <div class="md-layout-item md-small-size-100">
                            <md-field>
                                <label for="endRentDate">End rent date</label>
                                <md-datepicker name="endRentDate" id="endRentDate" v-model="form.endRentDate" />
                                </md-field>
                            </div>
                        </div>
                        <div class="md-layout md-gutter">
                            <div class="md-layout-item md-small-size-100">
                            <md-field>
                                <label for="latitude">Latitude</label>
                                <md-input type = "number" autocomplete =0  name="latitude" id="latitude" v-model="form.latitude" />
                            </md-field>
                            </div>
                            <div class="md-layout-item md-small-size-100">
                            <md-field>
                                <label for="longitude">Longitude</label>
                                <md-input type = "number"  autocomplete =0 name="longitude" id="longitude" v-model="form.longitude" />
                                </md-field>
                            </div>
                        </div>
                        <div class="md-layout md-gutter">
                            <div class="md-layout-item md-small-size-50">
                                <md-field :class="getValidationClass('city')">
                                    <label for="city">City</label>
                                    <md-input  id="city" name="city"  v-model="form.city" />
                                    <span class="md-error" v-if="!$v.form.city.required">The city is required</span>
                                </md-field>
                            </div>
                            <div class="md-layout-item md-small-size-50">
                                <div class="md-layout-item md-small-size-100">
                                    <md-field :class="getValidationClass('zipCode')">
                                        <label for="zipCode">Zip Code</label>
                                        <md-input type = "number" name="zipCode" id="zipCode" v-model="form.zipCode" />
                                        <span class="md-error" v-if="!$v.form.zipCode.required">The zip code is required</span>
                                    </md-field>
                                </div>
                            </div>
                        </div>
                        <div class="md-layout md-gutter">
                            <div class="md-layout-item md-small-size-50">
                                <md-field :class="getValidationClass('street')">
                                    <label for="street">Street</label>
                                    <md-input  id="street" name="street"  v-model="form.street" />
                                    <span class="md-error" v-if="!$v.form.street.required">The street is required</span>
                                </md-field>
                            </div>
                            <div class="md-layout-item md-small-size-100">
                                <md-field :class="getValidationClass('number')">
                                    <label for="number">Number</label>
                                    <md-input type = "number" name="number" id="number" v-model="form.number" />
                                    <span class="md-error" v-if="!$v.form.number.required">The zip code is required</span>
                                </md-field>
                            </div>
                        </div>
                    </md-card-content>
                    <md-card-actions>
                        <div v-if = "this.isEdit">
                            <md-button class="md-dense md-raised md-primary" @click = "validateApartment()">Submit</md-button>
                        </div>
                        <div v-if = "!isEdit">
                            <md-button type="submit" class="md-dense md-raised md-primary">Submit</md-button>
                        </div>
                </md-card-actions>
                </md-card>
            </form>
        </div>
    </div>
</template>

<script>
import { validationMixin } from 'vuelidate'
  import {
    required,
    minLength,
    between
    // sameAs
    // maxLength
  } from 'vuelidate/lib/validators'
import AccessDenied from '../pages/AccessDenied'
import http from '../http-common'
export default {
    name: 'Apartment',
    mixins: [validationMixin],
    props: {
        loggedInUser: null,
        id: Number,
        isEdit: Boolean,
        selectedApartment: null
    },
    components: {
        accessDenied: AccessDenied
    },
    data: () => ({
        form: {
            type: null,
            roomNumber: null,
            guestNumber: null,
            
            street: null,
            number:null,
            city: null,
            zipCode: null,
            location: {
                latitude: 0, 
                longitude: 0, 
            },
            rentDates: null, 
            freeDates: null,
            imageList: [], 
            price: null,
            entryTime: 14,
            leaveTime: 10, 
            amenities: [],
            loaded: Number,
            updatingImages: false,
            startRentDate: Date,
            endRentDate: Date
        },
        selectedAmenities: [],
        selectedImages: [],
        allAmenities: [],
        file: null,
        apartment: null,
        comments: []
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
            required,
            between: between(0, 24)
        }, 
        leaveTime: {
            required,
            between: between(0, 24)
        },
        price: {
            required
        },
        street: {
            required,
            minLength: minLength(3)
        },
        city: {
            required,
        },
        number: {
            required
        },
        zipCode: {
            required
        }
      }
    },
    methods: {
        imageLoaded(image) {
            this.form.imageList.push(image);
            console.log(this.form.imageList);
        },
        getBase64(file, callback) {
            var reader = new FileReader();
            reader.addEventListener('load', function () {
                callback(reader.result);
            }, false);
            reader.readAsDataURL(file);
        },
        fileChanged(event) {
            this.form.imageList = [];
            
            this.selectedImages = event.target.files;
            
            this.selectedImages.forEach(element => {
                this.getBase64(element, this.imageLoaded);
            });
            

            // event.target.files.forEach(element => {
            //     const formData = new FormData();
            //     formData.append('image', element, element.name);
            //     //http.post('apartment/'+data.data.id+'/image', formData)
            //     http.post('apartment/'+'1'+'/image', formData)
            //         .data(() => {
            //             console.log("USPEH USPEH");
            //         })
            //         .catch(() => {
            //             console.log("NESTO NE STIMA");
            //         });
            // });
        },
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

        if (!this.$v.$invalid && !this.isEdit) {
          this.saveApartment()
        } else if(!this.$v.$invalid  && this.isEdit) {
            this.saveEdit()
        }
      },
      saveApartment: function() {
        http.post('apartment',
                    {
                        type: this.form.type,
                        roomNumber: this.form.roomNumber,
                        guestNumber: this.form.guestNumber,
                        latitude : this.form.latitude,
                        longitude : this.form.longitude,
                        city: this.form.city,
                        street: this.form.street,
                        zipCode: this.form.zipCode,
                        number: this.form.number,
                        imageList: this.form.imageList,
                        price: this.form.price,
                        entryTime: this.form.entryTime,
                        leaveTime: this.form.leaveTime,
                        amenities: this.selectedAmenities,
                        active: true,
                        fancyRentDates: [this.form.startRentDate.getTime(), this.form.endRentDate.getTime()],
                        freeDates: [],
                        comments: [],
                        hostUsername: this.loggedInUser.username
                    })
                    .then(
                        this.$emit('globalMessage', 'Apartment added successfully.'),
                        this.$router.push('apartmentTable')
                    )
                    .catch(error => {
                        if(error.response.status === 403) {
                            this.$emit('globalMessage', 'Only apartment hosts can add a new apartment.')
                        }
                    });
      },
        isHost() {
            if(this.apartment === null) {
                return true
            }
            if(this.loggedInUser.userType === "HOST" && this.loggedInUser.username === this.apartment.hostUsername) {
                return true
            } else {
                return false
            }
        },
        saveEdit()  {
            let username = this.loggedInUser.username;
            if(this.isHost()) {
                username = this.selectedApartment.hostUsername;
            }
            http.put('apartment',
                    {
                        id: this.id,
                        type: this.form.type,
                        roomNumber: this.form.roomNumber,
                        guestNumber: this.form.guestNumber,
                        latitude : this.form.latitude,
                        longitude : this.form.longitude,
                        city: this.form.city,
                        street: this.form.street,
                        zipCode: this.form.zipCode,
                        number: this.form.number,
                        imageList: this.form.imageList,
                        price: this.form.price,
                        entryTime: this.form.entryTime,
                        leaveTime: this.form.leaveTime,
                        amenities: this.selectedAmenities,
                        active: this.selectedApartment.active,
                        fancyRentDates: [this.form.startRentDate.getTime(), this.form.endRentDate.getTime()],
                        freeDates: [],
                        comments: this.comments,
                        hostUsername: username
                    })
                    .then(() => {
                        this.$emit('globalMessage', 'Apartment edited successfully.')
                            this.$emit('apartmentAdded')})
                    .catch(error => {
                        if(error.response.status === 403)
                            this.$emit('globalMessage', 'Only apartment hosts and admins can edit an apartment.')
                    });
        }
    },
    mounted() {  
        if(this.isEdit) {
                this.form.type = this.selectedApartment.type;
                this.form.roomNumber = this.selectedApartment.roomNumber;
                this.form.guestNumber = this.selectedApartment.guestNumber;
                this.form.latitude = this.selectedApartment.latitude;
                this.form.longitude = this.selectedApartment.longitude;
                this.form.city = this.selectedApartment.city;
                this.form.street = this.selectedApartment.street;
                this.form.zipCode = this.selectedApartment.zipCode;
                this.form.number = this.selectedApartment.number;
                this.form.imageList = this.selectedApartment.imageList;
                this.form.price = this.selectedApartment.price;
                this.form.entryTime = this.selectedApartment.entryTime;
                this.form.leaveTime = this.selectedApartment.leaveTime;
                this.form.selectedAmenities = this.selectedApartment.amenities;
                this.form.startRentDate = new Date(this.selectedApartment.rentDates[0]);
                this.form.endRentDate =  new Date(this.selectedApartment.rentDates[1]);
        } 
        http.get('amenities').then(data => {this.allAmenities = data.data})
    },
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