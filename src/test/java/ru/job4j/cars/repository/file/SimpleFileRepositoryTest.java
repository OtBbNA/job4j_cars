package ru.job4j.cars.repository.file;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.cars.model.File;
import ru.job4j.cars.repository.CrudRepository;

import static org.assertj.core.api.Assertions.*;

@Transactional
class SimpleFileRepositoryTest {

    private static FileRepository fileRepository;

    @BeforeAll
    public static void init() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        fileRepository = new SimpleFileRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void clear() {
        for (File file : fileRepository.findAll()) {
            fileRepository.deleteById(file.getId());
        }
    }

    @Test
    void whenSaveThenGetSame() {
        File file = new File();
        file.setPath("/files");
        fileRepository.save(file);

        var expected = file.getName();
        var result = fileRepository.findById(file.getId()).get().getName();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void whenCreateThenFindByIdSame() {
        File file = new File();
        file.setPath("/files");
        fileRepository.save(file);

        var expected = file;
        var result = fileRepository.findById(file.getId()).get();

        assertThat(expected).isEqualTo(result);
    }

    @Test
    void whenDeleteThenFindEmpty() {
        File file = new File();
        file.setPath("/files");
        fileRepository.save(file);
        int id = file.getId();

        fileRepository.deleteById(id);

        assertThat(fileRepository.findById(id)).isEmpty();
    }
}