package com.trackpack.app.service;

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

    public Optional<ShipmentTracking> findById(UUID id) {
        return repository.findById(id);
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

    public void addCheckpoint(UUID id, CheckPoint checkPoint) {
            ShipmentTracking shipmentTracking = findById(id).get();
            List<CheckPoint> checkPoints = shipmentTracking.getCheckpoints();
            if (checkPoints == null) {
                checkPoints = new ArrayList<>();
            }
            checkPoints.add(checkPoint);
    }

    public void updateStatusInfo(UUID id, String status, OffsetDateTime statusChangeDate, String statusChangeReason) {
            ShipmentTracking shipmentTracking = findById(id).get();
            if (status != null) {
                shipmentTracking.setStatus(status);
            }
            shipmentTracking.setStatusChangeDate(statusChangeDate);
            shipmentTracking.setStatusChangeReason(statusChangeReason);
        }

    public void updateEstimatedDeliveryDate(UUID id, OffsetDateTime estimatedDeliveryDate) {
            ShipmentTracking shipmentTracking = findById(id).get();
            shipmentTracking.setEstimatedDeliveryDate(estimatedDeliveryDate);
    }

    public void updateAddressFrom(UUID id, Address addressFrom) {
        ShipmentTracking shipmentTracking = findById(id).get();
        shipmentTracking.setAddressFrom(addressFrom);
    }

    public void updateAddressTo(UUID id, Address addressTo) {
        ShipmentTracking shipmentTracking = findById(id).get();
        shipmentTracking.setAddressFrom(addressTo);
    }

    public void update(UUID id, Map<String, Object> changes) {
        ShipmentTracking shipmentTracking = findById(id).get();
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


