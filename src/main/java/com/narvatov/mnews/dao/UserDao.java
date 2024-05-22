package com.narvatov.mnews.dao;

import com.narvatov.mnews.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}

//@Repository
//public class UserDao {
//
//    public Optional<User> findByEmail(String email) {
//        return Optional.of(new User());
//    }
//
//    public void save(User user) {
//
//    }
//
//    public int count() {
//        return 1;
//    }
//
//}