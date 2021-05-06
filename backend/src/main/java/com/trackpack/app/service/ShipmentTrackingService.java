package com.trackpack.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.trackpack.app.exceptions.ResourceAlreadyExistsException;
import com.trackpack.app.model.tracking.Address;
import com.trackpack.app.model.tracking.CheckPoint;
import com.trackpack.app.model.tracking.ShipmentTracking;
import com.trackpack.app.repository.DeliveryStatisticsRepository;
import com.trackpack.app.repository.ShipmentTrackingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.*;

@Service
public class ShipmentTrackingService {

    private final ShipmentTrackingRepository repository;
    private final DeliveryStatisticsRepository deliveryRepository;
    private final ObjectMapper objectMapper;

    public ShipmentTrackingService(ShipmentTrackingRepository repository, DeliveryStatisticsRepository deliveryRepository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.deliveryRepository = deliveryRepository;
        this.objectMapper = objectMapper;
    }

    public List<ShipmentTracking> findAll() {
        return repository.findAll();
    }

    public Optional<ShipmentTracking> findById(UUID id) {
        return repository.findById(id);
    }

    @Transactional
    public void add(ShipmentTracking shipmentTracking) {
        repository.save(shipmentTracking);
    }

    public Optional<ShipmentTracking> findByTrackingCode(String trackingCode){
        return repository.findByTrackingCode(trackingCode);
    }

    @Transactional
    public void delete(ShipmentTracking shipmentTracking) {
        repository.delete(shipmentTracking);
    }

    @Transactional
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Transactional
    public void addCheckpoint(UUID id, CheckPoint checkPoint) {
        ShipmentTracking shipmentTracking = findById(id).get();
        List<CheckPoint> checkPoints = shipmentTracking.getCheckPoints();
        if (checkPoints == null) {
            checkPoints = new ArrayList<>();
        }
        checkPoints.add(checkPoint);
        repository.save(shipmentTracking);
    }

    @Transactional
    public void updateStatusInfo(UUID id, String status, OffsetDateTime statusChangeDate, String statusChangeReason) {
        ShipmentTracking shipmentTracking = findById(id).get();
        if (status != null) {
            shipmentTracking.setStatus(status);
        }
        shipmentTracking.setStatusChangeDate(statusChangeDate);
        shipmentTracking.setStatusChangeReason(statusChangeReason);
        repository.save(shipmentTracking);
    }

    @Transactional
    public void updateEstimatedDeliveryDate(UUID id, OffsetDateTime estimatedDeliveryDate) {
        ShipmentTracking shipmentTracking = findById(id).get();
        shipmentTracking.setEstimatedDeliveryDate(estimatedDeliveryDate);
        repository.save(shipmentTracking);
    }

    @Transactional
    public void updateAddressFrom(UUID id, Address addressFrom) {
        ShipmentTracking shipmentTracking = findById(id).get();
        shipmentTracking.setAddressFrom(addressFrom);
        repository.save(shipmentTracking);
    }

    @Transactional
    public void updateAddressTo(UUID id, Address addressTo) {
        ShipmentTracking shipmentTracking = findById(id).get();
        shipmentTracking.setAddressTo(addressTo);
        repository.save(shipmentTracking);
    }

    @Transactional
    public void updateCheckPoints(UUID id, List<CheckPoint> checkPoints) {
        ShipmentTracking shipmentTracking = findById(id).get();
        shipmentTracking.setCheckPoints(checkPoints);
        repository.save(shipmentTracking);
    }

    @Transactional
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
                shipmentTracking.setTrackingDate(OffsetDateTime.parse((String) value));
            }
            if(field.equals("status") && value != null){
                shipmentTracking.setStatus((String) value);
            }
            if(field.equals("statusChangeDate")){
                shipmentTracking.setStatusChangeDate(OffsetDateTime.parse((String) value));
            }
            if(field.equals("statusChangeReason")){
                shipmentTracking.setStatusChangeReason((String) value);
            }
            if(field.equals("weight")){
                shipmentTracking.setWeight((Double) value);
            }
            if(field.equals("estimatedDeliveryDate")){
                shipmentTracking.setEstimatedDeliveryDate(OffsetDateTime.parse((String) value));
            }
        });
        repository.save(shipmentTracking);
    }

    public List<ShipmentTracking> getDeliveredParcels() {
        return repository.getDeliveredParcels();
    }

    public List<ShipmentTracking> getNewDeliveredParcels(OffsetDateTime lastUpdateDate) {
        return repository.getNewDeliveredParcels(lastUpdateDate);
    }

    public String findCityFromById(UUID id) {
        return repository.findCityFromById(id);
    }

    public String findCityToById(UUID id) {
        return repository.findCityToById(id);
    }

    public List<ShipmentTracking> getCurrentParcels() {
        return repository.getCurrentParcels();
    }

    public Integer getStatisticsSize(UUID id) {
        String cityFrom = findCityFromById(id);
        String cityTo = findCityToById(id);
        DeliveryStatisticsService deliveryService = new DeliveryStatisticsService(deliveryRepository);
        if(deliveryService.findByCities(cityFrom, cityTo).isPresent()){
            return deliveryService.findByCities(cityFrom, cityTo).get().getParcelAmount();
        }
        return 0;
    }

    @Transactional
    public ShipmentTracking patch(JsonMergePatch patch, ShipmentTracking toBeUpdated) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(toBeUpdated, JsonNode.class));
        ShipmentTracking updated = objectMapper.treeToValue(patched, ShipmentTracking.class);
        add(updated);
        return updated;
    }

}


