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

    public List<ShipmentTracking> findAll(){
        return repository.findAll();
    }

    public ShipmentTracking findById(UUID id) throws ResourceNotFoundException {
        return repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Parcel with id " + id + " is not found"));
    }

    public void add(ShipmentTracking shipmentTracking){
        repository.save(shipmentTracking);
    }

    public void delete(ShipmentTracking shipmentTracking){
        repository.delete(shipmentTracking);
    }

    public void deleteById(UUID id){
        repository.deleteById(id);
    }


    public void addCheckpoint(UUID id, CheckPoint checkPoint) throws ResourceNotFoundException {
        ShipmentTracking shipmentTracking = findById(id);
        List<CheckPoint> checkPoints = shipmentTracking.getCheckpoints();
        if(checkPoints == null){
            checkPoints = new ArrayList<>();
        }
        checkPoints.add(checkPoint);
    }

    public void updateStatusInfo(UUID id, String status, OffsetDateTime statusChangeDate, String statusChangeReason)
            throws ResourceNotFoundException {
        ShipmentTracking shipmentTracking = findById(id);
        shipmentTracking.setStatus(status);
        shipmentTracking.setStatusChangeDate(statusChangeDate);
        shipmentTracking.setStatusChangeReason(statusChangeReason);
    }

    public void updateEstimatedDeliveryDate(UUID id, OffsetDateTime estimatedDeliveryDate)
            throws ResourceNotFoundException {
        ZoneOffset zoneOffset = estimatedDeliveryDate.getOffset();
        ZoneId zoneId = ZoneId.ofOffset("UTC", zoneOffset);
        if(estimatedDeliveryDate.isAfter(OffsetDateTime.now(zoneId))){
            ShipmentTracking shipmentTracking = findById(id);
            shipmentTracking.setEstimatedDeliveryDate(estimatedDeliveryDate);
        }
        else{
            throw new IllegalArgumentException("Delivery date must be greater than current date");
        }
    }

    public void updateAddressFrom(UUID id, Address addressFrom) throws ResourceNotFoundException {
        ShipmentTracking shipmentTracking = findById(id);
        shipmentTracking.setAddressFrom(addressFrom);
    }

    public void updateAddressTo(UUID id, Address addressTo) throws ResourceNotFoundException {
        ShipmentTracking shipmentTracking = findById(id);
        shipmentTracking.setAddressFrom(addressTo);
    }

    /*public void fullUpdate(UUID id, ShipmentTracking shipmentTracking) throws ResourceNotFoundException {
        ShipmentTracking toBeUpdated = findById(id);
        toBeUpdated.setHref(shipmentTracking.getHref());
        toBeUpdated.setCarrier(shipmentTracking.getCarrier());
        toBeUpdated.setCarrierTrackingUrl(shipmentTracking.getCarrierTrackingUrl());
        toBeUpdated.setStatus(shipmentTracking.getStatus());
        toBeUpdated.setStatusChangeDate(shipmentTracking.getStatusChangeDate());
        toBeUpdated.setStatusChangeReason(shipmentTracking.getStatusChangeReason());
        toBeUpdated.setAddressFrom(shipmentTracking.getAddressFrom());
        toBeUpdated.setAddressTo(shipmentTracking.getAddressTo());
        toBeUpdated.setEstimatedDeliveryDate(shipmentTracking.getEstimatedDeliveryDate());
        toBeUpdated.setWeight(shipmentTracking.getWeight());
        toBeUpdated.setTrackingDate(shipmentTracking.getTrackingDate());
        toBeUpdated.setTrackingCode(shipmentTracking.getTrackingCode());
        toBeUpdated.setCheckpoints(shipmentTracking.getCheckpoints());
    }

    public void updatePartially(UUID id, Map<String, Object> changes) throws ResourceNotFoundException {
        ShipmentTracking shipmentTracking = findById(id);
        changes.forEach((field, value) -> {
            switch(field){
                case "href": shipmentTracking.setHref((String) value);
                    break;
                case "carrier": shipmentTracking.setCarrier((String) value);
                    break;
                case "carrierTrackingUrl": shipmentTracking.setCarrierTrackingUrl((String) value);
                    break;
                case "status": shipmentTracking.setStatus((String) value);
                    break;
                case "statusChangeDate": shipmentTracking.setStatusChangeDate((OffsetDateTime) value);
                    break;
                case "statusChangeReason": shipmentTracking.setStatusChangeReason((String) value);
                    break;
                case "addressFrom": shipmentTracking.setAddressFrom((Address) value);
                    break;
                case "addressTo": shipmentTracking.setAddressTo((Address) value);
                    break;
                case "estimatedDeliveryDate": shipmentTracking.setEstimatedDeliveryDate((OffsetDateTime) value);
                    break;
                case "trackingDate": shipmentTracking.setTrackingDate((OffsetDateTime) value);
                    break;
                case "weight": shipmentTracking.setWeight((Integer) value);
                    break;
                case "trackingCode": shipmentTracking.setTrackingCode((String) value);
                    break;
                case "checkPoints": shipmentTracking.setCheckpoints((List<CheckPoint>) value);
                    break;
            }
        });*/
}

