package ru.job4j.cars.repository.price;

import ru.job4j.cars.model.PriceHistory;

import java.util.List;
import java.util.Optional;

public interface PriceHistoryRepository {

    PriceHistory create(PriceHistory priceHistory);

    void update(PriceHistory priceHistory);

    void delete(int priceHistoryId);

    Optional<PriceHistory> findById(int priceHistoryId);

    List<PriceHistory> findAllOrderById();
}
