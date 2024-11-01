package ru.job4j.cars.repository.price;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.repository.CrudRepository;
import ru.job4j.cars.repository.car.CarRepository;
import ru.job4j.cars.repository.car.SimpleCarRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SimplePriceHistoryRepositoryTest {

    private static PriceHistoryRepository simplePriceHistoryRepository;

    @BeforeAll
    public static void init() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        simplePriceHistoryRepository = new SimplePriceHistoryRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void clear() {
        for (PriceHistory priceHistory : simplePriceHistoryRepository.findAllOrderById()) {
            simplePriceHistoryRepository.delete(priceHistory.getId());
        }
    }

    @Test
    void whenCreateThenGetSame() {
        PriceHistory priceHistory = new PriceHistory();
        simplePriceHistoryRepository.create(priceHistory);

        var expected = priceHistory;
        var result = simplePriceHistoryRepository.findById(priceHistory.getId()).get();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void whenCreateThenFindByIdSame() {
        PriceHistory priceHistory = new PriceHistory();
        simplePriceHistoryRepository.create(priceHistory);

        var expected = priceHistory;
        var result = simplePriceHistoryRepository.findById(priceHistory.getId()).get();

        assertThat(expected).isEqualTo(result);
    }

    @Test
    void whenUpdateThenFindUpdated() {
        PriceHistory priceHistory = new PriceHistory();
        simplePriceHistoryRepository.create(priceHistory);
        PriceHistory updatingPriceHistory  = simplePriceHistoryRepository.findById(priceHistory.getId()).get();
        updatingPriceHistory.setAfter(2);

        simplePriceHistoryRepository.update(updatingPriceHistory);
        var result = simplePriceHistoryRepository.findById(priceHistory.getId()).get().getAfter();
        var expected = 2;

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void whenDeleteThenFindEmpty() {
        PriceHistory priceHistory = new PriceHistory();
        simplePriceHistoryRepository.create(priceHistory);
        int id = priceHistory.getId();

        simplePriceHistoryRepository.delete(id);

        assertThat(simplePriceHistoryRepository.findById(id)).isEmpty();
    }

    @Test
    void whenFindAllOrderByIdThenGetList() {
        PriceHistory priceHistory1 = new PriceHistory();
        PriceHistory priceHistory2 = new PriceHistory();
        PriceHistory priceHistory3 = new PriceHistory();
        simplePriceHistoryRepository.create(priceHistory1);
        simplePriceHistoryRepository.create(priceHistory2);
        simplePriceHistoryRepository.create(priceHistory3);

        var expected = List.of(priceHistory1, priceHistory2, priceHistory3);
        var result = simplePriceHistoryRepository.findAllOrderById();

        assertThat(result).isEqualTo(expected);
    }
}