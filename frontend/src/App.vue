<template>
  <v-app>
    <v-toolbar app dark class="indigo">
      <v-toolbar-title class="headline text-uppercase">
        <span>Track Pack &nbsp;</span>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-row>
        <v-col cols="8"></v-col>
        <v-row v-if="isAuthorized === true">
          <UserProfile :user="this.user" @existsInParcelList="this.setExistsInParcelList"
                       @notAuthorized="this.setNotAuthorized">
          </UserProfile>
        </v-row>
        <v-col v-if="isAuthorized === false" cols="2" align="end">
          <v-btn color="blue" @click="signInDialog = true">Sign In</v-btn>
        </v-col>
        <v-col v-if="isAuthorized === false" align="end">
          <v-btn color="blue" @click="signUpDialog = true">Sign Up</v-btn>
        </v-col>
      </v-row>
    </v-toolbar>
    <v-container></v-container>
    <v-container></v-container>
    <v-container></v-container>
    <v-main v-if="this.existsInParcelList === true && isAuthorized === true">
      <SearchParcel :existsInParcelList=this.existsInParcelList :isAuthorized="this.isAuthorized"
                    :user="this.user"></SearchParcel>
    </v-main>
    <v-main v-else-if="existsInCache() === true && isAuthorized === false">
      <SearchParcel :existsInCache="true" :isAuthorized="this.isAuthorized" :user="this.user"></SearchParcel>
    </v-main>
    <v-main v-else>
      <SearchParcel :isAuthorized="this.isAuthorized" :user="this.user"></SearchParcel>
    </v-main>
    <v-dialog v-model="signUpDialog" persistent max-width="600px" min-width="360px">
      <RegistrationForm :signUpDialog="signUpDialog"
                        v-on:closeSignUp="signUpDialog = false"
                        v-on:openSignIn="signInDialog = true">

      </RegistrationForm>
    </v-dialog>
    <v-dialog v-model="signInDialog" persistent max-width="600px" min-width="360px">
      <LoginForm :signInDialog="signInDialog"
                 v-on:closeSignIn="signInDialog = false"
                 v-on:openSignUp="signUpDialog = true"
                 @isAuthorized="setAuthorized"
                 @close="signInDialog = false">
      </LoginForm>
    </v-dialog>
  </v-app>
</template>
<script>

import SearchParcel from "@/components/SearchParcel";
import LoginForm from "@/components/LoginForm";
import RegistrationForm from "@/components/RegistrationForm";
import UserProfile from "@/components/UserProfile";


export default {
  name: 'App',
  components: {
    UserProfile,
    SearchParcel, LoginForm, RegistrationForm
  },
  data: () => ({
    signInDialog: false,
    signUpDialog: false,
    isAuthorized: false,
    user: {
      id: "", email: "", token: {}
    },
    existsInParcelList: false
  }),
  mounted() {
    let regexp = /.+@.+\..+/;
    for (let i = 0; i < localStorage.length; i++) {
      if (localStorage.getItem(localStorage.key(i)) && regexp.test(localStorage.key(i))) {
        this.user = JSON.parse(localStorage.getItem(localStorage.key(i)));
        this.isAuthorized = true;
      }
    }
  },
  methods: {
    existsInCache() {
      return localStorage.length > 1
    },
    setAuthorized(user) {
      this.user = user;
      this.isAuthorized = true;
    },
    setNotAuthorized() {
      this.isAuthorized = false;
    },
    setExistsInParcelList(val) {
      this.existsInParcelList = val;
    }
  }
};
</script>
