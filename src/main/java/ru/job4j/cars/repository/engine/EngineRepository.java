package ru.job4j.cars.repository.engine;

import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Optional;

public interface EngineRepository {

    Optional<Engine> findById(int engineId);

    List<Engine> findAllOrderById();
}
