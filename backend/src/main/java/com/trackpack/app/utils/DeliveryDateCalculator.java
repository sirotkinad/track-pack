package com.trackpack.app.utils;

import com.trackpack.app.model.tracking.DeliveryStatistics;
import com.trackpack.app.model.tracking.ShipmentTracking;
import com.trackpack.app.service.DeliveryStatisticsService;
import com.trackpack.app.service.ShipmentTrackingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class DeliveryDateCalculator {

    private final ShipmentTrackingService parcelService;
    private final DeliveryStatisticsService deliveryService;
    private static final String EXECUTING_TIME = "0 0 0 * * *"; //every day at midnight

    public DeliveryDateCalculator(ShipmentTrackingService parcelService, DeliveryStatisticsService deliveryService) {
        this.parcelService = parcelService;
        this.deliveryService = deliveryService;
    }

    @Scheduled(cron = EXECUTING_TIME)
    public void deliveryCalculator() {
        List<ShipmentTracking> deliveredParcels;
        if (deliveryService.findAll().isEmpty()) {
            deliveredParcels = parcelService.getDeliveredParcels();
        } else {
            OffsetDateTime maxLastUpdateDate = deliveryService.findMaxLastUpdateDate();
            deliveredParcels = parcelService.getNewDeliveredParcels(maxLastUpdateDate.truncatedTo(ChronoUnit.DAYS));
        }
        if (deliveredParcels.size() != 0) {
            for (ShipmentTracking parcel : deliveredParcels) {
                addToStatistics(parcel);
            }
        }
        setEstimatedDeliveryDate();
    }

    public void addToStatistics(ShipmentTracking parcel) {
        String cityFrom = parcelService.findCityFromById(parcel.getId());
        String cityTo = parcelService.findCityToById(parcel.getId());
        DeliveryStatistics deliveryStatistics;
        if (deliveryService.findByCities(cityFrom, cityTo).isPresent()) {
            deliveryStatistics = deliveryService.findByCities(cityFrom, cityTo).get();
            int parcelAmount = deliveryStatistics.getParcelAmount();
            double averageDeliveryTime = (deliveryStatistics.getAverageDeliveryTime() * parcelAmount + getDurationInDays(parcel)) / (parcelAmount + 1);
            deliveryStatistics.setAverageDeliveryTime(averageDeliveryTime);
            deliveryStatistics.setParcelAmount(parcelAmount + 1);
            deliveryStatistics.setLastUpdateDate(OffsetDateTime.now());
        } else {
            deliveryStatistics = new DeliveryStatistics(cityFrom, cityTo, 1,
                    getDurationInDays(parcel), OffsetDateTime.now());
        }
        deliveryService.save(deliveryStatistics);
    }

    public long getDurationInDays(ShipmentTracking parcel) {
        OffsetDateTime beginDate = parcel.getTrackingDate();
        OffsetDateTime deliveryDate = parcel.getEstimatedDeliveryDate();
        Duration duration = Duration.between(beginDate, deliveryDate);
        return duration.toDays();
    }

    public void setEstimatedDeliveryDate() {
        List<ShipmentTracking> currentParcels = parcelService.getCurrentParcels();
        for (ShipmentTracking parcel : currentParcels) {
            String cityFrom = parcelService.findCityFromById(parcel.getId());
            String cityTo = parcelService.findCityToById(parcel.getId());
            if (deliveryService.findByCities(cityFrom, cityTo).isPresent()) {
                double averageTime = deliveryService.findByCities(cityFrom, cityTo).get().getAverageDeliveryTime();
                OffsetDateTime deliveryDate = parcel.getTrackingDate().plusDays((long) Math.ceil(averageTime));
                parcelService.updateEstimatedDeliveryDate(parcel.getId(), deliveryDate);
            }
        }
    }

}
