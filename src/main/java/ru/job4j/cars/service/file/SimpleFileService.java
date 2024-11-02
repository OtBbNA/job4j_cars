package ru.job4j.cars.service.file;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.File;
import ru.job4j.cars.repository.file.FileRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimpleFileService implements FileService {

    private final FileRepository fileRepository;

    @Override
    public File save(File file) {
        return fileRepository.save(file);
    }

    @Override
    public Optional<File> findById(int fileId) {
        return fileRepository.findById(fileId);
    }

    @Override
    public List<File> findAll() {
        return fileRepository.findAll();
    }

    @Override
    public void deleteById(int fileId) {
        fileRepository.deleteById(fileId);
    }
}
