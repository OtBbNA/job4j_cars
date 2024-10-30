package ru.job4j.cars.repository.post;

import ru.job4j.cars.model.Brand;
import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post create(Post post);

    void update(Post post);

    void delete(int postId);

    Optional<Post> findById(int postId);

    List<Post> findAllOrderById();

    List<Post> findAllByLastDay();

    List<Post> findAllByImage();

    List<Post> findAllByBrand(Brand brand);
}
