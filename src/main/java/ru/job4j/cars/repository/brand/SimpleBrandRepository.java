package ru.job4j.cars.repository.brand;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Brand;
import ru.job4j.cars.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimpleBrandRepository implements BrandRepository {

    CrudRepository crudRepository;

    @Override
    public Brand create(Brand brand) {
        crudRepository.run(session -> session.persist(brand));
        return brand;
    }

    @Override
    public void delete(int brandId) {
        crudRepository.run(
                "delete from Brand where id = :fId",
                Map.of("fId", brandId)
        );
    }

    @Override
    public Optional<Brand> findById(int brandId) {
        return crudRepository.optional(
                "from Brand where id = :fId", Brand.class,
                Map.of("fId", brandId)
        );
    }

    @Override
    public List<Brand> findAllOrderById() {
        return crudRepository.query("from Brand order by id asc", Brand.class);
    }
}
