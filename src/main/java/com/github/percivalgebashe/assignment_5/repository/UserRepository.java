package com.github.percivalgebashe.assignment_5.repository;

import com.github.percivalgebashe.assignment_5.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT user FROM User user WHERE user.username == :username")
    User findByUsername(@Param("username") String username);
}
