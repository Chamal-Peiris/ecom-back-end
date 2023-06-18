package com.chamal.repository;
import com.chamal.model.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
public interface UserRepository extends JpaRepository<UserDao, Long> {
    UserDao findByUsername(String username);
}