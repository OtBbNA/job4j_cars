package ru.job4j.cars.service.user;

import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> create(User user);

    void update(User user);

    void delete(int userId);

    List<User> findAllOrderById();

    Optional<User> findById(int userId);

    List<User> findByLikeLogin(String key);

    Optional<User> findByLogin(String login);

    Optional<User> findByEmailAndPassword(String login, String password);
}
