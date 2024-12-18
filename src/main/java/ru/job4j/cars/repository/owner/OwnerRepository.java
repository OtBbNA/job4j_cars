package ru.job4j.cars.repository.owner;

import ru.job4j.cars.model.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerRepository {

    Owner create(Owner owner);

    void update(Owner owner);

    void delete(int ownerId);

    Optional<Owner> findById(int ownerId);

    List<Owner> findAllOrderById();
}
