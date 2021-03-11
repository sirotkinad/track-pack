package com.trackpack.app.service;

import com.trackpack.app.exceptions.ResourceNotFoundException;
import com.trackpack.app.model.tracking.Address;
import com.trackpack.app.model.tracking.CheckPoint;
import com.trackpack.app.model.tracking.ShipmentTracking;
import com.trackpack.app.repository.ShipmentTrackingRepository;
import org.springframework.stereotype.Service;
import java.time.*;
import java.util.*;

@Service
public class ShipmentTrackingService {

    private final ShipmentTrackingRepository repository;

    public ShipmentTrackingService(ShipmentTrackingRepository repository) {
        this.repository = repository;
    }

    public List<ShipmentTracking> findAll() {
        return repository.findAll();
    }

    public ShipmentTracking findById(UUID id) throws ResourceNotFoundException {
        return repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Parcel with id " + id + " is not found"));
    }

    public void add(ShipmentTracking shipmentTracking) {
        repository.save(shipmentTracking);
    }

    public void delete(ShipmentTracking shipmentTracking) {
        repository.delete(shipmentTracking);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }


    public void addCheckpoint(UUID id, CheckPoint checkPoint) throws ResourceNotFoundException {
        ShipmentTracking shipmentTracking = findById(id);
        List<CheckPoint> checkPoints = shipmentTracking.getCheckpoints();
        if (checkPoints == null) {
            checkPoints = new ArrayList<>();
        }
        checkPoints.add(checkPoint);
    }

    public void updateStatusInfo(UUID id, String status, OffsetDateTime statusChangeDate, String statusChangeReason)
            throws ResourceNotFoundException {
        ShipmentTracking shipmentTracking = findById(id);
        if (status != null) {
            shipmentTracking.setStatus(status);
        }
        shipmentTracking.setStatusChangeDate(statusChangeDate);
        shipmentTracking.setStatusChangeReason(statusChangeReason);
    }

    public void updateEstimatedDeliveryDate(UUID id, OffsetDateTime estimatedDeliveryDate)
            throws ResourceNotFoundException {
        ZoneOffset zoneOffset = estimatedDeliveryDate.getOffset();
        ZoneId zoneId = ZoneId.ofOffset("UTC", zoneOffset);
        if (estimatedDeliveryDate.isAfter(OffsetDateTime.now(zoneId))) {
            ShipmentTracking shipmentTracking = findById(id);
            shipmentTracking.setEstimatedDeliveryDate(estimatedDeliveryDate);
        } else {
            throw new IllegalArgumentException("Delivery date must be greater than current date");
        }
    }

    public void updateAddressFrom(UUID id, Address addressFrom) throws ResourceNotFoundException {
        if(addressFrom == null){
            throw new IllegalArgumentException("Address should not be a null value");
        }
            ShipmentTracking shipmentTracking = findById(id);
            shipmentTracking.setAddressFrom(addressFrom);
    }

    public void updateAddressTo(UUID id, Address addressTo) throws ResourceNotFoundException {
        if(addressTo == null){
            throw new IllegalArgumentException("Address should not be a null value");
        }
            ShipmentTracking shipmentTracking = findById(id);
            shipmentTracking.setAddressFrom(addressTo);
    }

    public void update(UUID id, Map<String, Object> changes) throws ResourceNotFoundException {
        ShipmentTracking shipmentTracking = findById(id);
        changes.forEach((field, value) -> {
            if(field.equals("href")){
                shipmentTracking.setHref((String) value);
            }
            if(field.equals("carrier")){
                shipmentTracking.setCarrier((String) value);
            }
            if(field.equals("trackingCode") && value != null){
                shipmentTracking.setTrackingCode((String) value);
            }
            if(field.equals("carrierTrackingUrl")){
                shipmentTracking.setCarrierTrackingUrl((String) value);
            }
            if(field.equals("trackingDate") && value != null){
                shipmentTracking.setTrackingDate((OffsetDateTime) value);
            }
            if(field.equals("status") && value != null){
                shipmentTracking.setStatus((String) value);
            }
            if(field.equals("statusChangeDate")){
                shipmentTracking.setStatusChangeDate((OffsetDateTime) value);
            }
            if(field.equals("statusChangeReason")){
                shipmentTracking.setStatusChangeReason((String) value);
            }
            if(field.equals("weight")){
                shipmentTracking.setWeight((Integer) value);
            }
            if(field.equals("estimatedDeliveryDate")){
                shipmentTracking.setEstimatedDeliveryDate((OffsetDateTime) value);
            }
            if(field.equals("addressFrom") && value != null){
                shipmentTracking.setAddressFrom((Address) value);
            }
            if(field.equals("addressTo") && value != null){
                shipmentTracking.setAddressTo((Address) value);
            }
            if(field.equals("checkPoints") && value != null){
                shipmentTracking.setCheckpoints((List<CheckPoint>) value);
            }
        });
    }

}


