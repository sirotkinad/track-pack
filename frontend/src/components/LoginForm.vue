<template>
  <v-card class="px-4">
    <v-card-title color="blue">
      <v-icon size="40" color=blue> mdi-account</v-icon>
      {{ this.message }}
      <v-spacer></v-spacer>
      <v-icon @click="(event) => closeDialog(event)" color="blue">mdi-window-close</v-icon>
    </v-card-title>
    <v-card-text>
      <v-form ref="signInForm" v-model="valid">
        <v-row>
          <v-col cols="12">
            <v-text-field v-model="registeredUser.email" :rules="emailRules" label="E-mail" required>
            </v-text-field>
          </v-col>
          <v-col cols="12">
            <v-text-field v-model="registeredUser.password" :rules="[passwordRules.required, passwordRules.min]"
                          :append-icon="showPassword?'mdi-eye':'mdi-eye-off'"
                          :type="showPassword ? 'text' : 'password'"
                          label="Password" hint="At least 8 characters" counter
                          @click:append="showPassword = !showPassword">
            </v-text-field>
          </v-col>
          <v-col class="d-flex" cols="12" sm="6" xsm="12">
          </v-col>
          <v-col v-if="failMessage != null" cols="7" class="red--text">
            {{ this.failMessage }}
          </v-col>
          <v-spacer></v-spacer>
          <v-col class="d-flex" cols="12" sm="3" xsm="12" align-end>
            <v-btn :disabled="!valid" color="blue" @click="validate"> Login</v-btn>
          </v-col>
        </v-row>
        <v-row>
          <v-col>Don't have an account yet?
            <u class="blue--text" @click="(event) => openSignUpDialog(event)">Sign Up</u>
          </v-col>
        </v-row>
      </v-form>
    </v-card-text>
  </v-card>
</template>

<script>

import {eventBus} from "@/main";

export default {
  name: "Login",
  data: () => ({
    registeredUser: {
      id: "", email: "", password: "", token: ""
    },
    showPassword: false,
    valid: true,
    emailRules: [
      v => !!v || "Required",
      v => /.+@.+\..+/.test(v) || "E-mail is not valid"
    ],
    passwordRules: {
      required: value => !!value || "Required.",
      min: v => (v && v.length >= 8) || "Min 8 characters"
    },
    message: "Sign In",
    failMessage: null
  }),
  created() {
    eventBus.$on("showSuccessReg", (successMessage) => {
      this.message = successMessage
    })
  },
  props: {
    signInDialog: Boolean,
  },
  methods: {
    closeDialog(event) {
      this.$emit('closeSignIn', event);
    },
    openSignUpDialog(event) {
      this.closeDialog(event)
      this.$emit('openSignUp', event);
    },
    validate() {
      if (this.$refs.signInForm.validate()) {
        this.$http.post("http://localhost:8080/auth/signIn", this.registeredUser).then(response => {
              this.registeredUser = response.data;
              localStorage.setItem(this.registeredUser.email, JSON.stringify(this.registeredUser));
              this.$emit("close")
              eventBus.$emit("getUserIdAndToken", this.registeredUser.id)
              this.$emit("isAuthorized", this.registeredUser)
            }, (response) => {
              console.log(response);
              this.failMessage = "Invalid email or password";
            }
        )
      }
    }
  }
}
</script>

<style scoped>

</style>