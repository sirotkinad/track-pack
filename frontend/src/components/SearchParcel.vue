<template>
  <v-app>
    <v-container>
      <v-row justify="center">
        <h4 class="headline blue-grey--text text--accent-2">
          Search and track your parcels easily!
        </h4>
      </v-row>
    </v-container>
    <v-container>
      <v-row>
        <v-col></v-col>
        <v-col>
          <v-text-field
              placeholder="Search"
              clearable
              hint="For example, RS762954205PK"
              solo
              input type="text" v-model="trackingCode"
              required>
          </v-text-field>
        </v-col>
        <v-col>
          <v-btn dark color="blue"
                 :loading="dialog"
                 @click="getParcel(trackingCode)">
            Search
          </v-btn>
        </v-col>
      </v-row>
      <v-row>
        <v-col v-if="parcelIsExists === true">
          <ParcelInfo :parcel="this.getLastParcel()" v-on:refreshRequest="refreshParcel()"></ParcelInfo>
        </v-col>
        <v-col v-else-if="notFound === false">
          <ParcelInfo :parcel="parcel" v-on:refreshRequest="refreshParcel()"></ParcelInfo>
        </v-col>
        <v-col v-else-if="notFound === true">
          <ParcelNotFound></ParcelNotFound>
        </v-col>
      </v-row>
    </v-container>
    <v-dialog
        transition="fade-transition"
        v-model="dialog"
        hide-overlay
        persistent
        width="300">
      <v-card
          color="primary"
          dark>
        <v-card-text>
          Looking for a parcel...
          <v-progress-linear
              indeterminate
              color="white"
              class="mb-0"
          ></v-progress-linear>
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-app>
</template>

<script>

import ParcelNotFound from "@/components/ParcelNotFound";
import ParcelInfo from "@/components/ParcelInfo";

export default {
  name: "SearchParcel",
  components: {ParcelInfo, ParcelNotFound},
  data() {
    return {
      parcel: {
        id: "", href: "", carrier: "", trackingCode: "", carrierTrackingUrl: "",
        trackingDate: "", status: "", statusChangeDate: "", statusChangeReason: "",
        weight: "", estimatedDeliveryDate: "", addressFrom: null, addressTo: null,
        checkPoints: [], lastUpdateDate: ""
      },
      dialog: false,
      notFound: null,
      trackingCode: ""
    }
  },
  props: {
    parcelIsExists: Boolean
  },
  watch: {
    parcel: {
      handler() {
        localStorage.setItem(this.parcel.trackingCode, JSON.stringify(this.parcel));
      }
    }
  },
  mounted() {
      if(this.parcelIsExists === true) {
        let lastParcel = this.getLastParcel();
        if(localStorage.getItem(lastParcel.trackingCode)) {
          this.parcel = JSON.parse(localStorage.getItem(lastParcel.trackingCode));
        }
      }
},
  methods: {
    getParcel(trackingCode) {
      this.dialog = true;
      setTimeout(() => (this.dialog = false), 500);
      this.$http.get("http://localhost:8080/track-pack/tracking/trackingCode/" + trackingCode).then(response => {
            this.parcel = response.data;
            this.parcel.lastUpdateDate = Date.now();
            this.notFound = false;
            this.parcelIsExists = false;
          }, (response) => {
            console.log(response.data);
            this.notFound = true;
          }
      )
    },
    refreshParcel() {
      this.$http.get("http://localhost:8080/track-pack/tracking/trackingCode/" + this.parcel.trackingCode).then(response => {
            this.parcel = response.data;
            this.parcel.lastUpdateDate = Date.now();
          }
      )
    },
    getLastParcel(){
      return JSON.parse(localStorage.getItem(localStorage.key(0)))
    }
  }
}
</script>

<style scoped>
/deep/ .v-text-field {
  width: 900px;
}
</style>