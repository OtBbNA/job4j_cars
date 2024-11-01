package ru.job4j.cars.repository.car;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.CrudRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SimpleCarRepositoryTest {

    private static CarRepository carRepository;

    @BeforeAll
    public static void init() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        carRepository = new SimpleCarRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void clear() {
        for (Car car : carRepository.findAllOrderById()) {
            carRepository.delete(car.getId());
        }
    }

    @Test
    void whenCreateThenGetSame() {
        Car car = new Car();
        car.setName("test1");
        carRepository.create(car);

        var expected = car.getName();
        var result = carRepository.findById(car.getId()).get().getName();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void whenCreateThenFindByIdSame() {
        Car car = new Car();
        car.setName("test1");
        carRepository.create(car);

        var expected = car;
        var result = carRepository.findById(car.getId()).get();

        assertThat(expected).isEqualTo(result);
    }

    @Test
    void whenUpdateThenFindUpdated() {
        Car car = new Car();
        car.setName("test1");
        carRepository.create(car);
        Car updatingCar = carRepository.findById(car.getId()).get();
        String updatingName = "test2";
        updatingCar.setName(updatingName);

        carRepository.update(updatingCar);
        var result = carRepository.findById(car.getId()).get().getName();
        var expected = updatingName;

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void whenDeleteThenFindEmpty() {
        Car car = new Car();
        car.setName("test1");
        carRepository.create(car);
        int id = car.getId();

        carRepository.delete(id);

        assertThat(carRepository.findById(id)).isEmpty();
    }

    @Test
    void whenFindAllOrderByIdThenGetList() {
        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();
        car1.setName("test1");
        car2.setName("test2");
        car3.setName("test3");
        carRepository.create(car1);
        carRepository.create(car2);
        carRepository.create(car3);

        List<Car> expected = List.of(car1, car2, car3);

        assertThat(carRepository.findAllOrderById()).isEqualTo(expected);
    }
}