<template>
    <div class = "register">
        <form novalidate class="md-layout md-centered" @submit.prevent="validateUser">
            <md-card class="md-layout-item md-size-50 md-small-size-100">
                <md-card-header>
                    <div class="md-title">Sign up</div>
                </md-card-header>
                <md-card-content>
                    <div class="md-layout md-gutter">
                        <div class="md-layout-item md-small-size-100">
                        <md-field :class="getValidationClass('name')">
                            <label for="name">First Name</label>
                            <md-input name="name" id="name" v-model="form.name" />
                            <span class="md-error" v-if="!$v.form.name.required">The first name is required</span>
                            <span class="md-error" v-else-if="!$v.form.name.minlength">Invalid first name</span>
                        </md-field>
                        </div>

                        <div class="md-layout-item md-small-size-100">
                        <md-field :class="getValidationClass('surname')">
                            <label for="surname">Last Name</label>
                            <md-input name="surname" id="surname" v-model="form.surname"  />
                             <span class="md-error" v-if="!$v.form.surname.required">The last name is required</span>
                             <span class="md-error" v-else-if="!$v.form.surname.minlength">Invalid last name</span>
                        </md-field>
                        </div>
                    </div>
                    <div class="md-layout md-gutter">
                      <div class="md-layout-item md-small-size-100">
                        <md-field :class="getValidationClass('username')">
                            <label for="username">Username</label>
                            <md-input name="username" id="username" v-model="form.username" @change="changeValue()" />
                            <span class="md-error" v-if="!$v.form.username.required">The username is required</span>
                            <span class="md-error" v-else-if="!$v.form.username.usernameExists">Username already exists</span>
                            <span class="md-error" v-else-if="!$v.form.username.minlength">Invalid username</span>
                        </md-field>
                      </div>
                      <div class="md-layout-item md-small-size-100">
                        <md-field :class="getValidationClass('gender')">
                            <label for="gender">Gender</label>
                            <md-select name="gender" id="gender" v-model="form.gender" md-dense placeholder = "Select gender">
                            <md-option value="MALE">Male</md-option>
                            <md-option value="FEMALE">Female</md-option>
                            <md-option value="OTHER">Other</md-option>
                            </md-select>
                            <span class="md-error">The gender is required</span>
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
                  <div v-if="!this.isEdit">
                    <md-button type="submit" class="md-dense md-raised md-primary">Submit</md-button>
                  </div>
                  <div v-else>
                    <md-button class="md-dense md-raised md-primary" @click="saveEdit">Submit</md-button>
                  </div>
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
    sameAs,
    // not
    // maxLength
  } from 'vuelidate/lib/validators'
  import http from '../http-common'

  function usernameExists () {
    return !this.wrongUsername
  }
export default {
    name: 'Register',
    mixins: [validationMixin],
    props: {
        loggedInUser: null,
    },
    data: () => ({
        form: {
            name: null,
            surname: null, 
            username: null,
            gender: null,
            password: null,
            userType: null
        },
        wrongUsername: false,
        isEdit: false
    }),
    validations: {
      form: {
        name: {
          required,
          minLength: minLength(3)
        },
        surname: {
          required,
          minLength: minLength(3)
        },
        username: {
            required,
            minLength: minLength(3),
            usernameExists
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
    },
     clearForm () {
        this.$v.$reset()
        this.form.name = ""
        this.form.surname = ""
        this.form.username = ""
        this.form.gender = null
        this.form.password = ""
        this.form.passwordConfirm = ""
      },
      changeValue() {
        this.wrongUsername = false
      },
      saveUser: function () {
        http.post('user',
                    {
                      username: this.form.username,
                      password: this.form.password,
                      name: this.form.name,
                      surname: this.form.surname,
                      gender: this.form.gender,
                      userType: "GUEST"
                    })
                    .then((response) => { 
                      this.wrongUsername = false
                      console.log(response) })
                    .catch(error => {
                       console.log(error)
                      this.wrongUsername = true
                    }) ;

      },
      saveEdit() {
        if(this.form.password === null) {
          http.put('user',
                    {
                      username: this.form.username,
                      password: this.loggedInUser.password,
                      name: this.form.name,
                      surname: this.form.surname,
                      gender: this.form.gender,
                      userType: this.form.userType,
                    })
                    .then(() => { 
                      this.wrongUsername = false
                      this.$emit('userEdited', this.form) })
                    .catch(() => {
                      this.$emit('globalMessage', 'Username already exists!')
                      this.wrongUsername = true
                    }) ;
      }
        else {
          http.put('user',
                    {
                      username: this.form.username,
                      password: this.form.password,
                      name: this.form.name,
                      surname: this.form.surname,
                      gender: this.form.gender,
                      userType: this.form.userType
                    })
                    .then(() => { 
                      this.wrongUsername = false
                      this.$emit('userEdited', this.form) })
                    .catch(() => {
                      this.$emit('globalMessage', 'Username already exists!')
                      this.wrongUsername = true
                    }) ;
      }
      }
    },
    mounted() {
      if(this.loggedInUser === null) {
        this.isEdit = false;
      } else {
        this.isEdit = true;
      }
      if(this.isEdit){
        this.form.name = this.loggedInUser.name,
        this.form.surname = this.loggedInUser.surname,
        this.form.gender = this.loggedInUser.gender,
        this.form.username = this.loggedInUser.username,
        this.form.userType = this.loggedInUser.userType
    } else {
      this.clearForm()
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