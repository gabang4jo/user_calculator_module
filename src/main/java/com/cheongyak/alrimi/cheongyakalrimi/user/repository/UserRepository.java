package com.cheongyak.alrimi.cheongyakalrimi.user.repository;

import com.cheongyak.alrimi.cheongyakalrimi.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByUserName(String userName);
    Optional<User> findUserByUserName(String userName);
    Optional<User> findByUserName(String userName);
}
