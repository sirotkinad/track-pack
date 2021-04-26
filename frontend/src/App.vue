<template>
  <v-app>
    <v-toolbar app dark class="indigo">
      <v-toolbar-title class="headline text-uppercase">
        <span>Track Pack &nbsp;</span>
      </v-toolbar-title>
    </v-toolbar>
    <v-container></v-container>
    <v-container></v-container>
    <v-container></v-container>
    <v-main v-if="existsInCache() === true">
      <SearchParcel :parcelIsExists="true"></SearchParcel>
    </v-main>
    <v-main v-else>
      <SearchParcel></SearchParcel>
    </v-main>
  </v-app>
</template>
<script>

import SearchParcel from "@/components/SearchParcel";

export default {
  name: 'App',
  components: {
    SearchParcel
  },
  data: () => ({}),
  methods: {
    existsInCache() {
      if (localStorage.length > 1) {
        return true;
      } else {
        return false;
      }
    }
  }
};
</script>
