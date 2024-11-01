package ru.job4j.cars.repository.owner;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.repository.CrudRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleOwnerRepositoryTest {

    private static OwnerRepository ownerRepository;

    @BeforeAll
    public static void init() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        ownerRepository = new SimpleOwnerRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void clear() {
        for (Owner owner : ownerRepository.findAllOrderById()) {
            ownerRepository.delete(owner.getId());
        }
    }

    @Test
    void whenCreateThenGetSame() {
        Owner owner = new Owner();
        owner.setName("test1");
        ownerRepository.create(owner);

        var expected = owner.getName();
        var result = ownerRepository.findById(owner.getId()).get().getName();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void whenCreateThenFindByIdSame() {
        Owner owner = new Owner();
        owner.setName("test1");
        ownerRepository.create(owner);

        var expected = owner;
        var result = ownerRepository.findById(owner.getId()).get();

        assertThat(expected).isEqualTo(result);
    }

    @Test
    void whenUpdateThenFindUpdated() {
        Owner owner = new Owner();
        owner.setName("test1");
        ownerRepository.create(owner);
        Owner updatingOwner = ownerRepository.findById(owner.getId()).get();
        String updatingName = "test2";
        updatingOwner.setName(updatingName);

        ownerRepository.update(updatingOwner);
        var result = ownerRepository.findById(owner.getId()).get().getName();
        var expected = updatingName;

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void whenDeleteThenFindEmpty() {
        Owner owner = new Owner();
        owner.setName("test1");
        ownerRepository.create(owner);
        int id = owner.getId();

        ownerRepository.delete(id);

        assertThat(ownerRepository.findById(id)).isEmpty();
    }

    @Test
    void whenFindAllOrderByIdThenGetList() {
        Owner owner1 = new Owner();
        Owner owner2 = new Owner();
        Owner owner3 = new Owner();
        owner1.setName("test1");
        owner2.setName("test2");
        owner3.setName("test3");
        ownerRepository.create(owner1);
        ownerRepository.create(owner2);
        ownerRepository.create(owner3);

        List<Owner> expected = List.of(owner1, owner2, owner3);

        assertThat(ownerRepository.findAllOrderById()).isEqualTo(expected);
    }
}