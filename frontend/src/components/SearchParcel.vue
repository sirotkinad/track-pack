<template>
  <v-app style="background-color: aliceblue">
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
        <v-col class=my-2>
          <v-btn dark color="blue"
                 :loading="dialog"
                 @click="getParcel(trackingCode)"
                 height="31px">
            Search
          </v-btn>
        </v-col>
      </v-row>
      <v-row>
        <v-col v-if="hideParcelInfo === true">
        </v-col>
        <v-col v-else-if="existsInParcelList === true && isAuthorized === true && fromParcelList === true">
          <ParcelInfo :parcel="this.parcel" :isAuthorized="this.isAuthorized" :user="this.user"
                      :parcelName="this.parcelName"
                      v-on:refreshRequest="refreshParcel()"></ParcelInfo>
        </v-col>
        <v-col v-else-if="existsInParcelList === true && isAuthorized === true">
          <ParcelInfo :parcel="this.lastParcel" :isAuthorized="this.isAuthorized" :user="this.user"
                      :parcelName="this.parcelName"
                      v-on:refreshRequest="refreshParcel()"></ParcelInfo>
        </v-col>
        <v-col v-else-if="existsInCache === true && isAuthorized === false">
          <ParcelInfo :parcel="this.getLastFromCache()" :isAuthorized="this.isAuthorized" :user="this.user"
                      v-on:refreshRequest="refreshParcel()"></ParcelInfo>
        </v-col>
        <v-col v-else-if="notFound === false">
          <ParcelInfo :parcel="parcel" :isAuthorized="this.isAuthorized" :user="this.user" :parcelName="this.parcelName"
                      v-on:refreshRequest="refreshParcel()"></ParcelInfo>
        </v-col>
        <v-row v-if="notFound === true">
          <ParcelNotFound></ParcelNotFound>
        </v-row>
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
import {eventBus} from "@/main";

export default {
  name: "SearchParcel",
  components: {ParcelInfo, ParcelNotFound},
  data() {
    return {
      parcel: {
        id: "", href: "", carrier: "", trackingCode: "", carrierTrackingUrl: "",
        trackingDate: "", status: "", statusChangeDate: "", statusChangeReason: "",
        weight: "", estimatedDeliveryDate: "", addressFrom: null, addressTo: null,
        checkPoints: [], lastUpdateDate: "", name: ""
      },
      dialog: false,
      notFound: null,
      trackingCode: "",
      parcelName: "",
      lastParcel: null,
      fromParcelList: false,
      hideParcelInfo: false
    }
  },
  props: {
    existsInCache: Boolean,
    existsInParcelList: Boolean,
    isAuthorized: Boolean,
    user: Object
  },
  watch: {
    parcel: {
      handler() {
        if (this.isAuthorized === false) {
          localStorage.setItem(this.parcel.trackingCode, JSON.stringify(this.parcel));
        }
      }
    }
  },
  created() {
    eventBus.$on("showParcelInfo", (parcel, name) => {
      this.hideParcelInfo = false;
      this.parcel = parcel;
      this.parcelName = name;
      this.fromParcelList = true;
    }),
        eventBus.$on("getLastFromParcelList", (parcel) => {
          this.lastParcel = parcel;
        }),
        eventBus.$on("hideParcelInfo", () => {
          this.hideParcelInfo = true;
          this.notFound = false;
        }),
        eventBus.$on("hideNotFound", () => {
          this.notFound = false;
        })
    eventBus.$on("setParcelName", (parcelName) => {
      this.parcelName = parcelName;
    })
  },
  mounted() {
    if (this.existsInParcelList === true && this.isAuthorized === true) {
      this.parcel = this.lastParcel;
    } else if (this.existsInCache === true && this.isAuthorized === false) {
      let lastParcel = this.getLastFromCache();
      if (localStorage.getItem(lastParcel.trackingCode)) {
        this.parcel = JSON.parse(localStorage.getItem(lastParcel.trackingCode));
      }
    }
  },
  methods: {
    getParcel(trackingCode) {
      this.dialog = true;
      setTimeout(() => (this.dialog = false), 500);
      this.$http.get("http://localhost:8080/track-pack/tracking/trackingCode/" + trackingCode).then(response => {
            this.hideParcelInfo = false;
            this.parcel = response.data;
            this.parcel.lastUpdateDate = Date.now();
            this.notFound = false;
            this.existsInCache = false;
            this.existsInParcelList = false;
            this.parcelName = this.parcel.trackingCode;
          }, () => {
            this.notFound = true;
            this.hideParcelInfo = true;
          }
      )
    },
    refreshParcel() {
      this.$http.get("http://localhost:8080/track-pack/tracking/trackingCode/" + this.parcel.trackingCode).then(response => {
            this.parcel = response.data;
            this.parcel.lastUpdateDate = Date.now();
            eventBus.$emit("setMessageAfterUpdate", "Information is updated")
          }, () => {
            eventBus.$emit("setMessageAfterUpdate", "Update has failed");
          }
      )
    },
    getLastFromCache() {
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