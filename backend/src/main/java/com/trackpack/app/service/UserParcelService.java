package com.trackpack.app.service;

import com.trackpack.app.exceptions.ResourceAlreadyExistsException;
import com.trackpack.app.exceptions.ResourceNotFoundException;
import com.trackpack.app.keys.UserParcelKey;
import com.trackpack.app.model.joining.UserParcel;
import com.trackpack.app.model.tracking.ShipmentTracking;
import com.trackpack.app.repository.ShipmentTrackingRepository;
import com.trackpack.app.repository.UserParcelRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserParcelService {

    private final UserParcelRepository repository;
    private final ShipmentTrackingRepository parcelRepository;

    public UserParcelService(UserParcelRepository repository, ShipmentTrackingRepository parcelRepository) {
        this.repository = repository;
        this.parcelRepository = parcelRepository;
    }

    public void addParcelForUser(UUID userId, UUID parcelId) {
        UserParcelKey id = new UserParcelKey(userId, parcelId);
        if(repository.existsById(id)){
            throw new ResourceAlreadyExistsException("Parcel already exists in user's tracking list");
        }
        UserParcel userParcel = new UserParcel(id);
        repository.save(userParcel);
    }

    public void deleteParcelFromUser(UUID userId, UUID parcelId) {
        UserParcelKey id = new UserParcelKey(userId, parcelId);
        if(repository.existsById(id)){
            repository.deleteById(id);
        }
        else{
            throw new ResourceNotFoundException("User does not have such parcel");
        }
    }

    public void setParcelName(UUID userId, UUID parcelId, String name) {
        UserParcelKey id = new UserParcelKey(userId, parcelId);
        UserParcel userParcel = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User's parcel is not found"));
        userParcel.setParcelName(name);
        repository.save(userParcel);
    }

    public List<ShipmentTracking> getParcelsByUserId(UUID userId) {
        List<String> parcelsId = repository.getParcelsByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User's parcel list is empty"));;
        List<ShipmentTracking> parcels = new ArrayList<>();
        ShipmentTracking parcel = new ShipmentTracking();
        for (String id : parcelsId) {
            parcel = parcelRepository.findById(UUID.fromString(id)).get();
            parcels.add(parcel);
        }
        return parcels;
    }

    public String getParcelName(UUID userId, UUID parcelId) {
        UserParcelKey id = new UserParcelKey(userId, parcelId);
        UserParcel userParcel = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User's parcel is not found"));
        return userParcel.getParcelName();
    }

}
