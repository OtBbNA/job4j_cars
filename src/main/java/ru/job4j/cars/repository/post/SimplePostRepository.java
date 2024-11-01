package ru.job4j.cars.repository.post;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Brand;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimplePostRepository implements PostRepository {

    private final CrudRepository crudRepository;

    @Override
    public Post create(Post post) {
        crudRepository.run(session -> session.save(post));
        return post;
    }

    @Override
    public void update(Post post) {
        crudRepository.run(session -> session.merge(post));
    }

    @Override
    public void delete(int postId) {
        crudRepository.run(
                "delete from Post where id = :fId",
                Map.of("fId", postId)
        );
    }

    @Override
    public Optional<Post> findById(int postId) {
        return crudRepository.optional(
                "from Post where id = :fId", Post.class,
                Map.of("fId", postId)
        );
    }

    @Override
    public List<Post> findAllOrderById() {
        return crudRepository.query("from Post order by id asc", Post.class);
    }

    @Override
    public List<Post> findAllByLastDay() {
        return crudRepository.query("from Post where created >= :fDayAgo", Post.class, Map.of("fDayAgo", LocalDateTime.now().minusDays(1)));
    }

    @Override
    public List<Post> findAllByImage() {
        return crudRepository.query("from Post p where size(p.files) != 0", Post.class);
    }

    @Override
    public List<Post> findAllByBrand(Brand brand) {
        return crudRepository.query("from Post p where p.car.brand = :fBrand", Post.class, Map.of("fBrand", brand));
    }
}
