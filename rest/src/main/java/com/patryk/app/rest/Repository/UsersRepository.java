package com.patryk.app.rest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.patryk.app.rest.Model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
