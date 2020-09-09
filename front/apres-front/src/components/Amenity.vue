<template>
    <div class = "amenity">
        <form novalidate class="md-layout" @submit.prevent="validateAmenity">
            <md-card class="md-layout-item md-size-50 md-small-size-100">
                <md-card-header>
                    <div class="md-title">Amenity</div>
                </md-card-header>
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
                    <md-button type="submit" class="md-dense md-raised md-primary" >Submit</md-button>
                </md-card-actions>
            </md-card>
        </form> 
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

    },
    data: () => ({
        form: {
            type: null,
            name: null
        }
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
    saveAmenity: function() {
        axios.post('http://localhost:8080/PocetniREST/rest/amenities',
                    {
                        type: this.form.type,
                        name: this.form.name
                    })
                    .then((response) => { console.log(response) })
                    .catch(error => {
                        console.log(error) 
                    });
      }
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