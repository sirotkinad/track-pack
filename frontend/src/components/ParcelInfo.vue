<template>
  <v-container>
    <v-card>
      <v-row>
        <v-col align="center" cols="1">
          <v-icon color="blue" @click="(event) => refresh(event)"> mdi-refresh</v-icon>
        </v-col>
        <v-col cols="8" class="black--text" style="font-size:1rem">{{ parcel.trackingCode }},
          {{ parcel.checkPoints[parcel.checkPoints.length - 1].status }}
        </v-col>
        <v-col cols="2">
          <v-card v-if="hover" style="font-size:0.7rem" @mouseleave="hover = false">
            Information was loaded {{ getDateInString(parcel.lastUpdateDate) }}
            {{ getTimeInString(parcel.lastUpdateDate) }}
          </v-card>
          <v-chip v-if="getHoursFromLastUpdate(parcel.lastUpdateDate) > 12 && hover===false"
                  class="ma-2" color="blue" text-color="white" small @mouseover="hover = true">
            Outdated: {{getOutdated(parcel.lastUpdateDate)}}
          </v-chip>
        </v-col>
        <v-col>
          <v-icon color="blue" @click="deleteParcelLocally()"> mdi-delete</v-icon>
        </v-col>
      </v-row>
      <v-container>
        <v-card-text>
          <v-card outlined>
            <v-card-text style="font-size:1rem">
              <p><b v-if="parcel.carrier" class="font-weight-bold"> Carrier: </b> {{ parcel.carrier }}</p>
              <p><b v-if="parcel.carrierTrackingUrl" class="font-weight-bold">Carrier's link for tracking: </b>
                {{ parcel.carrierTrackingUrl }} </p>
              <p><b class="font-weight-bold"> Current duration: </b>{{ getCurrentDuration(parcel.trackingDate) }} days
              </p>
              <p><b v-if="parcel.weight" class="font-weight-bold"> Weight: </b> {{ parcel.weight }}</p>
              <p><b v-if="parcel.estimatedDeliveryDate" class="font-weight-bold"> Estimated delivery date: </b>
                {{ getDateInString(parcel.estimatedDeliveryDate) }} </p>
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
                <v-col cols="3">
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
export default {
  name: "ParcelInfo",
  data() {
    return {
      snackbar: false,
      snackbarMessage: "",
      timeout: 2000,
      hover: false
    }
  },
  props: {
    parcel: Object
  },
  methods: {
    refresh(event) {
      this.$emit('refreshRequest', event);
      this.snackbar = true;
      this.snackbarMessage = "Information is updated";
    },
    deleteParcelLocally() {
      localStorage.removeItem(this.parcel.trackingCode);
      this.snackbar = true;
      this.snackbarMessage = "Parcel is deleted";
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
    getOutdated(lastUpdateDate){
      let result = ""
      if(this.getHoursFromLastUpdate(lastUpdateDate) < 48) {
        result = this.getHoursFromLastUpdate(lastUpdateDate) + " hours ago";
        return result;
      }
      else{
        result =  Math.floor(this.getHoursFromLastUpdate() / 24) + " days ago";
        return result;
      }
    }
  }
}
</script>

<style scoped>

</style>