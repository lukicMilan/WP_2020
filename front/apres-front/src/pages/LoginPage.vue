<template>
    <div class="login">
        <form novalidate class="md-layout md-centered">
            <md-card class="md-layout-item md-size-25 md-small-size-25">
                <!-- <md-card-header>
                    <div class="md-title">Login</div>
                </md-card-header> -->

                <md-card-content>
                    <div class="md-layout md-gutter">
                        <div class="md-layout-item md-small-size-100">
                            <md-field>
                                <label for="username">Username</label>
                                <md-input name="username" id="username" v-model="form.username"/>
                                <span class="md-error" v-if="false">The username is required</span>
                                
                            </md-field>
                        </div>

                        <div class="md-layout-item md-small-size-100">
                            <md-field>
                                <label for="password">Password</label>
                                <md-input type = "password" name="password" id="password" v-model="form.password" />
                                <span class="md-error" v-if="false">The password is required</span>
                            </md-field>
                        </div>
                    </div>
                </md-card-content>
                <md-card-actions>
                 <md-button type="submit" class="md-dense md-raised md-primary" v-on:click.prevent ="login">Login</md-button>
                </md-card-actions>
            </md-card>
        </form>
    </div>
</template>

<script>
import http from '../http-common'

export default {
    name: 'Login',
    props: {

    },
    methods: {
        login: function() {
            http.post('user/login', 
                this.form
            )
            .then(data => {
                this.$emit('userLoggedIn', data.data);
            });
        },
        // getValidationClass (fieldName) {
        //     const field = this.$v.form[fieldName];

        //     if(field) {
        //         return {
        //             'md-invalid': field.$invalid && field.$dirty
        //         };
        //     }
        // },
        // validateLogin () {
        //     this.$v.$touch();
        //     if (!this.$v.invalid) {
        //         //this.login();
        //     }
        // }
    },
    data: () => ({
        form: {
            username: null,
            password: null
        }
    }),
    // validations: {
    //     form: {
    //         username: {
    //             required
    //         },
    //         password: {
    //             required
    //         }
    //     }
    // }
}
</script>

<style scoped>

</style>