<template>
  <v-card class="px-4">
    <v-card-title color="blue">
      <v-icon size="40" color=blue> mdi-account-outline</v-icon>
      Sign Up
      <v-spacer></v-spacer>
      <v-icon @click="(event) => closeDialog(event)" color="blue">mdi-window-close</v-icon>
    </v-card-title>
    <v-card-text>
      <v-form ref="signUpForm" v-model="valid">
        <v-row>
          <v-col cols="12" sm="6" md="6">
            <v-text-field v-model="newUser.firstName" :rules="[rules.required]" maxlength="25" label="First Name"
                          required>
            </v-text-field>
          </v-col>
          <v-col cols="12" sm="6" md="6">
            <v-text-field v-model="newUser.lastName" :rules="[rules.required]" maxlength="25" label="Last Name"
                          required>
            </v-text-field>
          </v-col>
          <v-col cols="12">
            <v-text-field v-model="newUser.email" :rules="emailRules" label="E-mail" required>
            </v-text-field>
          </v-col>
          <v-col cols="12">
            <v-text-field v-model="newUser.password" :rules="[rules.required, rules.min]"
                          :append-icon="showPassword?'mdi-eye':'mdi-eye-off'"
                          :type="showPassword ? 'text' : 'password'"
                          label="Password" hint="At least 8 characters" counter
                          @click:append="showPassword = !showPassword">
            </v-text-field>
          </v-col>
          <v-col cols="12">
            <v-text-field v-model="verifyPassword"
                          :append-icon="showPassword?'mdi-eye':'mdi-eye-off'"
                          :type="showPassword ? 'text' : 'password'"
                          :rules="[rules.required, passwordMatch]"
                          counter
                          @click:append="showPassword = !showPassword"
                          label="Confirm Password" required>
            </v-text-field>
          </v-col>
          <v-col v-if="failMessage != null" cols="6" class="red--text">
            {{ this.failMessage }}
          </v-col>
          <v-spacer></v-spacer>
          <v-col class="d-flex ml-auto" cols="12" sm="3" xsm="12">
            <v-btn :disabled="!valid" color="blue" @click="(event) => validate(event)">Register</v-btn>
          </v-col>
        </v-row>
        <v-row>
          <v-col>Already have an account?
            <u class="blue--text" @click="(event) => openSignInDialog(event)">Sign In</u>
          </v-col>
        </v-row>
      </v-form>
    </v-card-text>
  </v-card>
</template>

<script>

import {eventBus} from "@/main";

export default {
  name: "Registration",
  data: () => ({
    newUser: {
      firstName: "", lastName: "", email: "", password: ""
    },
    verifyPassword: "",
    valid: true,
    showPassword: false,
    rules: {
      required: value => !!value || "Required.",
      min: v => (v && v.length >= 8) || "Min 8 characters"
    },
    emailRules: [
      v => !!v || "Required",
      v => /.+@.+\..+/.test(v) || "E-mail is not valid"
    ],
    successMessage: "Registration is successfull!",
    failMessage: null,
  }),
  props: {
    signUpDialog: Boolean
  },
  methods: {
    closeDialog(event) {
      this.$emit('closeSignUp', event);
    },
    openSignInDialog(event) {
      this.closeDialog(event)
      this.$emit('openSignIn', event);
    },
    validate(event) {
      if (this.$refs.signUpForm.validate()) {
        this.$http.post("http://localhost:8080/auth/signUp", this.newUser).then(response => {
              this.newUser = response.data;
              eventBus.$emit("showSuccessReg", this.successMessage)
              this.openSignInDialog(event);
            }, (response) => {
              console.log(response);
              this.failMessage = "User with such email already exists";
            }
        )
      }
    }
  },
  computed: {
    passwordMatch() {
      return () => this.newUser.password === this.verifyPassword || "Password must match";
    }
  },
}
</script>

<style scoped>

</style>