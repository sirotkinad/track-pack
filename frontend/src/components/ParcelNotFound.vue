<template>
  <v-container>
    <v-card max-width="500">
      <v-row>
        <v-col cols="3">
          <v-icon size="100" color=blue>
            mdi-package-variant-closed
          </v-icon>
        </v-col>
        <v-col>
          <v-card-title class="headline blue-grey--text text--accent-2">
            Parcel is not found
          </v-card-title>
          <v-card-subtitle>
            Check the correctness of a tracking code and try again
          </v-card-subtitle>
        </v-col>
      </v-row>
    </v-card>
  </v-container>
</template>
<script>
export default {
  name: "ParcelNotFound"
}
</script>

<style scoped>

</style>