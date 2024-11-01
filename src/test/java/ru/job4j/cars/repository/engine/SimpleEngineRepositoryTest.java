package ru.job4j.cars.repository.engine;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.CrudRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class SimpleEngineRepositoryTest {

    private static EngineRepository engineRepository;

    @BeforeAll
    public static void init() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        engineRepository = new SimpleEngineRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void clear() {
        for (Engine engine : engineRepository.findAllOrderById()) {
            engineRepository.delete(engine.getId());
        }
    }

    @Test
    void whenCreateThenFindByIdSame() {
        Engine engine = new Engine();
        engine.setName("test1");
        engineRepository.create(engine);

        var expected = engine;
        var result = engineRepository.findById(engine.getId()).get();

        assertThat(expected).isEqualTo(result);
    }

    @Test
    void whenFindAllOrderByIdThenGetList() {
        Engine engine1 = new Engine();
        Engine engine2 = new Engine();
        Engine engine3 = new Engine();
        engine1.setName("test1");
        engine2.setName("test2");
        engine3.setName("test3");
        engineRepository.create(engine1);
        engineRepository.create(engine2);
        engineRepository.create(engine3);

        List<Engine> expected = List.of(engine1, engine2, engine3);

        assertThat(engineRepository.findAllOrderById()).isEqualTo(expected);
    }
}