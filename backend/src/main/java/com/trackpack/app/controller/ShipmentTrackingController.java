package com.trackpack.app.controller;

import com.trackpack.app.exceptions.ResourceNotFoundException;
import com.trackpack.app.model.tracking.*;
import com.trackpack.app.service.ShipmentTrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;


@RestController("/track-pack")
public class ShipmentTrackingController {

    private final ShipmentTrackingService service;

    public ShipmentTrackingController(ShipmentTrackingService service) {
        this.service = service;
    }

    @GetMapping("/tracking")
    public ResponseEntity<List<ShipmentTracking>> getAll() {
        List<ShipmentTracking> parcels = service.findAll();
        if(parcels.isEmpty()){
            throw new ResourceNotFoundException("Parcel list is empty");
        }
        return ResponseEntity.ok().body(parcels);
    }

    @GetMapping("/tracking/{id}")
    public ResponseEntity<ShipmentTracking> getById(@PathVariable(value = "id") UUID id) {
        ShipmentTracking shipmentTracking = service.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parcel with id " + id + " is not found"));
        return ResponseEntity.ok().body(shipmentTracking);
    }

    @DeleteMapping("/tracking/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable(value = "id") UUID id) {
        ShipmentTracking shipmentTracking = service.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parcel with id " + id + " is not found"));
        service.delete(shipmentTracking);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/tracking")
    public ResponseEntity<ShipmentTracking> addParcel(@Valid @RequestBody ShipmentTracking shipmentTracking){
        service.add(shipmentTracking);
        return ResponseEntity.ok().body(shipmentTracking);
    }


    @PatchMapping("/tracking/{id}")
    public ResponseEntity<ShipmentTracking> updateById(@PathVariable(value = "id") UUID id, @RequestBody Map<String, Object> changes) {
        ShipmentTracking shipmentTracking = service.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parcel with id " + id + " is not found"));
        service.update(id, changes);
        return ResponseEntity.ok().body(shipmentTracking);
    }

    @PatchMapping("/tracking/checkpoint{id}")
    public ResponseEntity<ShipmentTracking> addCheckPoint(@PathVariable(value = "id") UUID id, @Valid @RequestBody CheckPoint checkPoint) {
        ShipmentTracking shipmentTracking = service.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parcel with id " + id + " is not found"));
        service.addCheckpoint(id, checkPoint);
        return ResponseEntity.ok().body(shipmentTracking);
    }

    @PatchMapping("/tracking/deliveryDate{id}")
    public ResponseEntity<ShipmentTracking> updateEstimatedDeliveryDate(@PathVariable(value = "id") UUID id,
                                                            @RequestBody OffsetDateTime estimatedDeliveryDate) {
        ShipmentTracking shipmentTracking = service.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parcel with id " + id + " is not found"));
        service.updateEstimatedDeliveryDate(id, estimatedDeliveryDate);
        return ResponseEntity.ok().body(shipmentTracking);
    }

    @PatchMapping("/tracking/status{id}")
    public ResponseEntity<ShipmentTracking> updateStatusInfo(@PathVariable(value = "id") UUID id,
                                                 @RequestBody Map<String, Object> statusInfo) {
        ShipmentTracking shipmentTracking = service.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parcel with id " + id + " is not found"));
        String status = (String) statusInfo.get("status");
        OffsetDateTime statusChangeDate = (OffsetDateTime) statusInfo.get("statusChangeDate");
        String statusChangeReason = (String) statusInfo.get("statusChangeReason");
        service.updateStatusInfo(id, status, statusChangeDate, statusChangeReason);
        return ResponseEntity.ok().body(shipmentTracking);
    }

    @PatchMapping("/tracking/addressFrom{id}")
    public ResponseEntity<ShipmentTracking> updateAddressFrom(@PathVariable(value = "id") UUID id, @Valid @RequestBody Address addressFrom) {
        ShipmentTracking shipmentTracking = service.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parcel with id " + id + " is not found"));
        service.updateAddressFrom(id, addressFrom);
        return ResponseEntity.ok().body(shipmentTracking);
    }

    @PatchMapping("/tracking/addressTo{id}")
    public ResponseEntity<ShipmentTracking> updateAddressTo(@PathVariable(value = "id") UUID id, @Valid @RequestBody Address addressTo) {
        ShipmentTracking shipmentTracking = service.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parcel with id " + id + " is not found"));
        service.updateAddressTo(id, addressTo);
        return ResponseEntity.ok().body(shipmentTracking);
    }

}