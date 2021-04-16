<template>
  <v-row>
    <v-col cols="2">
      <v-avatar>
        <v-icon color="white" large>
          mdi-account-circle
        </v-icon>
      </v-avatar>
    </v-col>
    <v-col cols="6" style="font-size:1.2rem">{{ user.email }}</v-col>
    <v-col cols="2">
      <v-menu :close-on-content-click="false">
        <template v-slot:activator="{ on, attrs }">
          <v-btn dark
                 icon
                 v-bind="attrs"
                 v-on="on">
            <v-icon>mdi-dots-vertical</v-icon>
          </v-btn>
        </template>
        <v-list>
          <v-list-item @click="logout(user.email)">
            <v-list-item-title>Log out</v-list-item-title>
          </v-list-item>
          <v-list-group v-if="this.existsInParcelList()">
            <template v-slot:activator>
              <v-list-item-title>My parcels</v-list-item-title>
            </template>
            <v-list-item
                v-for="parcel in parcelList"
                :key="parcel.trackingCode"
                @click="openParcelInfo(parcel)">
              <v-list-item-title v-text="parcel.trackingCode"></v-list-item-title>
            </v-list-item>
          </v-list-group>
        </v-list>
      </v-menu>
    </v-col>
  </v-row>
</template>

<script>

import {eventBus} from "@/main";

export default {
  name: "UserProfile",
  data() {
    return {
      parcelList: this.getParcelList()
    }
  },
  props: {
    username: String,
    user: Object
  },
  created() {
    eventBus.$on("addParcelToList", (parcel) => {
      if (this.parcelList.indexOf(parcel) === -1) {
        this.parcelList.push(parcel);
      }
    }),
        eventBus.$on("deleteFromParcelList", (parcel) => {
          if (this.parcelList.indexOf(parcel) != -1) {
            this.parcelList.splice(this.parcelList.indexOf(parcel), 1);
          }
        })
  },
  methods: {
    openParcelInfo(parcel) {
      eventBus.$emit("showParcelInfo", parcel);
    },
    getParcelList() {
      this.$http.get("http://localhost:8080/user/parcels/" + this.user.id, {headers: {Authorization: "Bearer " + this.user.token}})
          .then(response => {
            this.parcelList = response.data.slice();
            let len = this.parcelList.length;
            eventBus.$emit("getLastFromParcelList", this.parcelList[len - 1])
          })
    },
    existsInParcelList() {
      this.$emit("existsInParcelList", this.parcelList.length > 0);
      return this.parcelList.length > 0;
    },
    logout(email) {
      localStorage.removeItem(email);
      this.$emit("notAuthorized");
    },
  }
}
</script>

<style scoped>

</style>