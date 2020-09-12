<template>
    <div class = "amenity" >
        <div>
            <form novalidate class="md-layout" @submit.prevent="validateAmenity">
                <md-card class="md-layout-item md-size-50 md-small-size-100">
                    <div v-if = "isEdit == false">
                        <md-card-header>
                            <div class="md-title">Amenity</div>
                        </md-card-header>
                    </div>
                    <div v-else>
                        <md-card-header>
                            <div class="md-title">Edit amenity</div>
                        </md-card-header>
                    </div>
                    <md-card-content>
                        <div class="md-layout md-gutter">
                            <div class="md-layout-item md-small-size-100">
                                <md-field :class="getValidationClass('type')">
                                    <label for="type">Type</label>
                                    <md-input name="type" id="type" autocomplete="type" v-model="form.type"  />
                                    <span class="md-error" v-if="!$v.form.type.required">The type of amenity is required</span>
                                </md-field>
                            </div>

                            <div class="md-layout-item md-small-size-100">
                                <md-field :class="getValidationClass('name')">
                                    <label for="name">Name</label>
                                    <md-input name="name" id="name" autocomplete="name" v-model="form.name" />
                                    <span class="md-error" v-if="!$v.form.name.required">The name is required</span>
                                </md-field>
                            </div>
                        </div>
                    </md-card-content>
                    <md-card-actions>
                        <div v-if="this.isEdit == false">
                            <md-button type="submit" class="md-dense md-raised md-primary" >Submit</md-button>
                        </div>
                        <div v-else>
                            <md-button @click="saveEdit()" class="md-dense md-raised md-primary" >Submit</md-button>
                        </div>
                    </md-card-actions>
                </md-card>
            </form> 
        </div>
    </div>
</template>

<script>
import { required } from 'vuelidate/lib/validators'
import { validationMixin } from 'vuelidate'
import axios from 'axios'
export default {
    name: 'Amenity',
    mixins: [validationMixin],
    props: {
        changeType: String,
        changeName: String,
        isEdit: Boolean
    },
    data: () => ({
        form: {
            type: null,
            name: null
        },
        edit: false
    }),
    validations: {
        form: {
            type: {
                required
            },
            name: {
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
      validateAmenity () {
        this.$v.$touch()

        if (!this.$v.$invalid) {
          this.saveAmenity()
        }
      },
    saveEdit () {
        axios.put('http://localhost:8080/PocetniREST/rest/amenities',
                {
                    type: this.form.type,
                    name: this.form.name
                })
                .then((response) => { console.log(response) })
                .catch(error => {
                    console.log(error) 
            });
    },
    saveAmenity: function() {
        axios.post('http://localhost:8080/PocetniREST/rest/amenities',
                    {
                        type: this.form.type,
                        name: this.form.name
                    })
                    .then(data => {
                        this.$emit('amenityAdded', data.data);
                    })
                    .catch(error => {
                        console.log(error) 
                    });
      }
    },
    mounted () {
        this.form.type = this.changeType;
        this.form.name = this.changeName;
        this.edit = this.isEdit;
        console.log(this.isEdit)
    }
}
</script>

<style lang="scss" scoped>

</style>