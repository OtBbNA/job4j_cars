package ru.job4j.cars.service.brand;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Brand;
import ru.job4j.cars.repository.brand.BrandRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleBrandService implements BrandService {

    BrandRepository brandRepository;

    @Override
    public Brand create(Brand brand) {
        return brandRepository.create(brand);
    }

    @Override
    public void delete(int brandId) {
        brandRepository.delete(brandId);
    }

    @Override
    public Optional<Brand> findById(int brandId) {
        return brandRepository.findById(brandId);
    }

    @Override
    public List<Brand> findAllOrderById() {
        return brandRepository.findAllOrderById();
    }
}
