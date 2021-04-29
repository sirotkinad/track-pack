<template>
  <v-container>
    <v-card>
      <v-row>
        <v-col cols="1" align="center" align-self="center">
          <v-icon color="blue" @click="(event) => refresh(event)"> mdi-refresh</v-icon>
        </v-col>
        <v-col v-if="isAuthorized === true" @mouseover="editNameHover = true" @mouseleave="editNameHover = false"
               cols="5" align-self="center" align="start">
          <span v-if="edit === false">{{ getParcelName() }} {{ this.name }}</span>
          <input v-if="edit === true" v-model.trim="name" style="width:130px"
                 @blur="editName()"
                 @keyup.enter="editName"
                 @keyup.escape="edit = false"
                 placeholder="Parcel name">
          , {{ parcel.checkPoints[parcel.checkPoints.length - 1].status }}
          <v-icon v-if="editNameHover" color="blue" @click="edit = true">mdi-pencil</v-icon>
        </v-col>
        <v-col v-else cols="5" class="black--text" style="font-size:1rem" align-self="center">{{ parcel.trackingCode }},
          {{ parcel.checkPoints[parcel.checkPoints.length - 1].status }}
        </v-col>
        <v-col cols="2">
          <v-card v-if="dateLoadedHover" style="font-size:0.7rem" @mouseleave="dateLoadedHover = false">
            Information was loaded {{ getDateInString(parcel.lastUpdateDate) }}
            {{ getTimeInString(parcel.lastUpdateDate) }}
          </v-card>
          <v-chip v-if="outdated === true && dateLoadedHover === false"
                  class="ma-2" color="blue" text-color="white" small @mouseover="dateLoadedHover = true">
            Outdated: {{ getOutdated(parcel.lastUpdateDate) }}
          </v-chip>
        </v-col>
        <v-col cols="2">
          <v-chip class="ma-2" color="blue" text-color="white" small>
            Est. delivery: {{ getDateInString(parcel.estimatedDeliveryDate) }}
          </v-chip>
        </v-col>
        <v-col align-self="center" align="end">
          <v-icon color="blue" @click="addParcelToList()"> mdi-plus</v-icon>
        </v-col>
        <v-col align-self="center" align="center">
          <v-icon color="blue" @click="deleteParcel()"> mdi-delete</v-icon>
        </v-col>
      </v-row>
      <v-container>
        <v-card-text>
          <v-card outlined>
            <v-card-text style="font-size:1rem">
              <p><b v-if="parcel.trackingCode" class="font-weight-bold"> Tracking code: </b> {{ parcel.trackingCode }}
              </p>
              <p><b v-if="parcel.carrier" class="font-weight-bold"> Carrier: </b> {{ parcel.carrier }}</p>
              <p><b v-if="parcel.carrierTrackingUrl" class="font-weight-bold">Carrier's link for tracking: </b>
                {{ parcel.carrierTrackingUrl }} </p>
              <p><b class="font-weight-bold"> Current duration: </b>{{ getCurrentDuration(parcel.trackingDate) }} days
              </p>
              <p><b v-if="parcel.weight" class="font-weight-bold"> Weight: </b> {{ parcel.weight }}</p>
              <p><b v-if="parcel.estimatedDeliveryDate" class="font-weight-bold"> Estimated delivery date: </b>
                {{ getDateInString(parcel.estimatedDeliveryDate) }}
                {{ getParcelAmount() }}
                <span v-if="this.parcelAmount != 0"> (based on {{ parcelAmount }} parcels)</span></p>
              <p><b class="font-weight-bold"> Departure address: </b> {{ parcel.addressFrom.country }},
                {{ parcel.addressFrom.city }}, {{ parcel.addressFrom.streetName }}</p>
              <p><b class="font-weight-bold"> Arrival address: </b> {{ parcel.addressTo.country }},
                {{ parcel.addressTo.city }}, {{ parcel.addressTo.streetName }}</p>
            </v-card-text>
          </v-card>
          <v-timeline
              align-top
              dense>
            <v-timeline-item v-for="checkPoint in parcel.checkPoints" :key="checkPoint.id"
                             color="blue"
                             small>
              <v-row class="pt-1">
                <v-col cols="2">
                  <strong>{{ getDateInString(checkPoint.date) }} </strong>
                  <div class="grey--text">
                    {{ getTimeInString(checkPoint.date) }}
                  </div>
                </v-col>
                <v-col>
                  <strong style="font-size:1rem">{{ checkPoint.status }}</strong>
                  <div class="grey--text">
                    {{ checkPoint.message }}
                  </div>
                  <div class="caption grey--text">
                    {{ checkPoint.checkPost }}, {{ checkPoint.city }}, {{ checkPoint.stateOrProvince }},
                    {{ checkPoint.country }}
                  </div>
                </v-col>
              </v-row>
            </v-timeline-item>
          </v-timeline>
        </v-card-text>
      </v-container>
    </v-card>
    <v-snackbar v-model="snackbar" :timeout="timeout" color="blue">
      {{ snackbarMessage }}
    </v-snackbar>
  </v-container>
</template>

<script>

import {eventBus} from "@/main";

export default {
  name: "ParcelInfo",
  data() {
    return {
      snackbar: false,
      snackbarMessage: "",
      timeout: 3000,
      dateLoadedHover: false,
      editNameHover: false,
      outdated: this.setOutdated(this.parcel.lastUpdateDate),
      edit: false,
      name: "",
      existsInList: false,
      parcelAmount: 0
    }
  },
  props: {
    parcel: Object,
    user: Object,
    isAuthorized: Boolean,
    parcelName: String
  },
  created() {
    eventBus.$on("existsInList", (value) => {
      this.existsInList = value;
    }),
        eventBus.$on("setMessageAfterUpdate", (message) => {
          this.snackbar = true;
          this.snackbarMessage = message;
        })
  },
  methods: {
    refresh(event) {
      this.$emit('refreshRequest', event);
      this.outdated = false;
    },
    deleteParcel() {
      if (this.isAuthorized === true) {
        this.$http.delete("http://localhost:8080/user/deleteParcel/" + this.user.id + "/" + this.parcel.id, {headers: {Authorization: "Bearer " + this.user.token}})
            .then(() => {
            }, () => {
              this.snackbar = true;
              this.snackbarMessage = "Parcel is not in tracking list";
            })
        let parcel = this.parcel;
        eventBus.$emit("deleteFromParcelList", parcel);
        this.snackbar = true;
        this.snackbarMessage = "Parcel is deleted";
      } else {
        localStorage.removeItem(this.parcel.trackingCode);
        this.snackbar = true;
        this.snackbarMessage = "Parcel is deleted";
      }
      eventBus.$emit("hideParcelInfo");
    },
    getDateInString(date) {
      return new Date(date).toLocaleDateString();
    },
    getTimeInString(date) {
      return new Date(date).toLocaleTimeString();
    },
    getCurrentDuration(trackingDate) {
      return Math.floor((Math.abs(Date.now() - new Date(trackingDate)) / (60 * 60 * 24 * 1000)));
    },
    getHoursFromLastUpdate(lastUpdateDate) {
      return Math.floor((Math.abs(Date.now() - lastUpdateDate)) / (60 * 60 * 1000));
    },
    getOutdated(lastUpdateDate) {
      let result = ""
      if (this.getHoursFromLastUpdate(lastUpdateDate) < 48) {
        result = this.getHoursFromLastUpdate(lastUpdateDate) + " hours ago";
        return result;
      } else {
        result = Math.floor(this.getHoursFromLastUpdate() / 24) + " days ago";
        return result;
      }
    },
    setOutdated(lastUpdateDate) {
      if (this.getHoursFromLastUpdate(lastUpdateDate) > 12) {
        return true;
      } else {
        return false;
      }
    },
    addParcelToList() {
      if (this.isAuthorized === true) {
        this.$http.post("http://localhost:8080/user/addParcel/" + this.user.id + "/" + this.parcel.id, this.parcel, {headers: {Authorization: "Bearer " + this.user.token}})
            .then(() => {
              this.name = this.parcel.trackingCode;
              this.parcelName = this.parcel.trackingCode;
            }, () => {
              this.snackbar = true;
              this.snackbarMessage = "Parcel is already in a tracking list";
            })
        eventBus.$emit("addParcelToList", this.parcel);
        eventBus.$emit("getLastFromParcelList", this.parcel);
        this.snackbar = true;
        this.snackbarMessage = "Parcel is added to a tracking list";
      } else {
        this.snackbar = true;
        this.snackbarMessage = "Please, sign in to add parcel to a tracking list";
      }
    },
    editName() {
      this.edit = false;
      eventBus.$emit("checkIfInParcelList", this.parcel);
      if (this.existsInList === true) {
        if (this.name != 0 && this.name != this.parcelName) {
          this.$http.post("http://localhost:8080/user/addName/" + this.user.id + "/" + this.parcel.id + "/" + this.name, "", {headers: {Authorization: "Bearer " + this.user.token}});
          eventBus.$emit("editName", this.parcel.trackingCode, this.name)
          eventBus.$emit("setParcelName", this.name);
        }
      } else {
        eventBus.$emit("setParcelName", this.name);
      }
    },
    getParcelName() {
      this.name = this.parcelName;
    },
    getParcelAmount() {
      this.$http.get("http://localhost:8080/track-pack/tracking/statistics/" + this.parcel.id).then(response => {
            this.parcelAmount = response.data;
          }, () => {
            this.parcelAmount = 0;
          }
      )
    }
  }
}
</script>

<style scoped>

</style>