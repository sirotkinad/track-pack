package com.trackpack.app.service;

import com.trackpack.app.model.tracking.DeliveryStatistics;
import com.trackpack.app.repository.DeliveryStatisticsRepository;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryStatisticsService {

    private final DeliveryStatisticsRepository repository;

    public DeliveryStatisticsService(DeliveryStatisticsRepository repository) {
        this.repository = repository;
    }

    public List<DeliveryStatistics> findAll() {
        return repository.findAll();
    }

    public OffsetDateTime findMaxLastUpdateDate() {
        return OffsetDateTime.ofInstant(repository.findMaxLastUpdateDate().toInstant(), ZoneId.systemDefault());
    }

    public Optional<DeliveryStatistics> findByCities(String cityFrom, String cityTo) {
        return repository.findByCities(cityFrom, cityTo);
    }

    public void save(DeliveryStatistics deliveryStatistics) {
        repository.save(deliveryStatistics);
    }

}
