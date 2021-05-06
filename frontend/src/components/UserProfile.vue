<template>
  <v-row>
    <v-col cols="2">
      <v-avatar>
        <v-icon color="white" size="35px">
          mdi-account-circle
        </v-icon>
      </v-avatar>
    </v-col>
    <v-col cols="6" align-self="center" style="font-size:1.2rem">{{ user.email }}</v-col>
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
                two-line
                v-for="parcel in parcelList"
                :key="parcel.trackingCode"
                @click="openParcelInfo(parcel)">
              <v-list-item-content>
                <v-list-item-title v-text="parcel.trackingCode">{{ getParcelName(parcel) }}</v-list-item-title>
                <v-list-item-subtitle v-if="parcel.hasName" v-text="parcel.name"></v-list-item-subtitle>
              </v-list-item-content>
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
      parcelList: this.getParcelList(),
      names: new Map()
    }
  },
  props: {
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
          if (this.names != undefined && this.names.has(parcel.trackingCode)) {
            this.names.delete(parcel.trackingCode);
          }
        })
    eventBus.$on("editName", (trackingCode, parcelName) => {
      this.names.set(trackingCode, parcelName);
    }),
        eventBus.$on("checkIfInParcelList", (parcel) => {
          if (this.parcelList.indexOf(parcel) != -1) {
            eventBus.$emit("existsInList", true)
          } else {
            eventBus.$emit("existsInList", false)
          }
        })
  },
  methods: {
    openParcelInfo(parcel) {
      if (this.names != null && this.names.has(parcel.trackingCode)) {
        eventBus.$emit("showParcelInfo", parcel, parcel.name);
      } else {
        eventBus.$emit("showParcelInfo", parcel, parcel.trackingCode);
      }
    },
    getParcelList() {
      this.$http.get("http://localhost:8080/user/parcels/" + this.user.id, {headers: {Authorization: "Bearer " + this.user.token}})
          .then(response => {
            this.parcelList = response.data.slice();
            let len = this.parcelList.length;
            eventBus.$emit("getLastFromParcelList", this.parcelList[len - 1])
            eventBus.$emit("getParcelList", this.parcelList)
          })
    },
    existsInParcelList() {
        this.$emit("existsInParcelList", this.parcelList.length > 0);
        return this.parcelList.length > 0;
    },
    logout(email) {
      localStorage.removeItem(email);
      this.$emit("notAuthorized");
      eventBus.$emit("hideParcelInfo");
    },
    hasName(parcel) {
      if (this.names.has(parcel.trackingCode)) {
        parcel.name = this.names.get(parcel.trackingCode);
        return true;
      }
      return false;
    },
    getParcelName(parcel) {
      this.$http.get("http://localhost:8080/user/getName/" + this.user.id + "/" + parcel.id, {headers: {Authorization: "Bearer " + this.user.token}})
          .then(response => {
            let resp = response;
            if (resp.bodyText.length != 0) {
              parcel.name = resp.bodyText;
              parcel.hasName = true;
              this.names.set(parcel.trackingCode, parcel.name);
              eventBus.$emit("setParcelName", parcel.name);
            } else {
              parcel.name = null;
              parcel.hasName = false;
              eventBus.$emit("setParcelName", parcel.trackingCode);
            }
          }, () => {
        parcel.name = null;
        parcel.hasName = false;
        eventBus.$emit("setParcelName", parcel.trackingCode);
      })
    }
  }
}
</script>

<style scoped>

</style>