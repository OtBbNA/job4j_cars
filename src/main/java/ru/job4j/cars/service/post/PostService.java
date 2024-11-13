package ru.job4j.cars.service.post;

import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.Brand;
import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    Post create(Post post, List<FileDto> fileDto);

    void update(Post post);

    void delete(int postId);

    Optional<Post> findById(int postId);

    List<Post> findAllOrderById();

    List<Post> findAllByLastDay();

    List<Post> findAllByImage();

    List<Post> findAllByBrand(Brand brand);
}
