package ru.job4j.cars.service.post;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.Brand;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.post.PostRepository;
import ru.job4j.cars.service.file.FileService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimplePostService implements PostService {

    private final PostRepository postRepository;

    private final FileService fileService;

    private void saveNewFiles(Post post, List<FileDto> images) {
        for (FileDto fileDto : images) {
            post.getFiles().add(fileService.save(fileDto));
        }
    }

    @Override
    public Post create(Post post, List<FileDto> fileDto) {
        saveNewFiles(post, fileDto);
        return postRepository.create(post);
    }

    @Override
    public void update(Post post) {
        postRepository.update(post);
    }

    @Override
    public void delete(int postId) {
        postRepository.delete(postId);
    }

    @Override
    public Optional<Post> findById(int postId) {
        return postRepository.findById(postId);
    }

    @Override
    public List<Post> findAllOrderById() {
        return postRepository.findAllOrderById();
    }

    @Override
    public List<Post> findAllByLastDay() {
        return postRepository.findAllByLastDay();
    }

    @Override
    public List<Post> findAllByImage() {
        return postRepository.findAllByImage();
    }

    @Override
    public List<Post> findAllByBrand(Brand brand) {
        return postRepository.findAllByBrand(brand);
    }
}
