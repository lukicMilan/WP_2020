<template>
    <div class = "apartmentComment" >
        <div>
            <form novalidate class="md-layout" @submit.prevent="validateComment" >
                <md-card class="md-layout-item md-size-50 md-small-size-100">
                    <md-card-content>
                        <div class="md-layout-item md-small-size-100">
                            <md-field :class="getValidationClass('comment')">
                                <label for="comment">Comment</label>
                                <md-input name="comment" id="comment" autocomplete="comment" v-model="form.comment" />
                                <span class="md-error" v-if="!$v.form.comment.required">You must enter a comment!</span>
                            </md-field>
                        </div>
                            <div class="md-layout-item md-small-size-100">
                                <vue-stars
                                @input = "setRating($event)"
                                name="rating"
                                :max="5"
                                :readonly="false"
                                />
                                <label for="rating">Rate apartment</label>
                        </div>
                    </md-card-content>
                    <md-card-actions>
                        <div>
                            <md-button type = "submit" class="md-dense md-raised md-primary" >Submit</md-button>
                        </div>
                    </md-card-actions>
                </md-card>
            </form> 
        </div>
    </div>
</template>

<script>
import { required } from 'vuelidate/lib/validators'
import http from '../http-common'
import { validationMixin } from 'vuelidate'
export default {
    name: 'ApartmentComment',
    mixins: [validationMixin],
    props: {
        loggedInUser: null,
        apartmentId: null
    },
    data: () => ({
        form: {
            comment: null
        },
        rating: 0
    }),
     validations: {
        form: {
            comment: {
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
        validateComment () {
            this.$v.$touch()

            if (!this.$v.$invalid) {
            this.saveComment()
            }
        },
        saveComment: function() {
            console.log(this.rating)
            http.post('apartmentComment',
                        {
                            username: this.loggedInUser.username,
                            apartment: this.apartmentId,
                            comment: this.form.comment,
                            stars: this.rating
                        })
                        .then(data => {
                            console.log(data.data)
                            // this.$emit('commentAdded', data.data)
                        })
                        .catch(error => {
                            console.log(error) 
                        });
        },
        setRating: function(rating){
            this.rating = rating;
        }
    }
    
}
</script>

<style lang="scss" scoped>

</style>