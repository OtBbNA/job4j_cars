package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.job4j.cars.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserRepository {
    private final SessionFactory sf;

    public User create(User user) {
        Session session = sf.getCurrentSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return user;
    }

    public void update(User user) {
        Session session = sf.getCurrentSession();
        try {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    public void delete(int userId) {
        Session session = sf.getCurrentSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "DELETE User WHERE id = :fId")
                    .setParameter("fId", userId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    public List<User> findAllOrderById() {
        List<User> rsl = new ArrayList<>();
        Session session = sf.getCurrentSession();
        try {
            session.getTransaction().begin();
            Query<User> query = session.createQuery("FROM User", User.class);
            rsl = query.getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return rsl;
    }

    public Optional<User> findById(int userId) {
        User rsl = new User();
        Session session = sf.getCurrentSession();
        try {
            session.getTransaction().begin();
            Query<User> query = session.createQuery("FROM User AS i WHERE i.id = :fId", User.class).setParameter("fId", userId);
            rsl = query.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return Optional.ofNullable(rsl);
    }

    public List<User> findByLikeLogin(String key) {
        List<User> rsl = new ArrayList<>();
        Session session = sf.getCurrentSession();
        try {
            session.getTransaction().begin();
            Query<User> query = session.createQuery("FROM User WHERE login LIKE :fLogin").setParameter("fLogin", key);
            rsl = query.getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return rsl;
    }

    public Optional<User> findByLogin(String login) {
        User rsl = new User();
        Session session = sf.getCurrentSession();
        try {
            session.getTransaction().begin();
            Query<User> query = session.createQuery("FROM User AS i WHERE i.login = :fLogin", User.class).setParameter("fLogin", login);
            rsl = query.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return Optional.ofNullable(rsl);
    }
}