package ru.job4j.cars.service.price;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.repository.price.PriceHistoryRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimplePriceHistoryService implements PriceHistoryService {

    private final PriceHistoryRepository priceHistoryRepository;

    @Override
    public PriceHistory create(PriceHistory priceHistory) {
        return priceHistoryRepository.create(priceHistory);
    }

    @Override
    public void update(PriceHistory priceHistory) {
        priceHistoryRepository.update(priceHistory);
    }

    @Override
    public void delete(int priceHistoryId) {
        priceHistoryRepository.delete(priceHistoryId);
    }

    @Override
    public Optional<PriceHistory> findById(int priceHistoryId) {
        return priceHistoryRepository.findById(priceHistoryId);
    }

    @Override
    public List<PriceHistory> findAllOrderById() {
        return priceHistoryRepository.findAllOrderById();
    }
}
