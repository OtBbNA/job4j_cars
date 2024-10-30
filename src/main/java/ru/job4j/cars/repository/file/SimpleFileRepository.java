package ru.job4j.cars.repository.file;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.File;
import ru.job4j.cars.repository.CrudRepository;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimpleFileRepository implements FileRepository {

    private final CrudRepository crudRepository;

    @Override
    public File save(File file) {
        crudRepository.run(session -> session.persist(file));
        return file;
    }

    @Override
    public Optional<File> findById(int fileId) {
        return crudRepository.optional(
                "from File where id = :fId", File.class,
                Map.of("fId", fileId)
        );
    }

    @Override
    public void deleteById(int fileId) {
        crudRepository.run(
                "delete from File where id = :fId",
                Map.of("fId", fileId)
        );
    }
}
