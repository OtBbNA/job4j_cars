package ru.job4j.cars.repository.brand;

import ru.job4j.cars.model.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandRepository {

    Brand create(Brand brand);

    void delete(int brandId);

    Optional<Brand> findById(int brandId);

    List<Brand> findAllOrderById();
}
