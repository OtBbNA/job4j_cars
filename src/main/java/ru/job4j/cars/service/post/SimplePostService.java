package ru.job4j.cars.service.post;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Brand;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.post.PostRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimplePostService implements PostService {

    private final PostRepository postRepository;

    @Override
    public Post create(Post post) {
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
