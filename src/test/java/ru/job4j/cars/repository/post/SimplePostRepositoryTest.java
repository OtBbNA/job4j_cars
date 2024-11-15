package ru.job4j.cars.repository.post;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.cars.model.Brand;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.File;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.CrudRepository;
import ru.job4j.cars.repository.brand.BrandRepository;
import ru.job4j.cars.repository.brand.SimpleBrandRepository;
import ru.job4j.cars.repository.car.CarRepository;
import ru.job4j.cars.repository.car.SimpleCarRepository;
import ru.job4j.cars.repository.file.FileRepository;
import ru.job4j.cars.repository.file.SimpleFileRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class SimplePostRepositoryTest {

    private static SessionFactory sf;
    private static PostRepository postRepository;
    private static FileRepository fileRepository;
    private static CarRepository carRepository;
    private static BrandRepository brandRepository;

    @BeforeAll
    public static void init() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        postRepository = new SimplePostRepository(new CrudRepository(sf));
        fileRepository = new SimpleFileRepository(new CrudRepository(sf));
        carRepository = new SimpleCarRepository(new CrudRepository(sf));
        brandRepository = new SimpleBrandRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void clear() {
        for (File file : fileRepository.findAll()) {
            fileRepository.deleteById(file.getId());
        }
        for (Post post : postRepository.findAllOrderById()) {
            postRepository.delete(post.getId());
        }
    }

    @Test
    void whenCreateThenGetSame() {
        Post post = new Post();
        post.setDescription("test1");
        postRepository.create(post);

        var expected = post.getDescription();
        var result = postRepository.findById(post.getId()).get().getDescription();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void whenCreateThenFindByIdSame() {
        Post post = new Post();
        post.setDescription("test1");
        postRepository.create(post);

        var expected = post;
        var result = postRepository.findById(post.getId()).get();

        assertThat(expected).isEqualTo(result);
    }

    @Test
    void whenUpdateThenFindUpdated() {
        Post post = new Post();
        post.setDescription("test1");
        postRepository.create(post);
        Post updatingPost = postRepository.findById(post.getId()).get();
        String updatingName = "test2";
        updatingPost.setDescription(updatingName);

        postRepository.update(updatingPost);
        var result = postRepository.findById(post.getId()).get().getDescription();
        var expected = updatingName;

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void whenDeleteThenFindEmpty() {
        Post post = new Post();
        post.setDescription("test1");
        postRepository.create(post);
        int id = post.getId();

        postRepository.delete(id);

        assertThat(postRepository.findById(id)).isEmpty();
    }

    @Test
    void whenFindAllOrderByIdThenGetList() {
        Post post1 = new Post();
        Post post2 = new Post();
        Post post3 = new Post();
        post1.setDescription("test1");
        post2.setDescription("test2");
        post3.setDescription("test3");
        postRepository.create(post1);
        postRepository.create(post2);
        postRepository.create(post3);

        var expected = List.of(post1, post2, post3);
        var result = postRepository.findAllOrderById();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void whenFindAllByLastDayThenGetListByLastDayPosts() {
        Post post1 = new Post();
        Post post2 = new Post();
        Post post3 = new Post();
        post1.setDescription("test1");
        post2.setDescription("test2");
        post3.setDescription("test3");
        post1.setCreated(LocalDateTime.now().minusDays(2));
        post2.setCreated(LocalDateTime.now().minusDays(2));
        post3.setCreated(LocalDateTime.now());
        postRepository.create(post1);
        postRepository.create(post2);
        postRepository.create(post3);

        var expected = List.of(post3);
        var result = postRepository.findAllByLastDay();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void whenFindAllByImageThenGetListByPostsWithImage() {
        Post post1 = new Post();
        post1.setDescription("test1");
        Post post2 = new Post();
        post2.setDescription("test2");

        postRepository.create(post1);
        postRepository.create(post2);

        File file1 = new File();
        file1.setName("file1");
        file1.setPath("path1");

        File file2 = new File();
        file2.setName("file2");
        file2.setPath("path2");

        fileRepository.save(file1);
        fileRepository.save(file2);

        post1.setFiles(List.of(file1));
        post2.setFiles(List.of(file2));

        postRepository.update(post1);
        postRepository.update(post2);

        var expected = List.of(post1, post2);
        var result = postRepository.findAllByImage();

        assertThat(result).hasSize(2).isEqualTo(expected);
    }

    @Test
    void whenFindAllByBrandThenGetListOfPostsWithThisBrand() {
        Post post1 = new Post();
        Post post2 = new Post();
        Post post3 = new Post();
        post1.setDescription("test1");
        post2.setDescription("test2");
        post3.setDescription("test3");

        Brand brand1 = new Brand();
        Brand brand2 = new Brand();
        brand1.setName("toyota");
        brand2.setName("suzuki");
        brandRepository.create(brand1);
        brandRepository.create(brand2);

        Car car1 = new Car();
        Car car2 = new Car();
        Car car3 = new Car();
        car1.setBrand(brand1);
        car2.setBrand(brand2);
        car3.setBrand(brand2);
        carRepository.create(car1);
        carRepository.create(car2);
        carRepository.create(car3);

        post1.setCar(car1);
        post2.setCar(car2);
        post3.setCar(car3);

        postRepository.create(post1);
        postRepository.create(post2);
        postRepository.create(post3);

        var expected = List.of(post2, post3);
        var result = postRepository.findAllByBrand(brand2);

        assertThat(result).isEqualTo(expected);
    }
}