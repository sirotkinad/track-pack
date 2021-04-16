package com.trackpack.app.controller;

import com.trackpack.app.model.tracking.ShipmentTracking;
import com.trackpack.app.service.UserParcelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserParcelController {

    private final UserParcelService service;

    public UserParcelController(UserParcelService service) {
        this.service = service;
    }

    @GetMapping("parcels/{userId}")
    public ResponseEntity<List<ShipmentTracking>> getParcelsByUserId(@PathVariable(value = "userId") UUID id) {
        return ResponseEntity.ok().body(service.getParcelsByUserId(id));
    }

    @PostMapping("/addParcel/{userId}/{parcelId}")
    public ResponseEntity<Void> addParcelForUser(@PathVariable(value = "userId") UUID userId, @PathVariable(value = "parcelId")UUID parcelId) {
       service.addParcelForUser(userId, parcelId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteParcel/{userId}/{parcelId}")
    public ResponseEntity<Void> deleteParcelFromUser(@PathVariable(value = "userId") UUID userId, @PathVariable(value = "parcelId")UUID parcelId) {
        service.deleteParcelFromUser(userId, parcelId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addName/{userId}/{parcelId}/{name}")
    public ResponseEntity<Void> setParcelName(@PathVariable(value = "userId") UUID userId, @PathVariable(value = "parcelId") UUID parcelId, @PathVariable(value = "name")String name) {
        service.setParcelName(userId, parcelId, name);
        return ResponseEntity.ok().build();
    }

}
