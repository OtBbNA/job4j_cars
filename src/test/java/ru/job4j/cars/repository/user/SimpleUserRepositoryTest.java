package ru.job4j.cars.repository.user;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.CrudRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class SimpleUserRepositoryTest {

    private static UserRepository userRepository;

    @BeforeAll
    public static void init() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        userRepository = new SimpleUserRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void clear() {
        for (User user : userRepository.findAllOrderById()) {
            userRepository.delete(user.getId());
        }
    }

    @Test
    void whenCreateThenGetSame() {
        User user = new User();
        user.setLogin("test1");
        userRepository.create(user);

        var expected = user.getLogin();
        var result = userRepository.findById(user.getId()).get().getLogin();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void whenCreateThenFindByIdSame() {
        User user = new User();
        user.setLogin("test1");
        userRepository.create(user);

        var expected = user;
        var result = userRepository.findById(user.getId()).get();

        assertThat(expected).isEqualTo(result);
    }

    @Test
    void whenUpdateThenFindUpdated() {
        User user = new User();
        user.setLogin("test1");
        userRepository.create(user);
        User updatingUser = userRepository.findById(user.getId()).get();
        String updatingLogin = "test2";
        updatingUser.setLogin(updatingLogin);

        userRepository.update(updatingUser);
        var result = userRepository.findById(user.getId()).get().getLogin();
        var expected = updatingLogin;

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void whenDeleteThenFindEmpty() {
        User user = new User();
        user.setLogin("test1");
        userRepository.create(user);
        int id = user.getId();

        userRepository.delete(id);

        assertThat(userRepository.findById(id)).isEmpty();
    }

    @Test
    void whenFindAllOrderByIdThenGetList() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setLogin("test1");
        user2.setLogin("test2");
        user3.setLogin("test3");
        userRepository.create(user1);
        userRepository.create(user2);
        userRepository.create(user3);

        List<User> expected = List.of(user1, user2, user3);

        assertThat(userRepository.findAllOrderById()).isEqualTo(expected);
    }

    @Test
    void whenFindByLikeLoginThenGetListWithThisLogin() {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        user1.setLogin("test1");
        user2.setLogin("test1");
        user3.setLogin("test3");
        userRepository.create(user1);
        userRepository.create(user2);
        userRepository.create(user3);

        List<User> expected = List.of(user1, user2);
        var result = userRepository.findByLikeLogin(user1.getLogin());

        assertThat(expected).isEqualTo(result);
    }

    @Test
    void whenFindByLoginThenGetUserWithThisLogin() {
        User user = new User();
        user.setLogin("test1");
        userRepository.create(user);

        var expected = user;
        var result = userRepository.findByLogin(user.getLogin()).get();

        assertThat(expected).isEqualTo(result);
    }
}