package ru.job4j.cars.repository.brand;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Brand;
import ru.job4j.cars.repository.CrudRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SimpleBrandRepositoryTest {

    private static BrandRepository brandRepository;

    @BeforeAll
    public static void init() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        brandRepository = new SimpleBrandRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void clear() {
        for (Brand brand : brandRepository.findAllOrderById()) {
            brandRepository.delete(brand.getId());
        }
    }

    @Test
    void whenCreateThenGetSame() {
        Brand brand = new Brand();
        brand.setName("test1");
        brandRepository.create(brand);

        var expected = brand.getName();
        var result = brandRepository.findById(brand.getId()).get().getName();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void whenCreateThenFindByIdSame() {
        Brand brand = new Brand();
        brand.setName("test1");
        brandRepository.create(brand);

        var expected = brand;
        var result = brandRepository.findById(brand.getId()).get();

        assertThat(expected).isEqualTo(result);
    }

    @Test
    void whenDeleteThenFindEmpty() {
        Brand brand = new Brand();
        brand.setName("test1");
        brandRepository.create(brand);
        int id = brand.getId();

        brandRepository.delete(id);

        assertThat(brandRepository.findById(id)).isEmpty();
    }

    @Test
    void whenFindAllOrderByIdThenGetList() {
        Brand brand1 = new Brand();
        Brand brand2 = new Brand();
        Brand brand3 = new Brand();
        brand1.setName("test1");
        brand2.setName("test2");
        brand3.setName("test3");
        brandRepository.create(brand1);
        brandRepository.create(brand2);
        brandRepository.create(brand3);

        List<Brand> expected = List.of(brand1, brand2, brand3);

        assertThat(brandRepository.findAllOrderById()).isEqualTo(expected);
    }
}