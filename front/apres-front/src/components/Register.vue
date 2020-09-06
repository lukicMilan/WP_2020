<template>
    <div class = "register">
        <form novalidate class="md-layout" @submit.prevent="validateUser">
            <md-card class="md-layout-item md-size-50 md-small-size-100">
                <md-card-header>
                    <div class="md-title">Sign up</div>
                </md-card-header>
                <md-card-content>
                    <div class="md-layout md-gutter">
                        <div class="md-layout-item md-small-size-100">
                        <md-field :class="getValidationClass('firstName')">
                            <label for="first-name">First Name</label>
                            <md-input name="first-name" id="first-name" v-model="form.firstName" />
                            <span class="md-error" v-if="!$v.form.firstName.required">The first name is required</span>
                            <span class="md-error" v-else-if="!$v.form.firstName.minlength">Invalid first name</span>
                        </md-field>
                        </div>

                        <div class="md-layout-item md-small-size-100">
                        <md-field :class="getValidationClass('lastName')">
                            <label for="last-name">Last Name</label>
                            <md-input name="last-name" id="last-name" v-model="form.lastName"  />
                             <span class="md-error" v-if="!$v.form.lastName.required">The last name is required</span>
                             <span class="md-error" v-else-if="!$v.form.lastName.minlength">Invalid last name</span>
                        </md-field>
                        </div>
                    </div>
                    <div class="md-layout-item md-small-size-100">
                        <md-field :class="getValidationClass('username')">
                            <label for="username">Username</label>
                            <md-input name="username" id="username" v-model="form.username" />
                            <span class="md-error" v-if="!$v.form.username.required">The username is required</span>
                            <span class="md-error" v-else-if="!$v.form.username.minlength">Invalid username</span>
                        </md-field>
                    </div>
                    <div class="md-layout md-gutter">
                        <div class="md-layout-item md-small-size-100">
                        <md-field :class="getValidationClass('gender')">
                            <label for="gender">Gender</label>
                            <md-select name="gender" id="gender" v-model="form.gender" md-dense placeholder = "Select gender">
                            <md-option value="male">Male</md-option>
                            <md-option value="female">Female</md-option>
                            <md-option value="other">Other</md-option>
                            </md-select>
                            <span class="md-error">The gender is required</span>
                        </md-field>
                        </div>
                        <div class="md-layout-item md-small-size-100">
                            <md-field :class="getValidationClass('age')">
                                <label for="age">Age</label>
                                <md-input type="number" id="age" name="age" autocomplete="age" v-model="form.age" />
                                <span class="md-error" v-if="!$v.form.age.required">The age is required</span>
                            </md-field>
                        </div>
                    </div>
                    
                    <div class="md-layout md-gutter">
                        <div class="md-layout-item md-small-size-100">
                        <md-field :class="getValidationClass('password')">
                            <label for="password">Password</label>
                            <md-input type = "password" name="password" id="password" v-model="form.password" />
                            <span class="md-error" v-if="!$v.form.password.required">The password is required</span>
                            <span class="md-error" v-else-if="!$v.form.password.minlength">Invalid password</span>
                        </md-field>
                        </div>
                        <div class="md-layout-item md-small-size-100">
                        <md-field :class="getValidationClass('passwordConfirm')">
                            <label for="passwordConfirm">Confirm password</label>
                            <md-input type = "password" name="passwordConfirm" id="passwordConfirm" v-model="form.passwordConfirm"  />
                             <span class="md-error" v-if="!$v.form.passwordConfirm.sameAsPassword">Passwords do not match</span>
                        </md-field>
                        </div>
                    </div>

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
    email,
    minLength,
    sameAs
    // maxLength
  } from 'vuelidate/lib/validators'
export default {
    name: 'Register',
    mixins: [validationMixin],
    props: {
        
    },
    data: () => ({
        form: {
            firstName: null,
            lastName: null, 
            username: null,
            gender: null,
            password: null,
            confirmPassword: null,
            age: null
        }
    }),
    validations: {
      form: {
        firstName: {
          required,
          minLength: minLength(3)
        },
        lastName: {
          required,
          minLength: minLength(3)
        },
        username: {
            required,
            minLength: minLength(3)
        },
        password: {
            required,
            minLength: minLength(6)
        },
        passwordConfirm: {
            sameAsPassword: sameAs('password')
        },
        gender: {
          required
        },
        email: {
          required,
          email
        },
        age: {
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
      validateUser () {
        this.$v.$touch()

        if (!this.$v.$invalid) {
          this.saveUser()
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